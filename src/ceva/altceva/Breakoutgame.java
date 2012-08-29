package ceva.altceva;

public class Breakoutgame extends Thread {
	
	private long mTime;
	private boolean mRun;
	private Tst1Activity.MyView mView;
	
	public synchronized void setTime(long time) {
		mTime = time;
	}
	public synchronized void setRun(boolean run){
		mRun = run;
	}
	public synchronized boolean running(){
		return mRun;
	}
	
	public Breakoutgame(long time, Tst1Activity.MyView view) {
		setTime(time);
		setRun(true);
        mView = view;
        
	}
	
    public void run() {
		try  {
			while (running())
			{
				Thread.sleep(mTime);
				mView.loop();
				mView.invokeDraw();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}

