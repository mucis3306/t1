import java.awt.BorderLayout;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class View {
	JFrame frame;
	JPanel mainPanel;
	StatusPanel statusPanel;
	DrawlingPanel drawlingPanel;
	
	/**
	 * The constructor for the AView class. It is called from MazeApp.
	 */
	public View() {
		try {
			UIManager.setLookAndFeel(UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		statusPanel = new StatusPanel();
		drawlingPanel = new DrawlingPanel();

		mainPanel = new JPanel();
		mainPanel.setOpaque(true);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(drawlingPanel, BorderLayout.CENTER);
		mainPanel.add(statusPanel, BorderLayout.SOUTH);

		frame = new JFrame("Team 1 Shoot'em Up");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(mainPanel);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Called from the controller to add a key listener to the frame.
	 * 
	 * @param kl
	 *            a KeyListener passed from AController
	 */
	public void addKeyListener(KeyListener kl) {
		frame.addKeyListener(kl);
	}

}
