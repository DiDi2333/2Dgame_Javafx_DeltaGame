package main;

import javafx.animation.Timeline;
import javafx.scene.image.ImageView;

public interface Weapon {
	String name = null;
	ImageView weapon = null;
	int getDamage();
	ImageView getWeapon();
	Timeline getAttack();
	
}
