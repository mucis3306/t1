import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Alien {
	private int x;
	private int y;
	BufferedImage image;
	public Alien(int alienCount){
		x=(50*alienCount)+75;
		y=100;
		try {
			java.net.URL imageURL = Gun.class
					.getResource("/image/alien.jpg");
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
	public void moveDown(int yMove){
		y+=yMove;
	}
	public BufferedImage getImage() {
		return image;
	}
	public String toString(){
		return "X="+x+"Y="+y;
	}
}
