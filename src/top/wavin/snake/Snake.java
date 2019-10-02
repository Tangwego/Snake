package top.wavin.snake;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import top.wavin.snake.comp.FoodBlock;

public class Snake {
	
	public enum Direction{
		UP, DOWN, LEFT, RIGHT
	};
	
	public enum Status{
		DIED, FULL, ALIVE,EATING
	};
	
	private Map<Integer, FoodBlock> snake = new HashMap<Integer, FoodBlock>();
	
	private int length = 0;
	
	private Direction headDirection = Direction.RIGHT;
	
	private Status snakeStatus = Status.ALIVE;

	private int score = 0;
	
	public Snake() {
		this.score = 0;
	}
	
	public int init(Map<Integer, FoodBlock> map, int loc, int len) {
		int i = loc;
		while(true){
			if(i<=(loc - len)) break;
			FoodBlock block = map.get(i);
			int bx = block.getPosition().getX();
			int by = block.getPosition().getY();
			if(bx == 0 || bx == 19 || by == 0 || by == 19) {
				if(!block.isWall()) {
					block.reset();
				}
				Random rand = new Random();
				i = rand.nextInt(400)+1;
				loc = i;
				this.snakeReset();
				continue;
			}
			block.setSnake(true);
			this.length ++;
			snake.put(this.length, block);
			i--;
		}
		return i+len;
	}
	
	public void eating(FoodBlock block) {
		this.length ++;
        Map<Integer, FoodBlock> tmp = new HashMap<Integer, FoodBlock>();
		for (int i=1; i <= snake.size(); i++){
            tmp.put(i+1,snake.get(i));
        }
        tmp.put(1,block);
        this.snake = tmp;
	}
	
	public Status walk(FoodBlock block) {
		if(block.isSnake() || block.isWall()) {
            System.out.println("Snake died");
			snakeStatus = Status.DIED;
			return this.snakeStatus;
		}
		if(block.isFood()) {
			//��ʳ��
            score = score + 1;
            block.setSnake(true);
			eating(block);
            snakeStatus = Status.EATING;
		}else {
			removeLast();
			block.setSnake(true);
			this.length ++;
			snake.put(1, block);
            snakeStatus = Status.ALIVE;
		}
		return this.snakeStatus;
	}

	public void snakeReset(){
	    for(int i=1; i<=snake.size();i++){
	        FoodBlock tmp = snake.get(i);
	        tmp.reset();
        }
        snake.clear();
	    this.score = 0;
	    this.length = 0;
    }
	
	public void removeLast() {
		Map<Integer, FoodBlock> tmp = new HashMap<Integer, FoodBlock>();
		for (int i=1; i <= snake.size(); i++) {
			FoodBlock blk = snake.get(i);
			tmp.put(i+1, blk);
		}
		tmp.get(snake.size()+1).reset();
		tmp.remove(snake.size()+1);
		this.snake = tmp;
		this.length --;
	}

	public int getLength() {
		return this.length;
	}
	
	public FoodBlock getHead() {
		return snake.get(1);
	}
	
	public Direction getHeadDirection() {
		return this.headDirection;
	}
	
	public void setHeadDirection(Direction direction) {
		this.headDirection = direction;
	}

    public void setStatus(Status status){
        this.snakeStatus = status;
    }

	public Status getStatus(){
	    return this.snakeStatus;
    }

    public int getScore(){
	    return this.score;
    }
}
