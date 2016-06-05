package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

import buttons.Action;
import buttons.Button;
import buttons.Button;
import buttons.ButtonListener;
import buttons.ImageButton;
import buttons.ImageTextButton;
import buttons.LinkButton;
import dataStructures.AnalysisEquation;
import dataStructures.AttendanceCsv;
import dataStructures.CsvLoader;
import search.SearchWindow;
import weightEditor.WeightVersusTimeGrid;

public class UI extends JFrame implements ComponentListener, FocusListener{

	public static final int WIDTH = 1250;
	public static final int HEIGHT = 800;
	public static final int SPACING = 20;
	public static final Color ACCENT_COLOR= new Color(80,215,230);
	public static final Color DISABLED_COLOR= new Color(180,180,180);
	
	private AttendanceCsv csv;
	private SearchWindow searchWindow;
	private WeightVersusTimeGrid grid;
	private AnalysisEquation equation;
	private RecordViewer viewer;
	private SliderComponent sliders;
	private ArrayList<Visible> display;
	private BufferedImage image;
	private Button updateRanks;
	private Button addNode;
	private Button removeNode;
	private Button smoothCurve;
	private Button switchMode;
	private Button graph;
	private Button up;
	private Button search;
	private Button down;
	private ArrayList<Button> allButtons;
	private boolean refresh;
	
	private static final int _GRID_X_MARGIN = 145+SPACING;
	private static final int _GRID_Y_MARGIN = 120;
	private static final int _VIEWER_MARGIN_FROM_RIGHT = 50 + RecordViewer.VIEWER_WIDTH;
	private static final int _BUTTON_Y=_GRID_Y_MARGIN-40;
	private static final int _BUTTON_HEIGHT=30;
	private static final int _ARROW_WIDTH = 20;
	private static final int _ARROW_HEIGHT = 60;
	private static final String _PDS_MODE_TEXT = "PDs";
	private static final String _TEACHERS_MODE_TEXT = "Teachers";
	
	
	public UI(){
		applySettings();//display the JFrame the way I want it
		display = new ArrayList<Visible>();
		refresh = true;
		
		grid = new WeightVersusTimeGrid(_GRID_X_MARGIN+5, _GRID_Y_MARGIN+5);
		equation = new AnalysisEquation(SPACING, _GRID_Y_MARGIN+WeightVersusTimeGrid.PIXEL_HEIGHT+SPACING, grid);
		sliders = new SliderComponent(SPACING, _GRID_Y_MARGIN, equation);
		viewer = new RecordViewer(WIDTH-_VIEWER_MARGIN_FROM_RIGHT, _GRID_Y_MARGIN);
		searchWindow = new SearchWindow(this, getX() + getWidth()-RecordViewer.VIEWER_WIDTH, getY() + _GRID_Y_MARGIN);
		addButtons();
		setViewerButtonEnabled(false);
		
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
		repaint();
		setVisible(true);		

	}
	
