import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

public class DrawlingPanel extends JPanel {
	private Gun gun;
	private Alien[] alien;
	private Vector<GunMissile> missile;
	
	public DrawlingPanel() {
		setPreferredSize(new Dimension(500,
				500));
		setBackground(Color.BLACK);
	}
	public void setup(Gun gun, Alien[] alien, Vector<GunMissile> missile){
		this.gun=gun;
		this.alien=alien;
		this.missile=missile;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGun(gun,g);
		drawAlien(alien, g);
		drawMissile(missile, g);
		
	}
	public void refresh(){
		repaint();
	}
	public void drawGun(Gun gun, Graphics g){
		g.drawImage(gun.getImage(), gun.getX(), gun.getY(), gun.getX()+100, gun.getY()+50, 0,
				0, 100, 100, null);
	}
	public void drawAlien(Alien[] alien, Graphics g){
		for(int i = 0;i<alien.length;i++)
		{
			/* System.out.println("alien.length="+alien.length);
			System.out.println("i="+i);
			System.out.println(alien[i].toString()); */
			g.drawImage(alien[i].getImage(), alien[i].getX(), alien[i].getY(), alien[i].getX()+100, alien[i].getY()+50, 0,
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
