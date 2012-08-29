package ceva.altceva;

import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Tst1Activity extends Activity {
    
	private View mView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mView = new MyView(this);
        setContentView(mView);
    }
    
    public class MyView extends View {
    	
    	private Vector<Block> blocuri = new Vector<Block>(); 
    	private Vector<Explosion> explozii = new Vector<Explosion>();
    	
    	private Handler mHandler;
        
    	//private Bitmap mBuffer;
        //private Bitmap mBitmap;
    	//private Bitmap mDalvik;
    	private Bitmap mPlatform;
    	private Bitmap mBackground;
    	private Bitmap mBall;
    	private Bitmap mBrick;
    	private Bitmap mExplosion;
    	
    	private int mX, mY;
    	private int bX,bY;
    	private int dX = -1,dY = -1;
    	private int sX,sY;
    	private int dimX = 32,dimY = 32;
    	private Paint paint;
    	private boolean touched;
    	private int dBlocks;
    	
    	private Breakoutgame mGame;
    	
    	public MyView(Context context) {
			super(context);
			
			mHandler = new Handler();
			
            mPlatform = BitmapFactory.decodeResource(getResources(),R.drawable.paddle);
            mPlatform = Bitmap.createScaledBitmap(mPlatform,80,40,true);//) getResizedBitmap(mPlatform,150,80);
            mBackground = BitmapFactory.decodeResource(getResources(),R.drawable.wood);
            mBackground = getResizedBitmap(mBackground,Screen_Height(),Screen_Width());
            mBall = BitmapFactory.decodeResource(getResources(),R.drawable.ball1);
            mBall = Bitmap.createScaledBitmap(mBall, dimX, dimY, true);
            mExplosion = BitmapFactory.decodeResource(getResources(),R.drawable.explosion);
            mExplosion = Bitmap.createScaledBitmap(mExplosion, 100, 100, true);
            
			mGame = new Breakoutgame(5, this);
			mGame.start();
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
			sX = Screen_Width();
			sY = Screen_Height();
			
			mX = sX/2;
			mY = sY - mPlatform.getHeight()-10;
			
			bX = sX/2-dimX/2;
			bY = sY - 100;
			
			CreateBlocks();
			
			paint = new Paint();
    	}
    	
    	public int Screen_Height()
    	{
    		DisplayMetrics dimensions = new DisplayMetrics();
    		getWindowManager().getDefaultDisplay().getMetrics(dimensions);
    		
    		return dimensions.heightPixels;
    	}
    	
    	public int Screen_Width()
    	{
    		DisplayMetrics dimensions = new DisplayMetrics();
    		getWindowManager().getDefaultDisplay().getMetrics(dimensions);
    		
    		return dimensions.widthPixels;
    	}
    	
    	public void VerifyCollision()
    	{
    		if(bX < 0 || bX+dX > sX-dimX)
    			dX *= -1;
    			//bX = sX;
    		if(bY < 0)
    			dY *= -1;
    			//bY = Sy
    		if(bY+dY >= mY-dimY && bX+dX >= mX && bX+dX <= mX+mPlatform.getWidth())
    			dY *= -1;
    	}
    	
    	private void VerifiyBlockCollision(){
    		
    		for(int i=0;i<blocuri.size();i++)
    			if(blocuri.elementAt(i).getExist() == 1)
    				if(blocuri.elementAt(i).isHited(bX, bY,dimX,dimY) != 0)
    				{
    					--dBlocks;
    					
    					blocuri.elementAt(i).setExist(0);
    					
    					Block bloc = blocuri.elementAt(i);
    					
    					int a = bloc.isHited(bX,bY,dimX,dimY);
    					
    					if(a == 1 || a == 3)
    						dX *= -1;
    					else
    						dY *= -1;
    					explozii.add(new Explosion(mExplosion,bX-20,bY-20,255));
    					return ;
    				}
    	}
    	
    	public void loop()
    	{
    		VerifyCollision();
    		
    		if(touched == true)
    		{
    			bX += dX;
    			bY += dY;
    		}
    		
    		if(bY >= sY || dBlocks == 0)
    		{
    			bY = sY-100;
    			bX = sX/2-dimX/2;
    			dX = dY = -1;
    			
    			CreateBlocks();
    			
    			return ;
    		}
    		
    		for(int i=0;i<explozii.size();i++)
    			explozii.elementAt(i).proDecrease();
    		
    		VerifiyBlockCollision();
    	}
    		
    	public void invokeDraw() {
			mHandler.post(new Runnable() {
				public void run() {
					invalidate();
				}
			});
    	}
    	
    	public void CreateBlocks()
    	{
    		dBlocks = 25;
    		
    		touched = false;
    		mX = sX/2-mPlatform.getWidth()/2;
    		mY = sY-mPlatform.getHeight()-10; 		

    		blocuri.removeAll(blocuri);
    		
    		Random generator = new Random();
    		
    		for(int i=1;i<=5;i++)
    			for(int j=1;j<=5;j++)
    			{
    				int a = generator.nextInt()%4+1;
    				
    				switch(a)
    				{
    				case 1 : mBrick = BitmapFactory.decodeResource(getResources(),R.drawable.block1);
    						break;
    				case 2 : mBrick = BitmapFactory.decodeResource(getResources(),R.drawable.block2);
							break;
    				case 3 : mBrick = BitmapFactory.decodeResource(getResources(),R.drawable.block3);
							break;
    				default : mBrick = BitmapFactory.decodeResource(getResources(),R.drawable.block4);
							break;
    				}
    				blocuri.add(new Block((i-1)*80+i*10,(j-1)*40+j*10,mBrick));
    			}
    	}
    
    	@Override
        protected void onDraw(Canvas canvas) {
    		canvas.drawBitmap(mBackground, 0, 0,null);
    		
    		for(int i=0;i<blocuri.size();i++)
    			if(blocuri.elementAt(i).getExist() == 1)
    				canvas.drawBitmap(blocuri.elementAt(i).putImage(), blocuri.elementAt(i).CoordX() , blocuri.elementAt(i).CoordY() , null);
    		for(int i=0;i<explozii.size();i++)
    			if(explozii.elementAt(i).isVisible())
    			{
    				paint.setAlpha(explozii.elementAt(i).getTime());
    				canvas.drawBitmap(explozii.elementAt(i).getImage(), explozii.elementAt(i).getCoordX(), explozii.elementAt(i).getCoordY(), paint);
    			}
    		
    		canvas.drawBitmap(mPlatform,mX,mY, null);
    		canvas.drawBitmap(mBall, bX, bY,null);
    		//invalidate(d);
    	}
    	

    	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) 
    	{

    		int width = bm.getWidth();
    		int height = bm.getHeight();
    		float scaleWidth = ((float) newWidth) / width;
    		float scaleHeight = ((float) newHeight) / height;
    		Matrix matrix = new Matrix();

    		matrix.postScale(scaleWidth, scaleHeight);
    		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    		return resizedBitmap;
    	}


    	
    	@Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                	touched = true;
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                	mX = (int)x-40;
                	touched = true;
                	//mY = (int)y;
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                	touched = true;
                    invalidate();
                    break;
            }
            return true;
        }
    }
}