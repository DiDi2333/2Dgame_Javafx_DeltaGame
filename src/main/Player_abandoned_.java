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

public class Player_abandoned_ {
	public static int x = 600;
	public static int y=400;
	private ImageView Condition = null;
	private ImageView Stand = null;
	private int blood;
	private String name;
	private static Timeline Run1 = null;
	private static Timeline BackToStand = null;
	private boolean ForwardOrBack = false;
	private boolean FirstForward = true;
	double FallDistance = 0.0;
	static double k = 0.0;
	static double cFork = 0.0;
	static double Distance = 0.0;
	AnimationTimer timer;
	AnimationTimer timer2;
	AnimationTimer timerGo;
	AnimationTimer timer2Go;
	ArrayList<Line> road = null;
	Scene s;
	Pane pane;
	Polygon test;
	long time = 0L;
	public Player_abandoned_(Scene s,Pane pane,ArrayList<Line> road,Polygon test) {
		this.s = s;
		this.pane = pane;
		this.road = road;
		this.test = test;
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
	public void Move() {
		
		//Timeline出现问题时 直接使用TranslateTransition 已经解决了很多（可能时线程问题）
		System.out.println(pane.getLayoutY());
		TranslateTransition GoUp = new TranslateTransition(Duration.millis(150), pane);
		GoUp.setByY(-1.0/7.5*y);
		TranslateTransition GoDown = new TranslateTransition(Duration.millis(100), pane);
		
		
		
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				 final long frequency = 10000000;
				// TODO Auto-generated method stub
				if (now - time >= frequency) {
	                System.out.println("go forward"+pane.getTranslateX());
	                //GoForward.setToY(getRoadY(road,pane));
	                TranslateTransition GoForward = new TranslateTransition(Duration.millis(1), pane);
	                //TranslateTransition GoForward2 = new TranslateTransition(Duration.millis(10), pane);
	                double mid = getRoadY(road,pane);
	                if(k!=0) {
	                	GoForward.setByX(1.0/200*x);
	                	GoForward.setByY(getRoadY(road,pane)-pane.getTranslateY());
	                	GoForward.playFromStart();
	                	
	                	
	                	//GoForward.playFromStart();
	                	
	                	System.out.println("k:"+k);
	                }
	                else {
	                	GoForward.setByX(1.0/200*x);
	                	GoForward.playFromStart();
	                	System.out.println("k_ZERO:"+k);
	                	//mid = getRoadY(road,pane);
	                }
	                
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
	                
	                //跑动动画判断
	                if(Run1!=null) {
	                	System.out.println("Run不是空");
	                }
	                else {
	                	System.out.println("Run是空");
	                }
	                
	                Run1.play();
	                //GoForward.playFromStart();
	                //Run.play();// 2024/7/22/17:16 困扰许久的跑动动画问题解决了 
	                			//将Timeline Run设置为static即可，感觉可能是线程问题或者执行优先级为题吧
	                System.out.println("height_y_forward:"+pane.getTranslateY());
	                time = now;
	            }
			}
		};
		
		/*timerGo = new AnimationTimer() {
			@Override
			public void handle(long now) {
				 final long frequency = 10000000;
				// TODO Auto-generated method stub
				if (now - time >= frequency) {
	                pane.setTranslateY(Distance);
	                Distance += 1;
	                time = now;
	            }
			}
		};
		timerGo.start();*/
		timer2 = new AnimationTimer() {
			@Override
			public void handle(long now) {
				 final long frequency = 10000000;
				// TODO Auto-generated method stub
				if (now - time > frequency) {
	                System.out.println("go back"+pane.getTranslateX());
	                TranslateTransition GoBack = new TranslateTransition(Duration.millis(1), pane);
	                
	                double mid = getRoadY(road,pane);
	                
	                if(k!=0) {
	                	GoBack.setByX(-1.0/200*x);
	                	GoBack.setToY(getRoadY(road,pane));
	                	System.out.println("k:"+k);
	                	
	                }
	                else {
	                	GoBack.setByX(-1.0/200*x);
	                	System.out.println("k_ZERO:"+k);
	                	mid = getRoadY(road,pane);
	                }
	                
	              //转向 向左
	                Scale back = new Scale();
	                if(ForwardOrBack) {
	                	back.setX(-1);
	                	pane.getTransforms().add(back);
	                	ForwardOrBack = false;
	                }
	                Run1.play();
	                GoBack.playFromStart();
	                
	                
	                System.out.println("height_y_back:"+pane.getTranslateY());
	                time = now;
	            }
			}
		};
		
		s.setOnKeyPressed(Key_event->{
			if(Key_event.getCode()==KeyCode.D) {
					
					timer.start();
				
			}
			if(Key_event.getCode()==KeyCode.SPACE) {
				System.out.println("Jump"+pane.getTranslateY());
				//TranslateTransition GoDown = new TranslateTransition(Duration.millis(100), pane);//GoDown调用
				GoUp.play();
				GoUp.setOnFinished(finishedEvent -> {//动画执行完毕的承接 很好用
					System.out.println("Fall");
					System.out.println("Fall To Y:"+pane.getTranslateY());
					FallDistance = pane.getTranslateY();
					System.out.println(FallDistance);
					//FallDistance刷新后动画也要刷新 卡顿许久却忘记了java动画也是需要按照顺序执行而不是由线程代理
					GoDown.setToY(getRoadY(road,pane));
					GoDown.play();
					System.out.println("Actually:"+FallDistance);
				});
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
				
			}
			if(Key_event2.getCode()==KeyCode.A) {
				Run1.stop();
				BackToStand.play();
				timer2.stop();
				
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
		double x = pane.getTranslateX();
		Line current = null;
		for(Line l:road) {
			if(l.getStartX()<=x && l.getEndX()>=x) {
				current = l;
				break;
			}
		}
		if(current!=null) {
			double a = current.getEndY() - current.getStartY();
			System.out.println("a:"+a);
			double b = current.getStartX() - current.getEndX();
			System.out.println("b:"+b);
			double c = current.getEndX()*current.getStartY() - current.getStartX()*current.getEndY();
			System.out.println("c:"+c);
			road_y = -((a/b)*x+(c/b));
			k=a/b;
			//cFork = c;
			System.out.println("road_y:"+road_y);
		}
		else {
			road_y = 1.5*y;
		}
		
		return road_y-0.5*y;//两点式坐标计算出的结果是基于原框体一半未起点的 所以要减去0.5y
	}
}
	