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

import dataStructures.AnalysisEquation;
import dataStructures.AttendanceCsv;
import dataStructures.CsvLoader;
import weightEditor.WeightVersusTimeGrid;

public class UI extends JFrame{

	public static final int WIDTH = 1300;
	public static final int HEIGHT = 800;
	public static final int SPACING = 20;
	
	private AttendanceCsv csv;
	private WeightVersusTimeGrid grid;
	private AnalysisEquation equation;
	private RecordViewer viewer;
	private ArrayList<Visible> display;
	private BufferedImage image;
	private Button updateRanks;
	private ArrayList<Button> allButtons;
	
	private static final int _GRID_MARGIN = 100;
	private static final int _VIEWER_MARGIN = 100 + WeightVersusTimeGrid.PIXEL_WIDTH+SPACING;
	
	public UI(){
		applySettings();//display the JFrame the way I want it
		display = new ArrayList<Visible>();
		
		grid = new WeightVersusTimeGrid(_GRID_MARGIN, 70);
		equation = new AnalysisEquation(_GRID_MARGIN, WeightVersusTimeGrid.PIXEL_HEIGHT+SPACING, grid);
		viewer = new RecordViewer(_VIEWER_MARGIN, 200);
		addButtons();
		
		//add all visible components
		display.add(grid);
		display.add(equation);
		display.add(viewer);
		
		
		addMouseMotionListener(grid);
		addMouseMotionListener(viewer);
		
		Timer timer = new Timer(30, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean update = false;;
				for(Visible v: display){
					if(v.markedForUpdate()){
						update = true;
						break;
					}
				}
				if(update)UI.this.repaint();
			}
		});
		timer.start();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
		initImage();
		repaint();
		setVisible(true);		

	}
	
	private void addButtons() {
		allButtons = new ArrayList<Button>();
		
		
		updateRanks = new Button("Update Ranking", _VIEWER_MARGIN, 80,120,40, new Action() {
			
			@Override
			public void act() {
				viewer.recalculate(equation);
			}
		} );
		Button addNode = new Button("Add Node", _VIEWER_MARGIN+ 80, 80, 120, 40, new Action(){

			@Override
			public void act() {
				grid.addNode();
			}
			
		});
		
		
		display.add(updateRanks);
		display.add(addNode);
		
		allButtons.add(updateRanks);
		allButtons.add(addNode);
		
		addMouseListener(new ButtonListener(allButtons));
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

	public void setCsv(AttendanceCsv attendanceCsv) {
		csv = attendanceCsv;
		viewer.initTeachersAndPDs(equation, csv.getTeachers(), csv.getPDs());
	}

	
}
