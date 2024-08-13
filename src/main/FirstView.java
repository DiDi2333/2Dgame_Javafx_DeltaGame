package main;

import java.util.ArrayList;

import javafx.scene.paint.Color;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;


public class FirstView extends Application{
	private static Stage MainStage = null;
	private static boolean flag_char = false;
	public static double x = 600;//比例 画质
	public static double y = 400;//caution：分辨率只能在3/2附近进行调整，所有的效果都是基于3/2进行设计的
	private static int ChapterNum = 0;//0 章节第一张，以此递增
	private static int ChapterOrPlayerBattle = 0;//0 进入章节选择，1 进入玩家对战
	private static int Condition = 0;//0 处于模式选择 1 处于章节选择 2 处于玩家对战
	private static StackPane ModeChoose = new StackPane();
	private static int current_num = 0;
	private static int next_num = 0;
	private static int sum_up_down = 0;//管理当前所处位置
	private static ArrayList<ForBlankChapter> vboxs = new ArrayList<ForBlankChapter>();
	private static int first = 0;//初始化界面标识
	private static VBox previous = null;
	private static VBox current = null;
	private static VBox next = null;
	private static Label ForChapter = new Label();
	private static Label ForBattle = new Label();
	private static ImageView ImageForChapter = new ImageView();
	private static ImageView ImageForBattle = new ImageView();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.MainStage = primaryStage;//必须从FirstView启动 不然无法获取主窗口实例 无法返回主菜单
		System.out.println(MainStage);
		System.out.println(primaryStage);
		primaryStage.setScene(getMainRoot());
		primaryStage.setTitle("deltaGame");
		primaryStage.show();
		
	}
	public static Scene getMainRoot() {
		//初始化部分 在被调用时方便返回
		flag_char = false;
		ChapterNum = 0;//0 章节第一张，以此递增
		ChapterOrPlayerBattle = 0;//0 进入章节选择，1 进入玩家对战
		Condition = 0;//0 处于模式选择 1 处于章节选择 2 处于玩家对战
		current_num = 0;
		next_num = 0;
		sum_up_down = 0;//管理当前所处位置
		first = 0;//初始化界面标识
		VBox previous = null;
		VBox current = null;
		VBox next = null;
		
		// TODO Auto-generated method stub
		Pane root = new Pane();
		Pane UiSelect = new Pane();
		Pane ChapterAndPlayerBattle = new Pane();
		
		Rectangle fade = new Rectangle(x,y);
		/*
		ImageView character = new ImageView("chara_01.jpg");//暂时只有一个
		character.setLayoutX(0.5*x);
		character.setPreserveRatio(true);
		character.setFitHeight(y)
		root.getChildren().add(character);
		*/
		//ChapterAndPlayer 章节或玩家对战轮盘切换
		ForBlankChapter Chapter = new ForBlankChapter("故事模式",new Image("file:Chara/Delta_test/Run01.png"));
		ForBlankChapter PlayerBattle = new ForBlankChapter("玩家对战",new Image("file:Chara/Delta_test/Stand.png"));
		
		vboxs.add(Chapter);
		vboxs.add(PlayerBattle);
		ModeChoose.setStyle("-fx-background-color: rgba(0,0,0,0)");
		
		ChangeVBox(vboxs);
		
		ModeChoose.setLayoutX(-0.5*y);
		//ModeChoose.setLayoutY(-1.0*y);
		
		//Chapter 章节详细页面介绍（和ChapterAndPlayer在同一个布局中以便通过平移动画切换）
		
		//黑色
		fade.setStyle("-fx-fill: black");
		fade.setOpacity(0);
		
		
		//root设计（章节选择，人物选择展示）
		Circle switch_chara = new Circle();
		double r = 1.0/19.0*x;
		switch_chara.setRadius(r);
		switch_chara.setLayoutX(14.8/16.0*x);
		switch_chara.setLayoutY(14.3/16.0*y);
		
		switch_chara.setStyle("-fx-stroke-width:"+1.0/75.0*y+";"
				+ "-fx-stroke:#797979;"
				+ "-fx-fill: rgba(240,248,255,1)");
		root.getChildren().addAll(switch_chara);
		
		switch_chara.setOnMouseClicked(test_event->{
			System.out.println("test_mass_success");
		});
		
		//滑块设计
		
		Polygon pg = new Polygon();//滑块
		Double[] points = new Double[] {0.0,0.0 , 2.0/3.0*x,0.0 , 0.5*x,1.0*y , 0.0, 1.0*y};
		pg.getPoints().addAll(points);
		pg.setStyle("-fx-stroke-width: "+1.0/70.0*y+";-fx-stroke-type: inside;-fx-stroke: rgba(187,187,187,1);-fx-fill: #797979;");
		
		//选项1创建
		Double[] points_start = new Double[] {0.0,0.0 , 13.0/24.0*x,0.0 , 25.0/48.0*x,2.0/16.0*y , 
				0.0,2.0/16.0*y};
		Pane chapter = SelectPane("START GAME",points_start);
		chapter.setLayoutX(0.0);//绘制图形以及设置节点位置应当依照其父布局的坐标系
		chapter.setLayoutY(7.0/16.0*y);
		
		//选项2创建
		Double[] points_quit = new Double[] {0.0,0.0 , 25.0/48.0*x,0.0 , 24.0/48.0*x,2.0/16.0*y , 
				0.0,2.0/16.0*y};
		Pane quit = SelectPane("	QUIT",points_quit);
		quit.setLayoutX(0.0);
		quit.setLayoutY(10.0/16.0*y);
		
		//ModeChoose创建
		Double[] up_arrow = new Double[] {1.0/16.0*x,1.0/16.0*y-1.0/32.0*y , 0.0*x,3.0/16.0*y-1.0/32.0*y , 2.0/16.0*x,3.0/16.0*y-1.0/32.0*y};
		Double[] down_arrow = new Double[] {1.0/16.0*x,3.0/16.0*y-3.0/32.0*y , 0.0*x,1.0/16.0*y-3.0/32.0*y , 2.0/16.0*x,1.0/16.0*y-3.0/32.0*y};
		
		Pane up = SelectPane("str",up_arrow);
		Pane down = SelectPane("str",down_arrow);
		up.setTranslateX(1.0/4.0*x-1.0/16.0*x);//1.0/4.0*x是界面位置 1.0/16.0*x是由于setLayoutx对准了左上角而需要修改的偏移值
		up.setMaxWidth(2.0/16.0*x);
		up.setMaxHeight(2.0/16.0*y);
		down.setLayoutX(1.0/4.0*x-1.0/16.0*x);
		down.setLayoutY(1.0*y-2.0/16.0*y);
		down.setMaxWidth(2.0/16.0*x);
		down.setMaxHeight(2.0/16.0*y);
		
		
		//up.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");//测试
		//down.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");//测试
		
		//Chapter Or Player_Battle
		
		root.setStyle("-fx-background-color: rgba(0, 790, 0, 1);");
		
		UiSelect.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
		
		UiSelect.getChildren().addAll(pg,chapter,quit);//添加点
		
		root.getChildren().addAll(ModeChoose,up,down,UiSelect);
		
		
		//两个选项功能设置
		chapter.setOnMouseClicked(move_event -> {
			if(!flag_char) {
				System.out.println("change to chapter_select");
				flag_char=true;
				Timeline move = new Timeline(new KeyFrame(Duration.seconds(0.07),
	                    new KeyValue(UiSelect.layoutXProperty(), 0.5 * x)));
				move.play();
			}
			else if(flag_char){
				//切入点
				System.out.println("start game");
				//黑色切换页
				Scene scene1 = GameView.getRoot(MainStage);
				FadeTransition fade_anim = new FadeTransition();
				//出现
				//界面跳转
				fade_anim.setDuration(Duration.seconds(0.8));
				fade_anim.setCycleCount(1);
				fade_anim.setFromValue(0);
				fade_anim.setToValue(1);
				fade_anim.setNode(fade);
				
				root.getChildren().add(fade);
				fade_anim.play();
				fade_anim.setOnFinished(finish_event->{
					MainStage.setScene(scene1);
					root.getChildren().remove(fade);
				});
				
			}	
		});//动画测试 //已转正 作为切换动画
		quit.setOnMouseClicked(move_event -> {
			if(flag_char){
				System.out.println("back to Main");
				flag_char=false;
				Timeline move = new Timeline(new KeyFrame(Duration.seconds(0.07),
                    new KeyValue(UiSelect.layoutXProperty(), 0.0 * x)));
				move.play();
			}
			else if(!flag_char) {
				System.out.println("Bye");
				System.exit(0);
			}
		});//动画测试 //已转正 作为切换动画
		
		//向上向下
		up.setOnMouseClicked(up_event->{
			if(Condition == 0) {//Chapter Battle 选择界面
				System.out.println(current_num+" "+next_num);
				sum_up_down++;
				if(sum_up_down>vboxs.size()) {
					sum_up_down-=vboxs.size();
				}
				
				System.out.println(current_num+" "+next_num);
				
				ChangeVBox(vboxs);
			}
			else if(Condition == 1) {//Chapter界面
				
			}
		});
		Scene s = new Scene(root,x,y);
		
		return s;
	}
	
	public static Pane SelectPane(String label,Double[] points) {
		//Double数组用于传入坐标构建图形
		Pane pane = new Pane();
		Polygon chose_one = new Polygon();//选项框体
		chose_one.getPoints().addAll(points);
		chose_one.setStyle("-fx-stroke-width: "+1.0/70.0*y+";-fx-stroke-type: centered;"
				+ "-fx-stroke: rgba(223,223,223,1);"
				+ "-fx-stroke-linejoin: round;"
				+ "-fx-fill: rgba(239,239,239,1);");
		
		Label chap = new Label(label);//文字 字体大小会随分辨率变化二变化
		chap.setStyle("-fx-font-size: "+ 1.0/16.0*y+"px; " + 
                "-fx-font-family: Arial;-fx-text-fill: #656565;");
		chap.setLayoutX(1.0/4.0*x-1.0/30.0*x);
		chap.setLayoutY((1.0/16.0*y)/2.0);
		
		pane.getChildren().addAll(chose_one,chap);
		if(label.equals("str")) {
			pane.getChildren().remove(chap);
		}
		pane.setOnMouseEntered(move_event_in->{
			//System.out.println("big");
	        Press_anime(pane,1.1);
		});
		pane.setOnMouseExited(move_event_out->{
			Press_anime(pane,1);
		});
		return pane;
		
	}
	public static void Press_anime (Pane p,double r) {
		//按键动画效果 放大
		//r为放大倍数
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.001), p);
		scaleTransition.setToX(r);  // 设置X轴方向的放大倍数
		scaleTransition.setToY(r);
		scaleTransition.setAutoReverse(true);
		scaleTransition.play();
		        
	}
	public static VBox getChapterChoosePane(Label label,ImageView resource) {
		VBox Chapter = new VBox();
		Chapter.setStyle("-fx-border-width:"+1.0/70.0*y+"px;"
				+ "-fx-border-color: #797979;-fx-border-radius:"+10.0/400.0*y+"px;-fx-background-radius:"+15.0/400.0*y+"px;"
						+ "-fx-background-color: rgba(187,187,187,1);-fx-padding: "+5.0/400.0*y+"px");
		Chapter.setPrefWidth(0.5*x-0.05*x);
		Chapter.setPrefHeight(8.0/16.0*y);
		label.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFD7FF;-fx-font-size:"+1.0/16.0*y+"px");
		Chapter.getChildren().addAll(resource,label);
		return Chapter;
	}
	public static void ChangeVBox(ArrayList<ForBlankChapter> Chapters) {
		 // 创建 DropShadow 效果
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.GRAY); // 阴影颜色
        //shadow.setOffsetX(10);       // 阴影在 X 方向的偏移量
        shadow.setOffsetY(6);       // 阴影在 Y 方向的偏移量
        shadow.setRadius(5);        // 阴影的模糊半径
        
		if(first==0) {//初始化界面 
			//ChapterChoose (current)
			current_num = 0;
			next_num = 1;
			
			ForChapter.setText(Chapters.get(current_num).getName());
			ImageForChapter.setImage(Chapters.get(current_num).getImage());//Chapter图片设置
			ImageForChapter.setPreserveRatio(true);
			ImageForChapter.setFitHeight(6.0/16.0*y);
			current = getChapterChoosePane(ForChapter,ImageForChapter);
			
			//PlayerBattleChoose (next)
			ForBattle.setText(Chapters.get(next_num).getName());
			ImageForBattle.setImage(Chapters.get(next_num).getImage());//Battle图片设置
			ImageForBattle.setPreserveRatio(true);
			ImageForBattle.setFitHeight(6.0/16.0*y);
			next = getChapterChoosePane(ForBattle,ImageForBattle);
			
			
			current.setEffect(shadow);
			next.setEffect(shadow);
			
			ModeChoose.getChildren().addAll(next,current);//Stack默认将节点堆于中央 xy属性由容器管理 所以添加后设置xy才能生效
			
			current.setTranslateX(1.0/32.0*x+1.3/4.0*x);//位于ModeChoose列表的右侧上方
			current.setTranslateY(2.0/8.0*y);
			next.setTranslateX(1.0/32.0*x+1.3/4.0*x);
			next.setTranslateY(2.0/8.0*y+0.25*y);//思考中 应该用哪种效果 当前相差一个y
			
			first++;
		}
		else if(first == 1){
			System.out.println("start update!");
			/*
			System.out.println(current_num+" "+next_num);
			ForChapter.setText(Chapters.get(current_num).getName());
			ImageForChapter.setImage(Chapters.get(current_num).getImage());//Chapter图片设置
			
			//PlayerBattleChoose (next)
			ForBattle.setText(Chapters.get(next_num).getName());
			ImageForBattle.setImage(Chapters.get(next_num).getImage());//Battle图片设置
			*/
			
		}
	}
}
class ForBlankChapter{
	private String Name;
	private Image Image;
	public ForBlankChapter(String Name,Image Image) {
		this.Name = Name;
		this.Image = Image;
	}
	public String getName() {
		return Name;
	}
	public Image getImage() {
		return Image;
	}
}

