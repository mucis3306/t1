import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Alien {
	private int x;
	private int y;
	BufferedImage image;
	public Alien(int alienCount){
		if(alienCount>9){
			x=(50*(alienCount-10))+75;
			y=200;
		}
		else if(alienCount>4){
			x=(50*(alienCount-5))+75;
			y=150;
		}
		else{
			x=(50*alienCount)+75;
			y=100;
		}
		try {
			java.net.URL imageURL = Gun.class
					.getResource("/image/cowboy.jpg");
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
