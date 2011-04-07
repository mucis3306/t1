import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;



public class Controller implements Runnable {
	private View view;
	private Gun gun;
	private HitDetect detect;
	private Thread runAliens;
	private Thread hitDetect;
	private int alienCount;
	private Vector<GunMissile> missile = new Vector<GunMissile>();
	private Vector<Alien> aliens=new Vector<Alien>();
	private boolean setupDone=false;
	
	public Controller(View view)
	{
		this.view=view;
		gun = new Gun();
		alienCount=5;
		for(int i = 0; i<alienCount;i++)
		{
			aliens.add(new Alien(i));
		}
		setupGame();
		setupListener();
		runAliens = new Thread(this);
		detect = new HitDetect(aliens, missile);
		hitDetect = new Thread(detect);
		hitDetect.start();
		refresh();
	}
	void setupGame(){
		view.drawlingPanel.setup(gun,aliens,missile,setupDone);
		setupDone=true;
		
	}
	void refresh(){
		view.drawlingPanel.refresh();
	}
	private void setupListener(){
		view.addKeyListener(new MyKeyListener());
	}
	
	public void removeAlien(int x){
		aliens.remove(x);
		alienCount--;
	}
	public void removeMissile(){
		missile.remove(0);
		missile.trimToSize();
	}
	public Vector<GunMissile> getMissile(){
		return missile;
	}
	public Vector<Alien> getAliens(){
		return aliens;
	}
	
	public class HitDetect implements Runnable{
		
		private Vector<GunMissile> missile = new Vector<GunMissile>();
		private Vector<Alien> aliens=new Vector<Alien>();
		public HitDetect( Vector<Alien> aliens, Vector<GunMissile> missile){
			this.missile=missile;
			this.aliens=aliens;
			
		}
		@Override
		public void run() {
			while(true){
				for(int i=0;i<(missile.size()-1);i++)
				{
					System.out.println("missile.size()="+missile.size());
					for(int j=0;j<aliens.size();j++)
					{
						reGet();
						System.out.println("i="+i);
						System.out.println("j="+j);
						if(missile.get(i).getXStart()>=aliens.get(j).getX()&&
								missile.get(i).getXStart()<(aliens.get(j).getX()+20))
						{
							if(missile.get(i).getYStart()<=aliens.get(j).getY()&&
									missile.get(i).getYStart()>(aliens.get(j).getY()-5))
							{
								removeMissile();
								removeAlien(j);
								reGet();
							}
						}
					}
				}
			}
		}
		private void reGet(){
			this.aliens=getAliens();
			this.missile=getMissile();
		}
	}
	
	@Override
	public void run() {
		int count=1;
		while(true)
		{
			for(int j=0;j<60;j++)
			{	
				for(int i = 0;i<alienCount;i++)
				{
				//	System.out.println("count="+count+"\ncount%2="+count%2);
					
					if (j<30) {
						aliens.get(i).moveRight(5);
					}
					else
						aliens.get(i).moveLeft(5);
				}
				refresh();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) { }
			}
			for(int i = 0;i<alienCount;i++)
			{
				if(count%2==0)
				aliens.get(i).moveDown(5);
			}
			count++;
		}
	}
	class MyKeyListener extends KeyAdapter {
		
		public void keyPressed(KeyEvent ke) {
			int keyCode = ke.getKeyCode();
			
			if (keyCode == KeyEvent.VK_RIGHT) {
				if (gun.getX()<=400&&setupDone) {
					gun.moveRight(8);
				}
			}
			else if (keyCode == KeyEvent.VK_LEFT) {
				if (gun.getX()>=0&&setupDone){
					gun.moveLeft(8);
				}
			
			}
			else if(keyCode == KeyEvent.VK_UP) {
				if(setupDone)
				{
					if(missile.size()>15)
					{
						missile.remove(0);
						missile.trimToSize();
					}
					missile.add(new GunMissile(gun.getX()+50));
				}
			}
			else if(keyCode == KeyEvent.VK_ENTER)
			{
				setupGame();
				runAliens.start();
			}
			refresh();
		}
	}

}
