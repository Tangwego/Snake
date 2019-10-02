package top.wavin.snake;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

import top.wavin.snake.Snake.Direction;
import top.wavin.snake.Snake.Status;
import top.wavin.snake.comp.FoodBlock;
import top.wavin.snake.utils.TaskTimer;

public class SnakeMain {
    private TaskTimer timer;
    private int delay = 400;
    private void resetTimer(){
        if(timer != null){
            timer.cancel();
        }
        timer = new TaskTimer(delay);
    }

    private void clearTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
	class SnakeKeyEvent extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
            Direction direction = snake.getHeadDirection();
			switch(e.getKeyCode()) {
			    case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
                    if( direction == Direction.DOWN)
                    {
                        System.out.println("不能往相反方向移动");
                        return;
                    }
                    resetTimer();
					timer.setCallBack(new CallBack() {
                        @Override
                        public Object execute(Object[] params) {
                            doUP();
                            return null;
                        }
                    });
                    break;
                case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
                    if( direction == Direction.UP)
                    {
                        System.out.println("不能往相反方向移动");
                        return;
                    }
                    resetTimer();
                    timer.setCallBack(new CallBack() {
                        @Override
                        public Object execute(Object[] params) {
                            doDOWN();
                            return null;
                        }
                    });
					break;
                case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
                    if( direction == Direction.RIGHT)
                    {
                        System.out.println("不能往相反方向移动");
                        return;
                    }
                    resetTimer();
                    timer.setCallBack(new CallBack() {
                        @Override
                        public Object execute(Object[] params) {
                            doLEFT();
                            return null;
                        }
                    });
					break;
                case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
                    if( direction == Direction.LEFT)
                    {
                        System.out.println("不能往相反方向移动");
                        return;
                    }
                    resetTimer();
                    timer.setCallBack(new CallBack() {
                        @Override
                        public Object execute(Object[] params) {
                            doRIGHT();
                            return null;
                        }
                    });
					break;
				default:break;
			}
		}
	}

	private JFrame frame;
	private Container container;

	private JPanel main;
	private JPanel topPan;
    private JLabel scoreLb,lengthLb;

	private Map<Integer, FoodBlock> blocks = new HashMap<Integer, FoodBlock>();
	
	private Snake snake = new Snake();

	public SnakeMain(String title, int width, int height) {
		frame = new JFrame();
		container = frame.getContentPane();

		initFrame(title, width, height);
		addComponents();
		addListeners();
		show();
	}

	private void initFrame(String title, int width, int height) {
		GridLayout gridLayout = new GridLayout(20, 20);

		frame.setTitle(title);
		frame.setSize(width, height);
		frame.setAlwaysOnTop(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setLocationRelativeTo(null);

		int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame.getWidth()) / 2;
		int y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frame.getHeight()) / 2;

		frame.setLocation(x, y);
		frame.setResizable(false);
		frame.setVisible(true);

		main = new JPanel();
		main.setLayout(gridLayout);

		int dx = 0;
		int dy = 0;
		for (int i = 1; i <= 400; i++) {
			FoodBlock block = new FoodBlock();
			block.setMapLocation(i);
			block.setPosition(dx, dy);
			dx ++;
			if((i%20) == 0) {
				dy++;
				dx = 0;
			}
			block.reset();
			blocks.put(i, block);
			main.add(block);
		}
        topPan = new JPanel();
        scoreLb = new JLabel("分数:"+snake.getScore());
        lengthLb = new JLabel("蛇长度:" + snake.getLength());

        onReset();
        initSnake();
        initFood();
	}

	public void printLocationInfo(){
	    for(int i = 1; i<=blocks.size();i++){
	        FoodBlock block = blocks.get(i);
	        if((i%20) == 0){
                System.out.println("Location=" + block.getMapLocaton() + ", isFood="+block.isFood()+", isSnake="+block.isSnake());
            }else{
                System.out.print("Location=" + block.getMapLocaton() + ", isFood="+block.isFood()+", isSnake="+block.isSnake()+" ");
            }
        }
    }
	
	private void addComponents() {
        topPan.add(scoreLb);
        topPan.add(lengthLb);

	    container.add(topPan,BorderLayout.NORTH);
		container.add(main, BorderLayout.CENTER);
		frame.pack();
	}

	private void addListeners() {
		frame.addKeyListener(new SnakeKeyEvent());
	}
	
	public void initSnake() {
		Random rand = new Random();
		int a = rand.nextInt(400)+1;
		int loc = snake.init(blocks,a,3);

		System.out.println("蛇头位置:"+loc);
        snake.setHeadDirection(Direction.RIGHT);
        lengthLb.setText("蛇长度:"+snake.getLength());
		System.out.println("蛇当前长度为:"+snake.getLength());
	}

	public void initFood(){
        while(true){
			Random rand = new Random();
			int a = rand.nextInt(400)+1;
			FoodBlock food = blocks.get(a);
			if((!food.isWall()) && (!food.isSnake())){
				food.setFood(true);
				break;
			}else{
			    continue;
			}
		}
    }

    public void onReset(){
        for(int i=1; i<=blocks.size();i++) {
            FoodBlock tmp = blocks.get(i);
            int tx = tmp.getPosition().getX();
            int ty = tmp.getPosition().getY();
            if((tx == 0) || (tx == 19)) {
                tmp.setWall(true);
                continue;
            }
            if((ty == 0) || (ty == 19)) {
                tmp.setWall(true);
                continue;
            }
            tmp.reset();
        }
        scoreLb.setText("分数:0");
        lengthLb.setText("蛇长度:0");
    }

    public void onDirection(Direction direction){
        switch (direction){
            case UP:doUP();break;
            case DOWN:doDOWN();break;
            case LEFT:doLEFT();break;
            case RIGHT:doRIGHT();break;
            default:break;
        }
    }

    public void onDied(Direction direction){
        snake.setHeadDirection(direction);
        System.out.println("蛇死亡");
        snake.setStatus(Snake.Status.DIED);
        System.out.println("游戏结束");
        onGameOver(false);
        System.out.println("清除定时器");
        clearTimer();

    }

    public void onGameOver(boolean isWin){
        System.out.println("游戏结束");
        Object[] options = new Object[]{"重玩","退出"};
        int selected = JOptionPane.showOptionDialog(frame,
                isWin?"你赢了\n分数:"+snake.getScore():"游戏结束\n分数:"+snake.getScore(),
                "贪吃蛇",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options ,
                options[0]
        );
        if(selected >= 0){
            System.out.println("点击了: "+options[selected]);
            if (selected == 0){
                snake.snakeReset();
                onReset();
                initSnake();
                initFood();
            }else{
                System.exit(0);
            }
        }
    }

    public void onStatusChanged(Status status){
        switch (status){
            case DIED:
                onDied(snake.getHeadDirection());
                break;
            case EATING:
                scoreLb.setText("分数:"+snake.getScore());
                lengthLb.setText("蛇长度:"+snake.getLength());
                initFood();
                break;
            case ALIVE:
                break;
            case FULL:
                break;
                default:break;
        }
    }
	
	public void doUP() {
	    Direction direction = snake.getHeadDirection();
		if( direction == Direction.DOWN)
		{
			System.out.println("不能往相反方向移动");
			return;
		}
		FoodBlock tmp = snake.getHead();
		int loc = tmp.getMapLocaton();
		loc = loc - 20;
		FoodBlock next = blocks.get(loc);
        if(next.isWall() || next.isSnake()) {
            onDied(direction);
            return;
        }
        snake.setHeadDirection(Direction.UP);
        Status status = snake.walk(next);
        onStatusChanged(status);
	}
	
	public void doDOWN() {
        Direction direction = snake.getHeadDirection();
		if(direction == Direction.UP)
		{
			System.out.println("不能往相反方向移动");
			return;
		}
		FoodBlock tmp = snake.getHead();
		int loc = tmp.getMapLocaton();
		loc = loc + 20;
		FoodBlock next = blocks.get(loc);
		if(next.isWall() || next.isSnake()) {
            onDied(direction);
			return;
		}
        snake.setHeadDirection(Direction.DOWN);
        Status status = snake.walk(next);
        onStatusChanged(status);
	}
	
	public void doLEFT() {
        Direction direction = snake.getHeadDirection();
		if(direction == Direction.RIGHT)
		{
			System.out.println("不能往相反方向移动");
			return;
		}
		FoodBlock tmp = snake.getHead();
		int loc = tmp.getMapLocaton();
		loc = loc - 1;
		FoodBlock next = blocks.get(loc);
        if(next.isWall() || next.isSnake()) {
            onDied(direction);
            return;
        }
        snake.setHeadDirection(Direction.LEFT);
        Status status = snake.walk(next);
        onStatusChanged(status);
	}
	
	public void doRIGHT() {
        Direction direction = snake.getHeadDirection();
		if(direction == Direction.LEFT)
		{
			System.out.println("不能往相反方向移动");
			return;
		}

		FoodBlock tmp = snake.getHead();
		int loc = tmp.getMapLocaton();
		loc = loc + 1;
		FoodBlock next = blocks.get(loc);
        if(next.isWall() || next.isSnake()) {
            onDied(direction);
            return;
        }
        snake.setHeadDirection(Direction.RIGHT);
        Status status = snake.walk(next);
        onStatusChanged(status);
	}

	public void show() {
		if (frame != null) {
			frame.show();
		}
	}

	public static void main(String[] args) {
		SnakeMain snake = new SnakeMain("贪吃蛇", 500, 500);
	}

}
