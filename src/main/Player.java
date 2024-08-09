package main;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

public class Player {
	public static int x = 600;
	public static int y=400;
	private ImageView Condition = null;
	private ImageView Stand = null;
	private int blood;
	private String name;
	private static Timeline Run1 = null;
	private static Timeline BackToStand = null;
	private static boolean ForwardOrBack = false;
	private static boolean FirstForward = true;
	private static boolean isDead = false;
	private static boolean isMoving = false;
	private static int AllRoadLength = 4*x;
	double FallDistance = 0.0;
	static double k = 0.0;
	static double cFork = 0.0;
	static double Distance = 0.0;
	static int fps = 75;
	static double midX = 3.0/600.0 * x;
	AnimationTimer timer = null;
	AnimationTimer timer2 = null;
	static AnimationTimer timerDown = null;
	ArrayList<Line> road = null;
	Scene s;
	Pane pane;
	Polygon test;
	
	public Player(Scene s,Pane pane,ArrayList<Line> road,Polygon test) {
		this.s = s;
		this.pane = pane;
		this.road = road;
		this.test = test;
	}
	public void BackToStart(){//初始化处 手动
		
		Scale forward = new Scale();
		if(ForwardOrBack) {
			forward.setX(1);
		}
		else {
			forward.setX(-1);
		}
		pane.getTransforms().add(forward);
		ForwardOrBack = false;
		FirstForward = true;
	}
	public boolean isDead() {
		return isDead;
	}
	public void setTimeline(Timeline Run,Timeline BackToStand) {
		this.Run1 = Run;
		this.BackToStand = BackToStand;
		//Run.play();//测试
	}
	public void toSetStand(ImageView Condition,ImageView Stand) {
		this.Condition = Condition;//获取布局上的人物形象对象
		this.Stand = Stand;//获取实例化对象对应的Stand形象
	}
	public static void stopAll() {
		timerDown.stop();
	}
	public boolean getisMoving() {
		return isMoving;
	}
	public void Move() {
		
		//Timeline出现问题时 直接使用TranslateTransition 已经解决了很多（可能时线程问题）
		System.out.println(pane.getLayoutY());
		TranslateTransition GoUp = new TranslateTransition(Duration.millis(150), pane);
		GoUp.setByY(-90.0/400.0*y);
		
		timerDown = new AnimationTimer() {
			long time = 0L;
			double midX2 = 10.0/400.0 * y;//重力G产生的向下的速度
			double mid = getRoadY(road,pane);
			boolean first = true;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				final long frequency = 1_000_000_000/fps;//刷新率fps为75(已更改 fps为全局变量)
				if(now - time >= frequency) {
					
					if(pane.getTranslateY() > 0.5*y) {//掉落到死亡位置停止掉落
						isDead = true;
						//stopAll();
						if(!first) {
							System.out.println("dead");
							first = false;
						}
					}
					else {
						//重开人物数据更新处（自动）
						first = true;
						isDead = false;
						
						if(pane.getTranslateY() > 0.0*y) {//判断是否掉落深坑 防止坐标重新导向回地面 即普遍情况重力
							pane.setTranslateY(pane.getTranslateY()+midX2);
						}
						else {//斜坡重力逻辑
							if(pane.getTranslateY() < getRoadY(road,pane)) {
								pane.setTranslateY(pane.getTranslateY()+midX2);
								System.out.println(pane.getTranslateY()+" "+getRoadY(road,pane));
							}
							else if(pane.getTranslateY() > getRoadY(road,pane)) {
								pane.setTranslateY(pane.getTranslateY()-midX2);
								System.out.println(pane.getTranslateY()+" "+getRoadY(road,pane));
							}
							else {
								pane.setTranslateY(pane.getTranslateY());
							}
						}
					}
					time = now;
				}
			}
			
		};
		timerDown.start();
		
