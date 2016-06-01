package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

import dataStructures.AnalysisEquation;
import dataStructures.TimelinessRecord;

public abstract class ViewerLabel extends VisibleComponent implements Comparable<ViewerLabel> {

	public static final int LABEL_WIDTH = 400;
	public static final int LABEL_HEIGHT = 40;
	
	private static final int _MARGIN = 5;
	
    private ArrayList<TimelinessRecord> timestamps;
	private String displayNameString;//stays the same
	private String infoString;//is updated when recalculated
	protected double value;
	protected int totalAbsences;
	protected int totalAttendance;
	protected int totalExcused;
	protected int totalLate;
    private boolean hovered;
    private Color highlightColor;
    
	
	public ViewerLabel(String label, int x, int y) {
		super(x, y, LABEL_WIDTH, LABEL_HEIGHT);
		backGroundColor = new Color(200,200,200);
		highlightColor =  Color.white;
		foreGroundColor = Color.black;
		displayNameString = label;
		infoString = "";
		timestamps = new ArrayList<TimelinessRecord>();
		value = 0;
		hovered = false;
	}

	
	public ArrayList<TimelinessRecord> getTimestamps(){
		return timestamps;
	}
	
	public void addTimestamp(TimelinessRecord tr){
		timestamps.add(tr);
	}
	
	@Override
	public void draw() {
		if(!hovered){
			g.setColor(backGroundColor);
		}else{
			g.setColor(highlightColor);
		}
		g.fillRect(_MARGIN, _MARGIN, LABEL_WIDTH-2*_MARGIN, LABEL_HEIGHT-2*_MARGIN);	
		g.setColor(foreGroundColor);
		int margin = LABEL_WIDTH - 250;
		g.drawString(GuiUtilities.shortenStringtoFit(g, displayNameString,margin), _MARGIN +3, LABEL_HEIGHT-_MARGIN-3);
		g.drawString(infoString, margin + 2, LABEL_HEIGHT-_MARGIN-3);
	}

	public void updateValue(AnalysisEquation equation){
		infoString = equation.getTimelinessValueStatement(timestamps, this);
		update();
	}
	

	@Override
	public final int compareTo(ViewerLabel other){
		if(this.value-other.getValue() < 0)return 1;
		else if(this.value-other.getValue() > 0)return -1;
		else return 0;
	}


	private double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
	}


	public int getTotalAbsences() {
		return totalAbsences;
	}


	public void setTotalAbsences(int absences) {
		this.totalAbsences = absences;
	}


	public int getTotalAttendance() {
		return totalAttendance;
	}


	public void setTotalAttendance(int totalAttendance) {
		this.totalAttendance = totalAttendance;
	}


	public int getTotalExcused() {
		return totalExcused;
	}


	public void setTotalExcused(int totalExcused) {
		this.totalExcused = totalExcused;
	}


	public double getTotalLate() {
		return totalLate;
	}


	public void setTotalLate(int totalLate) {
		this.totalLate = totalLate;
	}
	
	
	public int getLatePercentage(){
		return (int)(100*(double)totalLate/totalAttendance);
	}
	
	public int getAbsentPercentage(){
		return (int)(100*(double)totalAbsences/totalAttendance);
	}


	public void setUnselected(boolean b) {
		hovered = b;
		update();
	}

}
