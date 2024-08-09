package main;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
/*
public class Main {
	int x;//可供调节的xy
	int y;

    public static void main(String[] args) {
    	
        FirstView.launch(FirstView.class, args);
    }
}
*/

/*此处为一个简单的Player子类使用案例 Player需要传入参数如下：
 *Player所在Scene 便于Player中的控制器更具减排时间调整位置
 *Player人物本身的pane 用于获取人物在根布局中的位置以及进行修改 调整
 *测试方块test 自定义图形 用于进行简单测试//可在Player子类中直接固定
 *场景路线ArrayList<Line> road 用于判断x y坐标
 */
public class Main extends Application{
	static int x=600;//可供调节的xy
	static int y=400;
	private Image Run01 = new Image("file:Chara/Delta_test/Run01.png");
	private Image Run02 = new Image("file:Chara/Delta_test/Run02.png");
	private Image Run03 = new Image("file:Chara/Delta_test/Run03.png");
	private Image Run04 = new Image("file:Chara/Delta_test/Run04.png");
	private ArrayList<Image> Runs = new ArrayList<Image>();
	private Timeline Run = null;
    public static void main(String[] args) {
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Pane pane = new Pane();
		Pane root = new Pane();
		
		pane.setLayoutX(0.0);//角色初始位置设置
		//pane.setLayoutY(0.5*y-10);//还要修改人物模型差值
		pane.setLayoutY(0.5*y);
		
		Double[] test_points = new Double[] {
				0.0,0.0 , 10.0,0.0 , 10.0,10.0 , 0.0,10.0
		};
		Polygon test = new Polygon();
		test.setStyle("-fx-fill: rgba(24,24,255,0.5)");
		test.getPoints().addAll(test_points);
		Line road1 = new Line(0.0,0.5*y,x,0.5*y);
		ArrayList<Line> road = new ArrayList<Line>();
		road.add(road1);
		
		//delta.StartRun().play();
		
		root.getChildren().addAll(pane,road1);
		Scene scene = new Scene(root,x,y);
		PlayerDelta delta = new PlayerDelta(scene,pane,road,test);
		ImageView Condition = delta.getStand();
		
		pane.getChildren().addAll(Condition);
		delta.setTimeline(delta.StartRun(),delta.BackToStand());
		//delta.StartRun().play();
		pane.requestFocus();
		delta.Move();
		primaryStage.setScene(scene);
		primaryStage.setTitle("test");
		primaryStage.show();
		
		
	}
	
}



