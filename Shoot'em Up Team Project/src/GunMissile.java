import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GunMissile implements Runnable{
	private int xStart;
	private int yStart;
	BufferedImage image;
	private Thread shoot;

	public GunMissile(int x)
	{
		yStart=400;
		xStart=x;
		try {
			java.net.URL imageURL = GunMissile.class
					.getResource("/image/bullet.jpg");
			image = ImageIO.read(imageURL);
		} catch (IOException ioe) {System.out.println("Error: Failed to load image.");
		}
		shoot= new Thread(this);
		shoot.start();
	}	
	@Override
	public void run() {
		while(yStart>0)
		{
			for(int i=yStart;i>=-15;i-=5)
			{
				moveUp();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) { }
			}
		}
	}
	public int getXStart()
	{
		return xStart;
	}
	
	public int getYStart()
	{
		return yStart;
	}
	
	public void moveUp()
	{
		yStart-=5;
	}
	public BufferedImage getImage() {
		return image;
	}
}
