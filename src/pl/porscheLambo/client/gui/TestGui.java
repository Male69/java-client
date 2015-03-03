package pl.porscheLambo.client.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.Timer;

public class TestGui {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestGui window = new TestGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		 Timer timer = new Timer(1000, new TestGuiListener());

		    timer.start();
		    System.out.println("A ja jestem za startem");
//		    try {
//		      Thread.sleep(10000);
//		    } catch (InterruptedException e) {
//		    }
		   // timer.stop();
		  }

	/**
	 * Create the application.
	 */
	public TestGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
