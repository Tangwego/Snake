package top.wavin.snake.utils;

import top.wavin.snake.CallBack;

public class TaskTimer implements Runnable {

    private long timeInterval = 1000;
    private Thread thread;
    private CallBack callBack;

    public TaskTimer(long delay){
        this.timeInterval = delay;
        thread = new Thread(this);
        thread.start();
    }

    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }

    public void cancel(){
        if(thread != null){
            thread.stop();
            thread = null;
        }
    }

    @Override
    public void run() {
        while(true){
            callBack.execute(null);
            try {
                Thread.sleep(timeInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
