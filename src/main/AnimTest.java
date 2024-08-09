package main;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;

/*public class AnimTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个 Arc 对象
        Arc arc = new Arc();
        Arc arc1 = new Arc();
        arc.setCenterX(150); // 圆心 X 坐标
        arc.setCenterY(150); // 圆心 Y 坐标
        arc.setRadiusX(150); // X 轴上的半径
        arc.setRadiusY(150); // Y 轴上的半径
        arc.setStartAngle(0); // 起始角度（以度为单位）
        arc.setLength(90);    // 弧度长度（以度为单位），1/4 圆即 90 度
        arc.setType(ArcType.ROUND); // 设置为圆形扇形
        
        // 设置填充颜色
        arc.setFill(Color.RED);
        arc.setStroke(Color.BLACK); // 设置边框颜色
        arc.setStrokeWidth(2); // 设置边框宽度
        
        
        arc1.setCenterX(150); // 圆心 X 坐标
        arc1.setCenterY(150); // 圆心 Y 坐标
        arc1.setRadiusX(150); // X 轴上的半径
        arc1.setRadiusY(150); // Y 轴上的半径
        arc1.setStartAngle(0); // 起始角度（以度为单位）
        arc1.setLength(90);    // 弧度长度（以度为单位），1/4 圆即 90 度
        arc1.setType(ArcType.ROUND); // 设置为圆形扇形
        
        // 设置填充颜色
        arc1.setFill(Color.BLUE);
        arc1.setStroke(Color.BLACK); // 设置边框颜色
        arc1.setStrokeWidth(2); // 设置边框宽度
        arc1.setLayoutY(100);
        // 将 Arc 添加到布局中
        StackPane root = new StackPane();
        root.getChildren().add(arc1);
        root.getChildren().add(arc);
        Scene scene = new Scene(root, 300, 300);
        TranslateTransition move = new TranslateTransition();
        move.setFromX(0);
        move.setToX(200);
        
        move.setDuration(Duration.seconds(2));
        move.setInterpolator(Interpolator.EASE_IN);
        move.setNode(arc);
        move.play();
        TranslateTransition move1 = new TranslateTransition();
        move1.setFromX(0);
        move1.setToX(200);
        
        move1.setDuration(Duration.seconds(2));
        //move1.setInterpolator(Interpolator.EASE_IN);
        move1.setNode(arc1);
        move1.play();
        primaryStage.setTitle("Quarter Circle Fan Shape");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;

public class AnimTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        // 创建两个标签
        Label label1 = new Label("This is the bottom layout");
        Label label2 = new Label("This is the top layout");

        // 创建 DropShadow 效果
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.GRAY); // 阴影颜色
        //shadow.setOffsetX(10);       // 阴影在 X 方向的偏移量
        shadow.setOffsetY(6);       // 阴影在 Y 方向的偏移量
        shadow.setRadius(15);        // 阴影的模糊半径

        // 创建底部布局
        AnchorPane bottomPane = new AnchorPane(label1);
        bottomPane.setStyle("-fx-background-color: lightblue;"); // 底部布局背景色
        AnchorPane.setTopAnchor(label1, 10.0);
        AnchorPane.setLeftAnchor(label1, 10.0);

        // 创建顶部布局
        AnchorPane topPane = new AnchorPane(label2);
        topPane.setEffect(shadow);
        topPane.setStyle("-fx-background-color: lightgreen;"); // 顶部布局背景色
        AnchorPane.setTopAnchor(label2, 50.0);
        AnchorPane.setLeftAnchor(label2, 50.0);
        topPane.setLayoutX(100);
        topPane.setLayoutY(100);
        // 创建根布局，将底部布局和顶部布局放置其中
        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(bottomPane, topPane);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("JavaFX Shadow Effect Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}