package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import dataStructures.AnalysisEquation;
import weightEditor.WeightVersusTimeGrid;

public class SliderComponent extends VisibleComponent implements MouseMotionListener, MouseListener{

	public static final int SLIDERS_WIDTH = 200;
	public static final int SLIDERS_HEIGHT = WeightVersusTimeGrid.PIXEL_HEIGHT;
	
	private VerticalSlider attendanceWeight;
	private AnalysisEquation equation;
	List<VerticalSlider> display;
	
	public SliderComponent(int x, int y, final AnalysisEquation equation) {
		super(x,y,SLIDERS_WIDTH, SLIDERS_HEIGHT);
		this.equation = equation;
		attendanceWeight = new VerticalSlider("Absences",3, 3, 80, SLIDERS_HEIGHT-6, new Action() {
			
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
			if(vc.markedForUpdate())vc.update();
			g.drawImage(vc.getImage(), vc.getX(), vc.getY(), null);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(mx> getX() && mx < (getX() + getWidth()) && my > getY()+VerticalSlider.TOP_MARGIN && my < getY() + getHeight()){
			for(VerticalSlider s : display){
				//relative x and y
				int rx = mx -getX();
				int ry = my -getY();
				if(mx > s.getX() && mx < s.getX()+s.getWidth()){
					s.notifyDrag(ry);
					setMarkedForUpdate(true);
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(VerticalSlider vs: display){
			vs.setDragging(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
