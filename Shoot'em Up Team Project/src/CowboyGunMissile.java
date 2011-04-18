import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class CowboyGunMissile implements Runnable{
	private int xStart;
	private int yStart;
	BufferedImage image;
	private Thread shoot;

	public CowboyGunMissile(int x, int y)
	{
		yStart=y;
		xStart=x;
		try {
			java.net.URL imageURL = CowboyGunMissile.class
					.getResource("/image/bulletdown.jpg");
			image = ImageIO.read(imageURL);
		} catch (IOException ioe) {System.out.println("Error: Failed to load image.");
		}
		shoot= new Thread(this);
		shoot.start();
	}	
	@Override
	public void run() {
		while(yStart<500)
		{
			for(int i=yStart;i<=500;i+=5)
			{
				moveDown();
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
	
	public void moveDown()
	{
		yStart+=5;
	}
	public BufferedImage getImage() {
		return image;
	}
}

