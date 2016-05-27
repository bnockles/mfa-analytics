package ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;


import weightEditor.WeightVersusTimeGrid;

public class UI extends JFrame{

	WeightVersusTimeGrid grid;
	
	public UI(){
		applySettings();//display the JFrame the way I want it
		reset();//starts the game from the beginning
		setVisible(true);
		Timer timer = new Timer(30, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				activeScreen.update();
				Game.this.repaint();
			}
		});
		timer.start();
		
		grid = new WeightVersusTimeGrid(100, 100);
		
	}
	
	protected void applySettings(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int monitorWidth = (int)screenSize.getWidth();
		int monitorHeight = (int)screenSize.getHeight();
		setSize(WIDTH,HEIGHT);
		setLocation((monitorWidth-WIDTH)/2,(monitorHeight-HEIGHT)/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(false);
		saveBeforeClose();
	}
	
	
}
