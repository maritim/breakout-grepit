package ceva.altceva;

import android.graphics.Bitmap;

public class Explosion {

	private Bitmap mImage;
	private int time;
	private int xCoord;
	private int yCoord;
	private int xSize = 100;
	private int ySize = 100;
	
	public Explosion(Bitmap mImage,int xCoord,int yCoord,int time)
	{
		this.time = time;
		this.xCoord = xCoord;
		this.yCoord = yCoord;

		this.mImage = Bitmap.createScaledBitmap(mImage, xSize , ySize, true);
	}
	
	public void proDecrease()
	{
		if(time >= 0)
			-- time;
	}
	
	public int getTime()
	{
		return time;
	}
	
	public Bitmap getImage()
	{
		return this.mImage;
	}
	
	public boolean isVisible()
	{
		if(time >= 0)
			return true;
		return false;
	}
	
	public int getCoordX()
	{
		return xCoord;
	}
	
	public int getCoordY()
	{
		return yCoord;
	}
}
