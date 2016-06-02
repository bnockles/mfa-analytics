package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dataStructures.AnalysisEquation;
import dataStructures.PD;
import dataStructures.Teacher;

public class RecordViewer extends VisibleComponent implements MouseMotionListener {

	public static int VIEWER_ROWS = 10;
	private static int _LABEL_MARGIN=30;
	public static int VIEWER_WIDTH = ViewerLabel.LABEL_WIDTH+_LABEL_MARGIN;
//	public static int VIEWER_HEIGHT = VIEWER_ROWS *ViewerLabel.LABEL_HEIGHT;
	public static int VIEWER_HEIGHT = VIEWER_ROWS*ViewerLabel.LABEL_HEIGHT;
	public static int LABEL_SPACE = ViewerLabel.LABEL_HEIGHT;
	
	public static final int TEACHERS_VIEW = 0;
	public static final int PDS_VIEW = 1;
	
	private List<Teacher> teachers;
	private List<PD> pds;
	private int viewing;
	private int startIndex;
	private ViewerLabel hovered;
	private InfoBox infoBox;
	
	
	public RecordViewer(int x, int y) {
		super(x, y, VIEWER_WIDTH, VIEWER_HEIGHT+InfoBox.INFO_BOX_HEIGHT);
		backGroundColor = new Color(205,235,245);
		viewing = TEACHERS_VIEW;
		startIndex = 0;
		infoBox = new InfoBox(0, VIEWER_HEIGHT);
	}


	@Override
	public void draw() {
		g.setColor(backGroundColor);
		g.fillRoundRect(1, 1, VIEWER_WIDTH-2, VIEWER_HEIGHT-2,8,8);
		if(viewing == TEACHERS_VIEW && teachers != null){
			drawObjects(teachers);
		}else if(viewing == PDS_VIEW && pds != null){
			drawObjects(pds);
		}else{
			g.setColor(foreGroundColor);
			g.drawString("No data has been loaded", 5, 30);
		}
		if(infoBox.markedForUpdate())infoBox.update();
		g.drawImage(infoBox.getImage(), infoBox.getX(), infoBox.getY(),null);
	
	}
	
	private void drawObjects(List<?> components) {
		int i = startIndex;
		int row = 0;
		while(i < startIndex+VIEWER_ROWS && i < components.size()){
			ViewerLabel label = (ViewerLabel)components.get(i);
			g.setColor(Color.black);
			g.drawString((i+1)+".", 3, LABEL_SPACE*(row)+32);
			g.drawImage(label.getImage(), _LABEL_MARGIN, LABEL_SPACE * row, null);
			row ++;
			i ++;
		}
	}

	public void initTeachersAndPDs(AnalysisEquation eq, List<Teacher> tlist, List<PD> pdlist){
		this.teachers = tlist;
		this.pds = pdlist;
		System.out.println("There are "+teachers.size()+" teacher records");
		recalculate(eq);
		setMarkedForUpdate(true);
	}

	public void setStartIndex(int i){
		while((viewing == TEACHERS_VIEW &&  i > teachers.size())  || (viewing == PDS_VIEW && i > pds.size())){
			i -=VIEWER_ROWS;
		}
		if(i < 0) i =0;
		startIndex = i;
	}
	
	public int getStartIndex(){
		return startIndex;
	}
	
	public void recalculate(AnalysisEquation eq) {
		if(viewing == TEACHERS_VIEW){
			for(Teacher t: teachers){
				t.updateValue(eq);
			}
			Collections.sort(teachers);
		}else if(viewing == PDS_VIEW){
			for(PD pd: pds){
				pd.updateValue(eq);
			}
			Collections.sort(pds);
		}
		setMarkedForUpdate(true);
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX() - getX();
		int my = e.getY() - getY();
		ViewerLabel nowHovering= determineHoveredLabel(mx, my);
		if(nowHovering != null){
			if(nowHovering != hovered){
				if(hovered != null){
					hovered.setUnselected(false);
				}
				hovered = nowHovering;
				hovered.setUnselected(true);
				infoBox.setInfo(hovered);
				setMarkedForUpdate(true);
			}
		}else{
			if(hovered!= null){
				hovered.setUnselected(false);
				infoBox.setInfo(null);
				hovered = null;
				setMarkedForUpdate(true);
			}
		}
	}


	private ViewerLabel determineHoveredLabel(int mx, int my) {

		if(mx > 0 && mx < VIEWER_WIDTH && my > 0 && my < VIEWER_HEIGHT){
			int itemNumber = my/LABEL_SPACE;
			if(viewing == TEACHERS_VIEW && teachers != null){
				if(teachers.size() <= startIndex + itemNumber || itemNumber > VIEWER_ROWS-1)return null;
				else return teachers.get(startIndex + itemNumber);
			}
			if(viewing == PDS_VIEW && pds != null){
				if(pds.size() <= startIndex + itemNumber  || itemNumber > VIEWER_ROWS-1)return null;
				else return pds.get(startIndex + itemNumber);
			}else return null;
			
		}else {
			return null;
		}
	}


	public void setMode(int mode) {
		viewing = mode;
		startIndex=0;
		setMarkedForUpdate(true);
	}

}
