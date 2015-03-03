package pl.porscheLambo.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestGuiListener implements ActionListener {

	public TestGuiListener() {
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("wywolalem sie");
	}
}