	private BufferedImage getArrow(boolean up){
		BufferedImage icon = new BufferedImage(_ARROW_WIDTH, _ARROW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = icon.createGraphics();
		int yBase = (up)? _ARROW_HEIGHT-5 : 5;
		int yTip = (up)?  5: _ARROW_HEIGHT-5;
		int[] xs =  {1,19,10};
		int[] ys =  {yBase,yBase,yTip};
		Polygon arrow = new Polygon(xs,ys, 3);//down arrow
		g.setColor(Color.BLACK);
		GradientPaint bluetowhite = new GradientPaint(0,yBase,new Color(200,200,255),0,yTip,Color.WHITE);
		g.setPaint(bluetowhite);
		g.fillPolygon(arrow);
		return icon;
	}
	
	private void addLinkButtons(){
		updateRanks = new LinkButton("Update Ranking", WIDTH-RecordViewer.VIEWER_WIDTH-50, _BUTTON_Y,120,_BUTTON_HEIGHT, new Action() {
			
			@Override
			public void act() {
				viewer.recalculate(equation);
			}
		} );
		
		switchMode = new LinkButton("PDs", WIDTH-RecordViewer.VIEWER_WIDTH-50+120+SPACING, _BUTTON_Y,120,_BUTTON_HEIGHT, new Action() {
			
			private boolean teacherMode=true;
			
			@Override
			public void act() {
				if(teacherMode){
				viewer.setMode(RecordViewer.PDS_VIEW);
				switchMode.setText(_TEACHERS_MODE_TEXT);
				}else{
					viewer.setMode(RecordViewer.TEACHERS_VIEW);
					switchMode.setText(_PDS_MODE_TEXT);
				}
				viewer.recalculate(equation);
				viewer.setMarkedForUpdate(true);
				switchMode.setMarkedForUpdate(true);
				teacherMode=!teacherMode;
			}
		} );
		
		addNode = new LinkButton("Add Node", _GRID_X_MARGIN, _BUTTON_Y, 120, _BUTTON_HEIGHT, new Action(){

			@Override
			public void act() {
				grid.addNode();
			}
			
		});

		removeNode = new LinkButton("Remove Node", _GRID_X_MARGIN+ 120+SPACING, _BUTTON_Y, 120, _BUTTON_HEIGHT, new Action(){

			
			@Override
			public void act() {
				grid.removeNode();
			}
			
		});
		
		smoothCurve = new LinkButton("Smooth", _GRID_X_MARGIN+ 240+2*SPACING, _BUTTON_Y, 120, _BUTTON_HEIGHT, new Action(){

			private boolean smooth = true;
			
			@Override
			public void act() {
				grid.setSmooth(smooth);
				if(smooth)smoothCurve.setText("Linear");
				else smoothCurve.setText("Smooth");
				smooth = !smooth;
			}
			
		});
		graph = new LinkButton("Summary", _GRID_X_MARGIN+ 360+3*SPACING, _BUTTON_Y, 120, _BUTTON_HEIGHT, new Action(){

			private boolean showGraph = false;
			
			@Override
			public void act() {
				grid.viewGraph(showGraph);
				if(showGraph){
					graph.setText("Summary");
					addNode.setEnabled(true);
					removeNode.setEnabled(true);
					smoothCurve.setEnabled(true);
				}
				else{
					graph.setText("Graph");
					addNode.setEnabled(false);
					removeNode.setEnabled(false);
					smoothCurve.setEnabled(false);
				}
				graph.update();
				showGraph = !showGraph;
			}
			
		});
		
		display.add(updateRanks);
		display.add(addNode);
		display.add(removeNode);
		display.add(smoothCurve);
		display.add(switchMode);
		display.add(graph);
		allButtons.add(updateRanks);
		allButtons.add(addNode);
		allButtons.add(removeNode);
		allButtons.add(smoothCurve);
		allButtons.add(switchMode);
		allButtons.add(graph);

	}
	
	private void addButtons() {
		allButtons = new ArrayList<Button>();
		
		//adds all grid and RecordViewer buttons, which have a LINK style
		addLinkButtons();
		
		up = new ImageButton(getArrow(true),WIDTH-60+_ARROW_WIDTH,_GRID_Y_MARGIN,_ARROW_WIDTH,_ARROW_HEIGHT, new Action() {
			
			@Override
			public void act() {
				viewer.setStartIndex(viewer.getStartIndex()-RecordViewer.VIEWER_ROWS);
				viewer.setMarkedForUpdate(true);
			}
		});
		down = new ImageButton(getArrow(false),WIDTH-60+_ARROW_WIDTH,_GRID_Y_MARGIN+RecordViewer.VIEWER_HEIGHT-_ARROW_HEIGHT,_ARROW_WIDTH,_ARROW_HEIGHT, new Action() {
			
			@Override
			public void act() {
				viewer.setStartIndex(viewer.getStartIndex()+RecordViewer.VIEWER_ROWS);
				viewer.setMarkedForUpdate(true);
			}
		});
		

		
		InputStream is = VisibleComponent.class.getResourceAsStream("/open.png");
		Image open=null;
		try {
			open = ImageIO.read(is);
			Button openButton = new ImageTextButton("Open",open.getScaledInstance(34, 34, Image.SCALE_SMOOTH), 10, _BUTTON_Y-10, 120, _BUTTON_HEIGHT+10, new Action() {
				
				@Override
				public void act() {
					new CsvLoader(UI.this);
				}
			});
			display.add(openButton);
			allButtons.add(openButton);
		} catch (IOException e) {
			e.printStackTrace();
		}
		


		
		is = VisibleComponent.class.getResourceAsStream("/Search.png");
		Image searchIcon=null;
		try {
			searchIcon = ImageIO.read(is);
			search = new ImageButton(searchIcon.getScaledInstance(34, 34, Image.SCALE_SMOOTH), getWidth() - _VIEWER_MARGIN_FROM_RIGHT + viewer.getWidth()-40, _BUTTON_Y,40,_BUTTON_HEIGHT, new Action() {
				
				
				@Override
				public void act() {
					searchWindow.setLocation(getX() + getWidth()-RecordViewer.VIEWER_WIDTH, getY() + _GRID_Y_MARGIN);
					showSearch();
				}
			} );
			display.add(search);
			allButtons.add(search);
		} catch (IOException e) {
			e.printStackTrace();
		}

		

		
		
		

		display.add(up);
		display.add(down);
		
		allButtons.add(up);
		allButtons.add(down);
		
		ButtonListener listener = new ButtonListener(allButtons);
		addMouseListener(listener);
		addMouseMotionListener(listener);
		
	}

	protected void showSearch() {
		searchWindow.setVisible(true);
		searchWindow.toFront();
	}

	public void paint(Graphics g){
		Graphics2D g2 = image.createGraphics();
		if(refresh){
			image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			g2 = image.createGraphics();
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

	private void setViewerButtonEnabled(boolean b) {
		switchMode.setEnabled(b);
		updateRanks.setEnabled(b);
	}
	
	/**
	 * used by search function when user selects a search result
	 * 
	 * @param i index RecordViewer is to move to
	 */
	public void setViewerStartIndex(int i) {
		viewer.setStartIndex(i);
	}
	
	/**
	 * 
	 * @param i Viewer mode (use Viewer static variables)
	 */
	public void setViewerMode(int i) {
		viewer.setMode(i);
		if(viewer.getMode() == RecordViewer.TEACHERS_VIEW)switchMode.setText(_PDS_MODE_TEXT);
		else if(viewer.getMode() == RecordViewer.PDS_VIEW)switchMode.setText(_TEACHERS_MODE_TEXT);
	}

	public void setCsv(AttendanceCsv attendanceCsv) {
		csv = attendanceCsv;
		viewer.initTeachersAndPDs(equation, csv.getTeachers(), csv.getPDs());
		grid.setBarData(attendanceCsv);
		setViewerButtonEnabled(true);
	}
	
	public AttendanceCsv getCsv(){
		return csv;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if(e.getComponent() == this){
			viewer.setX(getWidth()-viewer.getWidth()-50);
			updateRanks.setX(getWidth()-RecordViewer.VIEWER_WIDTH-50);
			switchMode.setX(getWidth()-RecordViewer.VIEWER_WIDTH-50+120+SPACING);
			up.setX(getWidth()-60+_ARROW_WIDTH);
			searchWindow.setX(getWidth()-RecordViewer.VIEWER_WIDTH);
			search.setX(getWidth() - _VIEWER_MARGIN_FROM_RIGHT + viewer.getWidth()-40);
			down.setX(getWidth()-60+_ARROW_WIDTH);
			refresh = true;
			repaint();
			searchWindow.setLocation(getWidth()-RecordViewer.VIEWER_WIDTH, _GRID_Y_MARGIN);
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		searchWindow.setLocation(getX() + getWidth()-RecordViewer.VIEWER_WIDTH, getY() + _GRID_Y_MARGIN);
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		if(searchWindow.isVisible()) {
			searchWindow.setVisible(true);
			searchWindow.toFront();
		}
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		searchWindow.setVisible(false);
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		searchWindow.setVisible(false);
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void recalculate() {
		viewer.recalculate(equation);
	}


	
}
