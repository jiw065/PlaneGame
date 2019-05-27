package jw.practice.game.plane;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * This is the Super class for game objects
 * @author Amber Wang
 * @version 1.0
 * @since 2019.04.03
 *
 */
public abstract class GameObject {
	  Image  img;
	  double  x,y;
	   int   speed;
	  int  width, height;
	
	public abstract void  drawSelf(Graphics  g);

	public GameObject(Image img, double x, double y, int speed, int width, int height) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
	}

	public GameObject(Image img, double x, double y) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
	}
	
	public GameObject() {
	}
	
	
	public Rectangle getRect(){
		return  new Rectangle((int)x, (int)y, width, height);
	}
	
	
}
