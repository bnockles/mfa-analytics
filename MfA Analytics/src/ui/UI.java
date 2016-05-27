package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import weightEditor.WeightVersusTimeGrid;

public class UI extends JFrame{

	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;
	WeightVersusTimeGrid grid;
	ArrayList<Visible> display;
	int count;
	BufferedImage image;
	
	public UI(){
		applySettings();//display the JFrame the way I want it
		display = new ArrayList<Visible>();
		
		grid = new WeightVersusTimeGrid(100, 100);
		
		//add all visible components
		display.add(grid);
		
		addMouseMotionListener(grid);
		
		count = 0;
		Timer timer = new Timer(30, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean update = false;;
				for(Visible v: display){
					if(v.markedForUpdate()){
						update = true;
						break;
					}
				}
				UI.this.repaint();
				count ++;
			}
		});
		timer.start();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
		initImage();
		repaint();
		setVisible(true);		
	}
	
	public void initImage(){
		Graphics2D g2 = image.createGraphics();
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		for(Visible v: display){
				v.update();
				g2.drawImage(v.getImage(), v.getX(), v.getY(), null);		
		}
		repaint();
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = image.createGraphics();
		for(Visible v: display){
//			if(v.markedForUpdate()){
				v.update();
				g2.drawImage(v.getImage(), v.getX(), v.getY(), null);		
//			}
		}
		g2.setColor(Color.black);
		g2.drawString("count = "+count, 100, 90);
		g.drawImage(image, 0, 0, null);
	}
	
	protected void applySettings(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int monitorWidth = (int)screenSize.getWidth();
		int monitorHeight = (int)screenSize.getHeight();
		setSize(WIDTH,HEIGHT);
		setLocation((monitorWidth-WIDTH)/2,(monitorHeight-HEIGHT)/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(false);
	}
	
	
}
