

public class SpaceInvaderApp {

	public SpaceInvaderApp() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				View view = new View();
				new Controller(view);
			}
		});
	}
	public static void main(String[] args) {
		new SpaceInvaderApp();

	}

}
