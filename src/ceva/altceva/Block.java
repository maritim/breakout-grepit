package ceva.altceva;

import android.graphics.Bitmap;
import android.util.Log;

public class Block 
{
	private int xCoord;
	private int yCoord;
	//private int health;
	//private int damage;
	private int xSize = 80;
	private int ySize = 40;
	private int exist;
	private Bitmap mImage;
	private static final String TAG = "Tst1Activity";
	
	public Block(int xCoord,int yCoord,Bitmap mImage) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		
		this.mImage = Bitmap.createScaledBitmap(mImage, xSize , ySize, true);
		
		exist = 1;
	}
	
	public Bitmap putImage() {
		return mImage;
	}
	
	public int CoordX() {
		return xCoord;
	}
	
	public int CoordY() {
		return yCoord;
	}
	
	public int getExist() {
		return exist;
	}
	
	public void setExist(int exist) {
		this.exist = exist;
	}
	
	public int isHited(int x,int y,int dimX,int dimY) {
		
		if(x+dimX >= xCoord && x <= xCoord+xSize && y+dimY >= yCoord && y <= yCoord+ySize) {
		
			Log.i(TAG,"Collision !!!");		
			if(x+dimX > xCoord+xSize)
				return 1;
			else if(x < xCoord)
				return 3;
			else if(y+dimY > yCoord+ySize)
				return 2;
			
			return 4;
		}
		
		return 0;
	}
}