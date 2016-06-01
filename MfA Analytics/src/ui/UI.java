package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import dataStructures.AnalysisEquation;
import dataStructures.AttendanceCsv;
import dataStructures.CsvLoader;
import weightEditor.WeightVersusTimeGrid;

public class UI extends JFrame implements ComponentListener{

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;
	public static final int SPACING = 20;
	
	private AttendanceCsv csv;
	private WeightVersusTimeGrid grid;
	private AnalysisEquation equation;
	private RecordViewer viewer;
	private SliderComponent sliders;
	private ArrayList<Visible> display;
	private BufferedImage image;
	private Button updateRanks;
	private Button switchMode;
	private Button up;
	private Button down;
	private ArrayList<Button> allButtons;
	private boolean refresh;
	
	private static final int _GRID_X_MARGIN = 120+SPACING;
	private static final int _GRID_Y_MARGIN = 120;
	private static final int _VIEWER_MARGIN = 100 + WeightVersusTimeGrid.PIXEL_WIDTH+SPACING;
	private static final int _BUTTON_Y=_GRID_Y_MARGIN-50;
	private static final int _BUTTON_HEIGHT=40;
	
	public UI(){
		applySettings();//display the JFrame the way I want it
		display = new ArrayList<Visible>();
		refresh = true;
		
		grid = new WeightVersusTimeGrid(_GRID_X_MARGIN+5, _GRID_Y_MARGIN+5);
		equation = new AnalysisEquation(SPACING, _GRID_Y_MARGIN+WeightVersusTimeGrid.PIXEL_HEIGHT+SPACING, grid);
		sliders = new SliderComponent(SPACING, _GRID_Y_MARGIN, equation);
		viewer = new RecordViewer(WIDTH-RecordViewer.VIEWER_WIDTH-50, _GRID_Y_MARGIN);
		addButtons();
		
		//add all visible components
		display.add(grid);
		display.add(equation);
		display.add(viewer);
		display.add(sliders);
		
		addComponentListener(this);
		addMouseMotionListener(grid);
		addMouseListener(grid);
		addMouseListener(sliders);
		addMouseMotionListener(viewer);
		addMouseMotionListener(sliders);
		
		Timer timer = new Timer(30, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
//				boolean update = false;;
//				for(Visible v: display){
//					if(v.markedForUpdate()){
//						v.update();
//						update = true;
////						break;
//					}
//				}
//				if(update)
				UI.this.repaint();
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
		
		int arrowHeight = 40;
		int arrowWidth = 20;
		BufferedImage upIcon = new BufferedImage(arrowWidth, arrowHeight, BufferedImage.TYPE_INT_ARGB);
		BufferedImage downIcon = new BufferedImage(arrowWidth, arrowHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D upG = upIcon.createGraphics();
		Graphics2D downG = downIcon.createGraphics();
		int[] xs =  {1,19,10};
		int[] yDown =  {5,5,35};
		int[] yUp =  {35,35,5};
		Polygon arrowDown = new Polygon(xs,yDown,3);//down arrow
		Polygon arrowUp = new Polygon(xs,yUp,3);//up arrow
		upG.setColor(Color.BLACK);
		downG.setColor(Color.BLACK);
		upG.fillPolygon(arrowUp);
		downG.fillPolygon(arrowDown);
		up = new ImageButton(upIcon,WIDTH-RecordViewer.VIEWER_WIDTH-50-arrowWidth,_GRID_Y_MARGIN,arrowWidth,arrowHeight, new Action() {
			
			@Override
			public void act() {
				viewer.setStartIndex(viewer.getStartIndex()-RecordViewer.VIEWER_ROWS);
				viewer.setMarkedForUpdate(true);
			}
		});
		down = new ImageButton(downIcon,WIDTH-RecordViewer.VIEWER_WIDTH-50-arrowWidth,_GRID_Y_MARGIN+RecordViewer.VIEWER_HEIGHT-arrowHeight,arrowWidth,arrowHeight, new Action() {
			
			@Override
			public void act() {
				viewer.setStartIndex(viewer.getStartIndex()+RecordViewer.VIEWER_ROWS);
				viewer.setMarkedForUpdate(true);
			}
		});
		
		updateRanks = new Button("Update Ranking", WIDTH-RecordViewer.VIEWER_WIDTH-50, _BUTTON_Y,120,_BUTTON_HEIGHT, new Action() {
			
			@Override
			public void act() {
				viewer.recalculate(equation);
			}
		} );
		
		switchMode = new Button("PDs", WIDTH-RecordViewer.VIEWER_WIDTH-50+120+SPACING, _BUTTON_Y,120,_BUTTON_HEIGHT, new Action() {
			
			private boolean teacherMode=true;
			
			@Override
			public void act() {
				if(teacherMode){
				viewer.setMode(RecordViewer.PDS_VIEW);
				switchMode.setText("Teachers");
				}else{
					viewer.setMode(RecordViewer.TEACHERS_VIEW);
					switchMode.setText("PDs");
				}
				viewer.recalculate(equation);
				viewer.setMarkedForUpdate(true);
				switchMode.setMarkedForUpdate(true);
				teacherMode=!teacherMode;
			}
		} );
		
		Button addNode = new Button("Add Node", _GRID_X_MARGIN, _BUTTON_Y, 120, _BUTTON_HEIGHT, new Action(){

			@Override
			public void act() {
				grid.addNode();
			}
			
		});

		Button removeNode = new Button("Remove Node", _GRID_X_MARGIN+ 120+SPACING, _BUTTON_Y, 120, _BUTTON_HEIGHT, new Action(){

			
			@Override
			public void act() {
				grid.removeNode();
			}
			
		});
		
		Button smoothCurve = new Button("Smooth", _GRID_X_MARGIN+ 240+2*SPACING, _BUTTON_Y, 120, _BUTTON_HEIGHT, new Action(){

			private boolean smooth = true;
			
			@Override
			public void act() {
				grid.setSmooth(smooth);
				smooth = !smooth;
			}
			
		});
		
		
		
		display.add(updateRanks);
		display.add(addNode);
		display.add(removeNode);
		display.add(smoothCurve);
		display.add(switchMode);
		display.add(up);
		display.add(down);
		
		allButtons.add(updateRanks);
		allButtons.add(addNode);
		allButtons.add(removeNode);
		allButtons.add(smoothCurve);
		allButtons.add(switchMode);
		allButtons.add(up);
		allButtons.add(down);
		
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
		if(refresh){
			g2.setColor(Color.white);
			g2.fillRect(0, 0, getWidth(), getHeight());
		}
		for(Visible v: display){
			if(refresh || v.markedForUpdate()){
				v.update();
				g2.drawImage(v.getImage(), v.getX(), v.getY(), null);	
			}
		}
		refresh = false;
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

	@Override
	public void componentResized(ComponentEvent e) {
		if(e.getComponent() == this){
			viewer.setX(getWidth()-viewer.getWidth()-50);
			updateRanks.setX(getWidth()-RecordViewer.VIEWER_WIDTH-50);
			refresh = true;
			repaint();
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
