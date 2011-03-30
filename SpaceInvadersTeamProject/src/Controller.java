import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;



public class Controller implements Runnable {
	private View view;
	private Gun gun;
	private Thread runAliens;
	private int alienCount;
	private Vector<GunMissile> missile = new Vector<GunMissile>();
	private Alien[] aliens=new Alien[5];
	
	public Controller(View view)
	{
		this.view=view;
		gun = new Gun();
		alienCount=5;
		for(int i = 0; i<alienCount;i++)
		{
			aliens[i]= new Alien(i);
		}
		setupGame();
		setupListener();
		runAliens = new Thread(this);
		runAliens.start();
		refresh();
	}
	void setupGame(){
		view.drawlingPanel.setup(gun,aliens,missile);
		
	}
	void refresh(){
		view.drawlingPanel.refresh();
	}
	private void setupListener(){
		view.addKeyListener(new MyKeyListener());
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
						aliens[i].moveRight(5);
					}
					else
						aliens[i].moveLeft(5);
				}
				refresh();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) { }
			}
			for(int i = 0;i<alienCount;i++)
			{
				if(count%2==0)
				aliens[i].moveDown(5);
			}
			count++;
		}
	}
	class MyKeyListener extends KeyAdapter {
		
		public void keyPressed(KeyEvent ke) {
			int keyCode = ke.getKeyCode();
			
			if (keyCode == KeyEvent.VK_RIGHT) {
				if (gun.getX()<=400) {
					gun.moveRight(8);
				}
			}
			else if (keyCode == KeyEvent.VK_LEFT) {
				if (gun.getX()>=0){
					gun.moveLeft(8);
				}
			
			}
			else if(keyCode == KeyEvent.VK_UP) {
				System.out.println("done");
					missile.add(new GunMissile(gun.getX()+50));
			}
			refresh();
		}
	}

}
