package main;

import java.awt.Robot;
import java.util.ArrayList;

import com.sun.glass.events.KeyEvent;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameView extends Application{
	public static int first = 0;
	public static int x = 600;
	public static int y=400;
	public static int AllRoadLength = 4*x;
	private static AnimationTimer DeadAnim = null;
	private static AnimationTimer MoveAnim = null;
	private static Rectangle fade2 = null;
	private static Player player = null;
	private static double mid_position = 0.0;
	static boolean flag = false;
	private static ImageView Condition = null;
	static long time = 0L;
	private static Scene s = null;
	private static Rectangle fade;
	private static Label Wasted;
	private static Label ClickToGo;
	private static Stage primaryStage = null;
	static Pane root = new Pane();
	static Pane pane = new Pane();
	static ArrayList<Line> road = null;
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setScene(getRoot(primaryStage));
		primaryStage.setTitle("deltaGame_game");
		primaryStage.show();
		
		//pane.requestFocus();//焦点对象 键盘输入需要对该节点或者节点所在容器进行设置
		
	}
	public static Scene getRoot(Stage MainStage) {
		if(first==0) {
			//背景
			ImageView backgroud = new ImageView("test_backGroud.jpg");
			backgroud.setPreserveRatio(true);
			backgroud.setFitHeight(2.0*y);
			backgroud.setOpacity(0.8);
			root.getChildren().add(backgroud);
			
			//路线设计
			road = new ArrayList<Line>();
			
			Line road0 = new Line(-0.5*x,0.5*y,0.0,0.5*y);
			Line road1 = new Line(0.0,0.5*y,1.0/4.0*x,0.5*y);
			//Line road1_0 = new Line(0.0,0.3*y,1.0/8.0*x,0.5*y);
			Line road2 = new Line(1.0/4.0*x,0.5*y,3.0/8.0*x,3.0/8.0*y);
			Line road3 = new Line(3.0/8.0*x,3.0/8.0*y,0.5*x,3.0/8.0*y);
			Line road4 = new Line(0.5*x,3.0/8.0*y,5.0/8.0*x,0.5*y);
			Line road5 = new Line(4.5/8.0*x,0.5*y,x,0.5*y);
			Line road5_5 = new Line(x,0.5*y,1.0*AllRoadLength-0.3*x,0.5*y);
			Line road6 = new Line(1.0*AllRoadLength-0.1*x,0.5*y , 1.0*AllRoadLength,0.5*y);
			
			road.add(road0);
			road.add(road1);
			//road.add(road1_0);
			road.add(road2);
			road.add(road3);
			//road.add(road4);
			road.add(road5);
			road.add(road5_5);
			road.add(road6);
			
			pane.setTranslateX(0.05*x);//角色初始位置设置
			//pane.setLayoutY(0.5*y-10);//还要修改人物模型差值
			pane.setLayoutY(0.5*y);//注：此处的y不可变为TranslateY 只可设置实际坐标 不然会和Player中的死亡判断发生冲突
			
			for(Line l:road) {
				root.getChildren().add(l);
			}
			
			DeadAnim = new AnimationTimer() {//角色死亡黑色遮罩
				long time = 0L;
				@Override
				public void handle(long now) {
					final long frequency = 1_000_000_000/75;
					if(now - time >= frequency) {
						
						if(player.isDead()) {
							System.out.println("isDead:"+player.isDead());
							DeadBlack();
						}
						//主体视窗移动 
						if(!(pane.getTranslateX()<0.5*x||pane.getTranslateX()>AllRoadLength - 0.5*x)) {
							if(player.getisMoving()) {//使用到达确定位置而不是递增 保证到达指定位置而不是会因为所加位置不确定而导致root逐渐永久性向右
								TranslateTransition RootMove = new TranslateTransition(Duration.millis(1), root);
								RootMove.setToX(-pane.getTranslateX()+0.5*x);//此处时直接获取人物位置而不是通过获取player中的移动长度
								RootMove.playFromStart();
								//root.setTranslateX(root.getTranslateX() - player.getSpeedX());
							}
						}
						time = now;
					}
				}
				
			};
			DeadAnim.start();
			
			// 创建 DropShadow 效果
	        DropShadow shadow = new DropShadow();
	        shadow.setColor(Color.GRAY); // 阴影颜色
	        //shadow.setOffsetX(10);       // 阴影在 X 方向的偏移量
	        shadow.setOffsetY(6);       // 阴影在 Y 方向的偏移量
	        shadow.setRadius(5);        // 阴影的模糊半径
			pane.setEffect(shadow);
			
			root.getChildren().addAll(pane);
			
			//载入黑色
			fade = new Rectangle(x,y);
			fade.setStyle("-fx-fill: black");
			fade.setOpacity(1);
			FadeTransition fade_anim = new FadeTransition();
			
			//出现
			
			fade_anim.setDuration(Duration.seconds(1.5));
			fade_anim.setCycleCount(1);
			fade_anim.setFromValue(1);
			fade_anim.setToValue(0);
			fade_anim.setNode(fade);
			
			fade_anim.setOnFinished(FadeEvent->{
				root.getChildren().remove(fade);
			});
			root.getChildren().add(fade);
			fade_anim.play();
			
			s = new Scene(root,x,y);
			
			s.setOnMouseClicked(MouseEvent->{
				System.out.println("start initial");
				MainStage.setScene(FirstView.getMainRoot());
			});
			
			
			player = getPlayer();
			pane.requestFocus();//焦点对象 键盘输入需要对该节点或者节点所在容器进行设置
			
			Condition = player.getStand();
			Polygon test = player.getChara();
			pane.getChildren().addAll(Condition,test);//角色实体创建
			player.toSetStand(Condition, player.getStand());
			
			player.Move();
			first++;
		}
		else {
			//载入黑色
			fade = new Rectangle(x,y);
			fade.setStyle("-fx-fill: black");
			fade.setOpacity(1);
			FadeTransition fade_anim = new FadeTransition();
			
			//出现
			
			fade_anim.setDuration(Duration.seconds(1.5));
			fade_anim.setCycleCount(1);
			fade_anim.setFromValue(1);
			fade_anim.setToValue(0);
			fade_anim.setNode(fade);
			
			fade_anim.setOnFinished(FadeEvent->{
				root.getChildren().remove(fade);
			});
			root.getChildren().add(fade);
			fade_anim.play();
			
			root.setTranslateX(0.0*x);
			root.getChildren().remove(fade2);
			root.getChildren().remove(Wasted);
			root.getChildren().remove(ClickToGo);
			pane.setTranslateX(0.05*x);//角色初始位置设置
			pane.setTranslateY(0.0*y);//注：此处的y不可变为TranslateY 只可设置实际坐标 不然会和Player中的死亡判断发生冲突
			player.BackToStart();
			DeadAnim.start();
		}
		return s;
	}
	
	public static void DeadBlack() {
		//出现
		mid_position = pane.getTranslateX()-0.5*x;
		
		if(mid_position<=0.0) {
			mid_position = 0.0*x;
		}
		else if(mid_position>=1.0*AllRoadLength-1.0*x) {
			mid_position = 1.0*AllRoadLength-1.0*x;
		}
		
		fade2 = new Rectangle(2.0*x,y);
		fade2.setStyle("-fx-fill: #4d4d4d");
		fade2.setOpacity(0);
		root.getChildren().add(fade2);
		
		fade2.setLayoutX(mid_position-0.2*x);
		FadeTransition fade_anim2 = new FadeTransition();
		fade_anim2.setDuration(Duration.seconds(0.5));
		fade_anim2.setCycleCount(1);
		fade_anim2.setFromValue(0);
		fade_anim2.setToValue(1);//原本想设置为0.5 但发现会出现两个重叠导致部分深色 暂时不知道原因 所以设置为全黑
		fade_anim2.setNode(fade2);
		fade_anim2.play();
		
		fade_anim2.setOnFinished(Wasted_event->{
			Wasted = new Label("Try Again");
			Wasted.setStyle("-fx-font-size: "+1.0/8.0*x+"px; -fx-text-fill: RED;");//通过css设置属性很方便 但会产生绑定
			Wasted.setOpacity(0); // 初始透明度为 0
			Wasted.setLayoutX(mid_position + 1.0/5.0*x);
			Wasted.setLayoutY(1.0/4.0*y+1.0/25.0*y);
			FadeTransition fade_anim_label = new FadeTransition();
			fade_anim_label.setDuration(Duration.seconds(0.85));
			fade_anim_label.setCycleCount(1);
			fade_anim_label.setFromValue(0);
			fade_anim_label.setToValue(1);
			fade_anim_label.setNode(Wasted);
			root.getChildren().add(Wasted);
			fade_anim_label.play();
			fade_anim_label.setOnFinished(ClickEvent->{
				ClickToGo = new Label("点击任意处以继续");
				ClickToGo.setStyle("-fx-font-size: "+1.0/32.0*x+"px; -fx-text-fill: RED;");//通过css设置属性很方便 但会产生绑定
				ClickToGo.setLayoutX(mid_position + 1.65/5.0*x);
				ClickToGo.setLayoutY(1.0/4.0*y+1.0/4.0*y+1.0/25.0*y);
				ClickToGo.setOpacity(0);
				FadeTransition fade_anim_clicklabel = new FadeTransition();
				fade_anim_clicklabel.setDuration(Duration.seconds(0.7));
				fade_anim_clicklabel.setCycleCount(1);
				fade_anim_clicklabel.setFromValue(0);
				fade_anim_clicklabel.setToValue(1);
				fade_anim_clicklabel.setNode(ClickToGo);
				root.getChildren().add(ClickToGo);
				fade_anim_clicklabel.play();
			});
		});
		
		DeadAnim.stop();
		
	}
	public static Player getPlayer() {//此处以实例化delta为案例 还需要修改
		PlayerDelta delta = null;//(1.0/5.0*y)*(2/3)
		double width = (1.0/5.0*y)/3;
		Double[] test_points = new Double[] {
				-1.0*width,0.0 , 1.0*width,0.0 , 1.0*width,10.0 , -1.0*width,10.0
		};
		Polygon test = new Polygon();
		test.setStyle("-fx-fill: rgba(24,24,255,0.5)");
		test.getPoints().addAll(test_points);
		delta = new PlayerDelta(s,pane,road,test);
		delta.setTimeline(delta.StartRun(),delta.BackToStand());//设置跑动动画
		return delta;
	}
}


