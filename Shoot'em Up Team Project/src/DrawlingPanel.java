import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

public class DrawlingPanel extends JPanel {
	private Gun gun;
	private PistolImage pistol=new PistolImage();
	private Vector<Cowboy> cowboys=new Vector<Cowboy>();
	private Vector<GunMissile> missile;
	private Vector<CowboyGunMissile> cowboymissile;
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
			, boolean paused, boolean gameOver, boolean gameOverLose, Vector<CowboyGunMissile> cowboymissile){
		this.gun=gun;
		this.cowboys=cowboys;
		this.missile=missile;
		this.gameStarted=gameStarted;
		this.paused=paused;
		this.gameOver=gameOver;
		this.gameOverLose=gameOverLose;
		this.cowboymissile = cowboymissile;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!gameStarted){
			drawMenu(g);
		}
		if(gameStarted&&!paused&&!gameOver&&!gameOverLose){
			drawGun(gun,g);
			drawAlien(cowboys, g);
			drawMissile(missile, g);
			drawCowboyMissile(cowboymissile, g);
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
		Font font = new Font("Arial", Font.BOLD, 40);
		g.setFont(font);
		g.setColor(new Color(210, 84, 0));
		g.drawString("Team 1", 180, 100);
		font = new Font("Arial", Font.BOLD, 50);
		g.setFont(font);
		g.drawImage(pistol.getLeftPistol(),10, 70, 160, 160, 0,
				0, 150, 90, null);
		g.drawImage(pistol.getRightPistol(), 340, 70, 490, 160, 0,
				0, 150, 90, null);
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
		g.setColor(new Color(210, 84, 0));
		g.drawString("PAUSED", 175, 220);
		font = new Font("Arial", Font.BOLD, 20);
		g.setFont(font);
		g.drawString("Press [ESC] again to resume", 120, 250);
	}
	public void drawWin(Graphics g){
		Font font = new Font("Arial", Font.BOLD, 40);
		g.setFont(font);
		g.setColor(new Color(210, 84, 0));
		g.drawString("This town ain't big", 15, 100);
		g.drawString("enough for the both of us!", 15, 140);
		font = new Font("Arial", Font.BOLD, 20);
		g.setFont(font);
		g.drawString("You Won", 210, 180);
		g.drawString("Press [ENTER] to restart",140,300);
	}
	public void drawLose(Graphics g){
		Font font = new Font("Arial", Font.BOLD, 60);
		g.setFont(font);
		g.setColor(new Color(210, 84, 0));
		g.drawString("Slow Draw", 100, 200);
		g.drawString("You Lose", 120, 260);
		font = new Font("Arial", Font.BOLD, 20);
		g.setFont(font);
		g.drawString("Press [ENTER] to restart",140,300);
	}
	public void drawGun(Gun gun, Graphics g){
		g.drawImage(gun.getImage(), gun.getX(), gun.getY(), gun.getX()+100, gun.getY()+50, 0,
				0, 100, 100, null);
	}
	public void drawAlien(Vector<Cowboy> cowboys, Graphics g){
		for(int i = 0;i<cowboys.size();i++)
		{
			g.drawImage(cowboys.get(i).getImage(), cowboys.get(i).getX(), cowboys.get(i).getY(), cowboys.get(i).getX()+100, cowboys.get(i).getY()+50, 0,
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
	public void drawCowboyMissile(Vector<CowboyGunMissile> missile, Graphics g) {
		for(int i = 0; i < missile.size(); i++) {
			g.drawImage(missile.elementAt(i).getImage(), missile.elementAt(i).getXStart(), missile.elementAt(i).getYStart(), missile.elementAt(i).getXStart() + 50, missile.elementAt(i).getYStart() + 100, 0, 
				0, 50, 100, null);
		}
	}
}
