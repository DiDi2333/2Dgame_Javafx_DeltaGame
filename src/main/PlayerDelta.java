package main;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class PlayerDelta extends Player{
	private Image StandImage = new Image("file:Chara/Delta_test/Stand.png");
	private ImageView Stand = new ImageView(StandImage);
	private ImageView Condition = Stand;
	private Image Run01 = new Image("file:Chara/Delta_test/Run01.png");
	private Image Run02 = new Image("file:Chara/Delta_test/Run02.png");
	private Image Run03 = new Image("file:Chara/Delta_test/Run03.png");
	private Image Run04 = new Image("file:Chara/Delta_test/Run04.png");
	private ArrayList<Image> Runs = new ArrayList<Image>();
	private Timeline Run = null;
	private Timeline BackToStand = null;
	private WeaponTest delta_weapon = null;//武器
	
	
	public PlayerDelta(Scene s, Pane pane, ArrayList<Line> road, Polygon test) {
		super(s, pane, road, test);
		// TODO Auto-generated constructor stub
		//Stand初始化
		Condition.setPreserveRatio(true);
		Condition.setFitHeight(1.0/5.0*y);//人物模型高度
		Condition.setLayoutX(-3.0/60.0*x);
		Condition.setLayoutY(-1.0/5.0*y);
		//跑动动画
		this.Run = new Timeline();
		this.Run.setCycleCount(Timeline.INDEFINITE);
		
		Runs.add(Run01);
		Runs.add(Run02);
		Runs.add(Run03);
		Runs.add(Run04);
		double i = 0.0;
		for(Image image:Runs) {//帧数过低会导致触发播片不及时//前进动画
				KeyFrame keyframe = new KeyFrame(Duration.seconds(i+=0.08),Run_event->{
				Condition.setImage(image); 
			});
			Run.getKeyFrames().add(keyframe);
		}
		this.BackToStand = new Timeline();//归位取消动画
		BackToStand.getKeyFrames().add(new KeyFrame(Duration.seconds(0.01),Run_event->{
				Condition.setImage(StandImage); 
			}));
		
	}
	
	public Timeline StartRun() {
		return Run;
	}
	public Timeline BackToStand() {
		return BackToStand;
	}
	@Override
	public ImageView getStand() {
		return Condition;
	}
	
}
class WeaponTest implements Weapon{//角色绑定武器
	int damage = 10;//武器伤害
	String name = "测试武器";
	//Image weapon = new Image();
	
	
	@Override
	public int getDamage() {
		// TODO Auto-generated method stub
		return damage;
	}

	@Override
	public ImageView getWeapon() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Timeline getAttack() {
		// TODO Auto-generated method stub
		Timeline attack = null;
		return null;
	}
	
}
