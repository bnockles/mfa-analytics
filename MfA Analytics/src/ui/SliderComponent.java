package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import dataStructures.AnalysisEquation;
import weightEditor.WeightVersusTimeGrid;

public class SliderComponent extends VisibleComponent implements MouseMotionListener{

	public static final int SLIDERS_WIDTH = 30;
	public static final int SLIDERS_HEIGHT = WeightVersusTimeGrid.PIXEL_HEIGHT;
	
	private VerticalSlider attendanceWeight;
	private AnalysisEquation equation;
	List<VerticalSlider> display;
	
	public SliderComponent(int x, int y, final AnalysisEquation equation) {
		super(x,y,SLIDERS_WIDTH, SLIDERS_HEIGHT);
		this.equation = equation;
		attendanceWeight = new VerticalSlider(3, 3, 30, SLIDERS_HEIGHT-6, new Action() {
			
			@Override
			public void act() {
				equation.setAbsenceCoef(attendanceWeight.getValue());
			}
		});
		display = new ArrayList<VerticalSlider>();
		display.add(attendanceWeight);
		update();
	}

	@Override
	public void draw() {
		for(VisibleComponent vc : display){
			g.drawImage(vc.getImage(), vc.getX(), vc.getY(), null);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int mx = e.getX();
		int my = e.getX();
		if(mx> getX() && mx < getX() + getWidth() && my > getY() + VerticalSlider.TOP_MARGIN && my < getY() + getHeight() -VerticalSlider.BOTTOM_MARGIN){
			System.out.println("Draggin "+mx +"," +my);
		
			for(VerticalSlider s : display){
				//relative x and y
				int rx = mx -getX();
				int ry = my -getY();
				if(mx > s.getX() && mx < s.getX()+s.getWidth()){
					s.notifyDrag(ry);
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
