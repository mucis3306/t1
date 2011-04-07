import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Gun {
	private int x;
	private int y;
	BufferedImage image;
	
	public Gun(){
		x=200;
		y=400;
		try {
			java.net.URL imageURL = Gun.class
					.getResource("/image/gun.jpg");
			image = ImageIO.read(imageURL);
		} catch (IOException ioe) {System.out.println("Error: Failed to load image.");
		}
		
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void moveRight(int xMove){
		x+=xMove;
	}
	public void moveLeft(int xMove){
		x-=xMove;
	}
	public BufferedImage getImage() {
		return image;
	}
}
