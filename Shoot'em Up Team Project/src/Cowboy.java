import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Cowboy {
	private int x;
	private int y;
	BufferedImage image;
	public Cowboy(int cowboyCount){
		
		if(cowboyCount>19){
			x=(50*(cowboyCount-20))+75;
			y=300;
		}
		else if(cowboyCount>14){
			x=(50*(cowboyCount-15))+75;
			y=250;
		}
		else if(cowboyCount>9){
			x=(50*(cowboyCount-10))+75;
			y=200;
		}
		else if(cowboyCount>4){
			x=(50*(cowboyCount-5))+75;
			y=150;
		}
		else{
			x=(50*cowboyCount)+75;
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
