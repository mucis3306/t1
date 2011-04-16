import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

public class DrawlingPanel extends JPanel {
	private Gun gun;
	private Vector<Cowboy> cowboys=new Vector<Cowboy>();
	private Vector<GunMissile> missile;
	private boolean gameStarted;
	private boolean paused;
	private boolean gameOver;
	private boolean gameOverLose;
	
	public DrawlingPanel() {
		setPreferredSize(new Dimension(500,
				500));
		setBackground(Color.BLACK);
	}
	public void setup(Gun gun, Vector<Cowboy>cowboys, Vector<GunMissile> missile,boolean gameStarted
			, boolean paused, boolean gameOver, boolean gameOverLose){
		this.gun=gun;
		this.cowboys=cowboys;
		this.missile=missile;
		this.gameStarted=gameStarted;
		this.paused=paused;
		this.gameOver=gameOver;
		this.gameOverLose=gameOverLose;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!gameStarted){
			//System.out.println("Drawling Menu");
			drawMenu(g);
		}
		if(gameStarted&&!paused&&!gameOver&&!gameOverLose){
			drawGun(gun,g);
			drawAlien(cowboys, g);
			drawMissile(missile, g);
		}
		if(paused){
			drawPause(g);
		}
		if(gameOver){
			drawWin(g);
		}
		if(gameOverLose){
			drawLose(g);
		}
		
	}
	public void refresh(){
		repaint();
	}
	public void drawMenu(Graphics g){
		//System.out.println("drawMenu Done");
		Font font = new Font("Arial", Font.BOLD, 40);
		g.setFont(font);
		g.setColor(new Color(250, 165, 0));
		g.drawString("Team 1", 180, 100);
		font = new Font("Arial", Font.BOLD, 50);
		g.setFont(font);
		g.drawString("Shoot'em Up", 95, 150);
		font = new Font("Arial", Font.BOLD, 20);
		g.setFont(font);
		g.drawString("Press [Enter] to Start", 145, 200);
		g.drawString("Press [ESC] to pause during game", 95, 250);
		g.drawString("Press [LEFT] and [RIGHT] arrow keys to move", 40, 300);
		g.drawString("Press [UP]arrow key to shoot",110,350);
		font = new Font ("Arial", Font.BOLD, 12);
		g.setFont(font);
		g.drawString("Developers: Michael Getz, Robert Cox, Clint Decker, Jeffery Kennedy",50,450);
	}
	public void drawPause(Graphics g){
		Font font = new Font("Arial", Font.BOLD, 40);
		g.setFont(font);
		g.setColor(new Color(250, 165, 0));
		g.drawString("PAUSED", 175, 220);
		font = new Font("Arial", Font.BOLD, 20);
		g.setFont(font);
		g.drawString("Press [ESC] again to resume", 120, 250);
	}
	public void drawWin(Graphics g){
		Font font = new Font("Arial", Font.BOLD, 40);
		g.setFont(font);
		g.setColor(new Color(250, 165, 0));
		g.drawString("This town ain't big", 15, 100);
		g.drawString("enough for the both of us!", 15, 140);
		font = new Font("Arial", Font.BOLD, 20);
		g.setFont(font);
		g.drawString("You Won", 210, 300);
	}
	public void drawLose(Graphics g){
		Font font = new Font("Arial", Font.BOLD, 60);
		g.setFont(font);
		g.setColor(new Color(250, 165, 0));
		g.drawString("Slow Draw", 100, 200);
		g.drawString("You Lose", 120, 260);
	}
	public void drawGun(Gun gun, Graphics g){
		g.drawImage(gun.getImage(), gun.getX(), gun.getY(), gun.getX()+100, gun.getY()+50, 0,
				0, 100, 100, null);
	}
	public void drawAlien(Vector<Cowboy> cowboies, Graphics g){
		for(int i = 0;i<cowboies.size();i++)
		{
			/* System.out.println("alien.length="+alien.length);
			System.out.println("i="+i);
			System.out.println(alien[i].toString()); */
			g.drawImage(cowboies.get(i).getImage(), cowboies.get(i).getX(), cowboies.get(i).getY(), cowboies.get(i).getX()+100, cowboies.get(i).getY()+50, 0,
					0, 100, 50, null);
		}
	}
	public void drawMissile(Vector<GunMissile> missile, Graphics g)
	{
		for(int i=0;i<missile.size();i++)
		{	
			g.drawImage(missile.elementAt(i).getImage(),missile.elementAt(i).getXStart(), missile.elementAt(i).getYStart(), missile.elementAt(i).getXStart()+50, missile.elementAt(i).getYStart()+100, 0,
				0, 50, 100, null);
		}
	}
}
