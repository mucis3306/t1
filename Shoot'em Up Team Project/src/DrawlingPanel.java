import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

public class DrawlingPanel extends JPanel {
	private Gun gun;
	private Vector<Alien> aliens=new Vector<Alien>();
	private Vector<GunMissile> missile;
	private boolean setupDone;
	
	public DrawlingPanel() {
		setPreferredSize(new Dimension(500,
				500));
		setBackground(Color.BLACK);
	}
	public void setup(Gun gun, Vector<Alien>aliens, Vector<GunMissile> missile,boolean setupDone){
		this.gun=gun;
		this.aliens=aliens;
		this.missile=missile;
		this.setupDone=setupDone;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!setupDone){
			drawMenu(g);
		}
		
		if(setupDone){
			drawGun(gun,g);
			drawAlien(aliens, g);
			drawMissile(missile, g);
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
		g.drawString("Shoot'em Up", 90, 150);
		font = new Font("Arial", Font.BOLD, 20);
		g.setFont(font);
		g.drawString("Press [Enter] to Start", 145, 200);
	}
	public void drawGun(Gun gun, Graphics g){
		g.drawImage(gun.getImage(), gun.getX(), gun.getY(), gun.getX()+100, gun.getY()+50, 0,
				0, 100, 100, null);
	}
	public void drawAlien(Vector<Alien> aliens, Graphics g){
		for(int i = 0;i<aliens.size();i++)
		{
			/* System.out.println("alien.length="+alien.length);
			System.out.println("i="+i);
			System.out.println(alien[i].toString()); */
			g.drawImage(aliens.get(i).getImage(), aliens.get(i).getX(), aliens.get(i).getY(), aliens.get(i).getX()+100, aliens.get(i).getY()+50, 0,
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
