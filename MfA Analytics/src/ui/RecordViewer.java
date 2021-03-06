package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buttons.CheckBox;
import dataStructures.AnalysisEquation;
import dataStructures.Filter;
import dataStructures.HolisticDataDisplay;
import dataStructures.PD;
import dataStructures.Teacher;
import progressMonitor.TaskAssignWeights;
import progressMonitor.WeightCalculator;

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
	
	private List<Teacher> shownTeachers;
	private List<PD> shownPds;
	private List<Teacher> hiddenTeachers;
	private List<PD> hiddenPds;
	
	private int viewing;
	private int startIndex;
	private ViewerLabel hovered;
	private InfoBox infoBox;
	private List<Filter> filters;
	private boolean filtering;



	public RecordViewer(int x, int y) {
		super(x, y, VIEWER_WIDTH, VIEWER_HEIGHT+InfoBox.INFO_BOX_HEIGHT);
		backGroundColor = new Color(205,235,245);
		viewing = TEACHERS_VIEW;
		startIndex = 0;
		infoBox = new InfoBox(0, VIEWER_HEIGHT);
		filters = new ArrayList<Filter>();
		filtering = false;
	}

	public void addFilter(Filter f){
		filters.add(f);
		f.setParent(this);
	}
	
	public void removeFilter(Filter f){
		if(filters.contains(f))filters.remove(f);
	}

	@Override
	public void draw() {
		g.setColor(backGroundColor);
		g.fillRoundRect(1, 1, VIEWER_WIDTH-2, VIEWER_HEIGHT-2,8,8);
		if(viewing == TEACHERS_VIEW && shownTeachers != null){
			drawObjects(shownTeachers);
		}else if(viewing == PDS_VIEW && shownPds != null){
			drawObjects(shownPds);
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
		g.setColor(Color.black);
		while(i < startIndex+VIEWER_ROWS && i < components.size()){
			ViewerLabel label = (ViewerLabel)components.get(i);
//			boolean display = true;
//			for(Filter f : filters){
//				if(!f.isSatisfied(label)) display = false;
//			}
//			if(display){
				g.drawString((i+1)+".", 3, LABEL_SPACE*(row)+32);
				g.drawImage(label.getImage(), _LABEL_MARGIN, LABEL_SPACE * row, null);
				row ++;
//			}s
			i ++;
		}
	}

	public void updateShownHidden(){
		shownTeachers = new ArrayList<Teacher>();
		shownPds = new ArrayList<PD>();
		hiddenTeachers = new ArrayList<Teacher>();
		hiddenPds = new ArrayList<PD>();
		if(filtering && teachers != null){
			for(Teacher t : teachers){
				boolean shown = true;
				for(Filter f: filters){
					if(!f.isSatisfied(t))shown = false;
				}
				if(shown)shownTeachers.add(t);
				else hiddenTeachers.add(t);
			}
			for(PD pd : pds){
				boolean shown = true;
				for(Filter f: filters){
					if(!f.isSatisfied(pd))shown = false;
				}
				if(shown)shownPds.add(pd);
				else hiddenPds.add(pd);
			}
		}else{
			shownTeachers = teachers;
			shownPds = pds;
		}
		startIndex = 0;
		setMarkedForUpdate(true);
	}
	
	public void initTeachersAndPDs(AnalysisEquation eq, List<Teacher> tlist, List<PD> pdlist){
		this.teachers = tlist;
		this.pds = pdlist;
		updateShownHidden();
		
	}

	public void setStartIndex(int i){
		if(teachers != null && pds != null){
			while((viewing == TEACHERS_VIEW &&  i > teachers.size())  || (viewing == PDS_VIEW && i > pds.size())){
				i -=VIEWER_ROWS;
			}
			if(i < 0) i =0;
			startIndex = i;
			setMarkedForUpdate(true);
		}
	}

	public int getStartIndex(){
		return startIndex;
	}

	
	
	public void recalculate(AnalysisEquation eq) {

		new WeightCalculator(this, teachers, pds, eq, filters);

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


//	private ViewerLabel determineHoveredLabel(int mx, int my) {
//
//		if(mx > 0 && mx < VIEWER_WIDTH && my > 0 && my < VIEWER_HEIGHT){
//			int itemNumber = my/LABEL_SPACE;
//			if(viewing == TEACHERS_VIEW && teachers != null){
//				if(teachers.size() <= startIndex + itemNumber || itemNumber > VIEWER_ROWS-1)return null;
//				else return teachers.get(startIndex + itemNumber);
//			}
//			if(viewing == PDS_VIEW && pds != null){
//				if(pds.size() <= startIndex + itemNumber  || itemNumber > VIEWER_ROWS-1)return null;
//				else return pds.get(startIndex + itemNumber);
//			}else return null;
//
//		}else {
//			return null;
//		}
//	}
	
	
	/**
	 * The following method was written to work with filtering, to return to the version without filters, use
	 * the commented out method above
	 * @param mx
	 * @param my
	 * @return
	 */
	private ViewerLabel determineHoveredLabel(int mx, int my) {

		if(mx > 0 && mx < VIEWER_WIDTH && my > 0 && my < VIEWER_HEIGHT){
			int itemNumber = my/LABEL_SPACE;
			if(viewing == TEACHERS_VIEW && shownTeachers != null){
				if(shownTeachers.size() <= startIndex + itemNumber || itemNumber > VIEWER_ROWS-1)return null;
				else return shownTeachers.get(startIndex + itemNumber);
			}
			if(viewing == PDS_VIEW && shownPds != null){
				if(shownPds.size() <= startIndex + itemNumber  || itemNumber > VIEWER_ROWS-1)return null;
				else return shownPds.get(startIndex + itemNumber);
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
	
	public int getMode(){
		return viewing;
	}

	public void setFiltering(boolean on) {
		filtering = on;
	}


}
