package top.wavin.snake.comp;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class FoodBlock extends JButton{
	
	public class Position{
		private int x;
		private int y;
		public Position() {
		}
		
		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		
	}
	
	private Position postion = new Position();
	
	private int inMapLocation = 0;
	
	private boolean isFood = false;
	private boolean isSnake = false;
	private boolean isWall = false;
	
	private ImageIcon blank = new ImageIcon("res/blank.png");
	private ImageIcon food = new ImageIcon("res/food.png");
	private ImageIcon wall = new ImageIcon("res/wall.png");
	private ImageIcon snake = new ImageIcon("res/snake.png");

	public FoodBlock() {
		this.setEnabled(false);
		this.setDisabledIcon(blank);
		this.setIcon(blank);
		this.setPreferredSize(new Dimension(blank.getIconWidth() - 8, blank.getIconHeight() - 8));
	}
	
	public void setFood(boolean isFood) {
		this.isFood = isFood;
		if(isFood) {
			this.setDisabledIcon(food);
			return;
		}
		this.setDisabledIcon(blank);
	}
	
	public void setSnake(boolean isSnake) {
		this.isSnake = isSnake;
		if(isSnake) {
			this.setDisabledIcon(snake);
			return;
		}
		this.setDisabledIcon(blank);
	}
	
	public void setWall(boolean isWall) {
		this.isWall = isWall;
		if(isWall) {
			this.setDisabledIcon(wall);
			return;
		}
		this.setDisabledIcon(blank);
	}
	
	public void reset() {
		this.isFood = false;
		this.isSnake = false;
		this.setDisabledIcon(blank);
	}

	public void setPosition(int x, int y) {
		postion.setX(x);
		postion.setY(y);
	}
	
	public Position getPosition() {
		return this.postion;
	}
	
	public void setMapLocation(int location) {
		this.inMapLocation = location;
	}
	
	public int getMapLocaton() {
		return this.inMapLocation;
	}

	public boolean isFood() {
		return this.isFood;
	}
	
	public boolean isSnake() {
		return this.isSnake;
	}
	
	public boolean isWall() {
		return this.isWall;
	}

	
}