		timer = new AnimationTimer() {
			long time = 0L;
			
			@Override
			public void handle(long now) {
				 final long frequency = 1_000_000_000/fps;//刷新率fps为75
				// TODO Auto-generated method stub
				if (now - time >= frequency) {
	                System.out.println("go forward"+pane.getTranslateX());
	                //判断是否到达边界
					if(pane.getTranslateX()>=1.0*AllRoadLength) {
						System.out.println("Should stop");
						pane.setTranslateX(1.0*AllRoadLength);
					}
					else {//normal情况
						pane.setTranslateX(pane.getTranslateX()+midX);
					}
	                
	                System.out.println("height_y_forward:"+pane.getTranslateY());
	                isMoving = true;
	                time = now;
	                
	              //转向 向右
	                Scale forward = new Scale();
	                if(!ForwardOrBack) {
	                	if(FirstForward) {
	                		forward.setX(1);
	                		FirstForward = false;
	                	}
	                	else {
	                		forward.setX(-1);
	                	}
	                	
	                	pane.getTransforms().add(forward);
	                	ForwardOrBack = true;
	                }
	                Run1.play();
	            }
			}
		};
		
		timer2 = new AnimationTimer() {
			long time = 0L;
			//double midX = 3.0/600.0 * x;
			@Override
			public void handle(long now) {
				 final long frequency = 1_000_000_000/fps;
				// TODO Auto-generated method stub
				if (now - time > frequency) {
	                System.out.println("go back"+pane.getTranslateX());
	                //判断是否到达边界
					if(pane.getTranslateX()<=0.0) {
						pane.setTranslateX(0.0);
					}
					else {
						pane.setTranslateX(pane.getTranslateX()-midX);
					}
	                System.out.println("height_y_back:"+pane.getTranslateY());
	                isMoving = true;
	                time = now;
	              //转向 向左
	                Scale back = new Scale();
	                if(ForwardOrBack) {
	                	back.setX(-1);
	                	pane.getTransforms().add(back);
	                	ForwardOrBack = false;
	                }
	                Run1.play();
	               
	            }
			}
		};
		
		s.setOnKeyPressed(Key_event->{
			if(Key_event.getCode()==KeyCode.SHIFT) {
				System.out.println("Speed up!");
				midX = 5.0/600.0 * x;
				
			}
			if(Key_event.getCode()==KeyCode.D) {
				System.out.println("true");
				timer.start();
			}
			if(Key_event.getCode()==KeyCode.SPACE) {
				System.out.println("Jump"+pane.getTranslateY());
				if(pane.getTranslateY()==getRoadY(road,pane)) {
					GoUp.play();
				}
			}
			if(Key_event.getCode()==KeyCode.A) {
				timer2.start();
			}
			
		});
		s.setOnKeyReleased(Key_event2->{
			if(Key_event2.getCode()==KeyCode.D) {
				Run1.stop();
				BackToStand.play();
				timer.stop();
				isMoving = false;
			}
			if(Key_event2.getCode()==KeyCode.A) {
				Run1.stop();
				BackToStand.play();
				timer2.stop();
				isMoving = false;
			}
			if(Key_event2.getCode()==KeyCode.SHIFT) {
				System.out.println("Speed down!");
				midX = 3.0/600.0 * x;
			}
		});
		
	}
	//待修改 任务造型
	public Polygon getChara() {
		return test;
	}
	public ImageView getStand() {
		return null;
	}
	public static double getRoadY(ArrayList<Line> road,Pane pane) {
		double road_y = y;
		int mid_fin = 0;
		double x = pane.getTranslateX();
		double y_pane= pane.getTranslateY();
		int first = 0;
		if(first==0) {
			y_pane = 0.5*y;
			first++;
		}
		
		Line current = null;
		for(Line l:road) {
			if(l.getStartX()<=x && l.getEndX()>=x) {
				current = l;
				break;
			}
		}
		if(current!=null) {
			double a = current.getEndY() - current.getStartY();
			//System.out.println("a:"+a);
			double b = current.getStartX() - current.getEndX();
			//System.out.println("b:"+b);
			double c = current.getEndX()*current.getStartY() - current.getStartX()*current.getEndY();
			//System.out.println("c:"+c);
			road_y = -((a/b)*x+(c/b));
			k=a/b;
			//cFork = c;
			//System.out.println("road_y:"+road_y);
		}
		else {
			road_y = 1.5*y;
		}
		if(k!=0) {
			road_y-=0.5*y;
			road_y/=10.0;
			mid_fin = (int) road_y;
			return mid_fin*10;
		}
		else if(k==0) {
			return road_y-0.5*y;
		}
		
		return 0;//两点式坐标计算出的结果是基于原框体一半未起点的 所以要减去0.5y
	}
}
	