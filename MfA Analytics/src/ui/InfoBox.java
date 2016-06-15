package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Collections;

import buttons.ActOnClick;
import buttons.CheckBox;
import dataStructures.AnalysisEquation;
import dataStructures.PD;
import dataStructures.Teacher;
import dataStructures.TimelinessRecord;

public class InfoBox extends VisibleComponent {

	public static final int INFO_BOX_WIDTH = RecordViewer.VIEWER_WIDTH;
	public static final int INFO_BOX_HEIGHT = 240;

	private static final int LINE_HEIGHT = 25;
	private static final int y1=25;
	private static final int y2=y1 + LINE_HEIGHT;
	private static final int y3=y2 + LINE_HEIGHT;

	private static final int x1=10;
	private static final int x2=INFO_BOX_WIDTH/2;

	private ViewerLabel info;

	public InfoBox(int x, int y) {
		super(x, y, INFO_BOX_WIDTH, INFO_BOX_HEIGHT);
		backGroundColor = new Color(255,255,255);
		foreGroundColor = (Color.black);

//		update();
	}

	@Override
	public void draw() {
		g.setColor(backGroundColor);
		g.fillRect(0, 0, INFO_BOX_WIDTH, INFO_BOX_HEIGHT);

		g.setColor(foreGroundColor);
		int y = y2;
		String prefix = "";
		String suffix = "";
		if(info != null) suffix = "due to lateness ("+ (GuiUtilities.formatTenths(Math.abs(info.getMinutesLate()/info.getTotalRecords())))+" minutes, on average.)";
		if(info instanceof PD){
			PD pd = (PD)info;
			g.drawString(GuiUtilities.shortenStringtoFit(g, pd.getTitle(), INFO_BOX_WIDTH-50), x1, y1);
			g.drawString(GuiUtilities.shortenStringtoFit(g, pd.getWorkshop()+" workshops, "+pd.getNumberOfParticipants()+" participants and "+pd.getTimestamps().size()+" records",INFO_BOX_WIDTH-50), x1, y2);
			y=y2 + LINE_HEIGHT;
			suffix = "";
			if(info.getMinutesLate() > 0) {
				prefix = "On average, participants of this PD are "+(GuiUtilities.formatTenths((info.getMinutesLate()/pd.getTimestamps().size())))+" minutes late";
			}else{
				prefix = "On average, participants of this PD are "+ (GuiUtilities.formatTenths(Math.abs(info.getMinutesLate()/info.getTimestamps().size())))+" minutes early, average.";
			}
						
		}else if (info instanceof Teacher){
			Teacher t = (Teacher)info;
			g.drawString(t.getFirstName()+" "+t.getLastName(), x1, y1);
			g.drawString("( out of "+t.getTotalAttendance()+" records)", x2, y1);
			if(info.getMinutesLate() > 0) {
				prefix = t.getFirstName()+ " is missing a total of "+info.getMinutesLate()+" minutes";
			}else{
				prefix = t.getFirstName()+ "is usually early, about "+ (GuiUtilities.formatTenths(Math.abs(info.getMinutesLate()/info.getTimestamps().size())))+" minutes early, average.";
				suffix="";
				
			}
		}
		if(info != null){
			Stroke currentStroke = g.getStroke();
			g.setColor(new Color(100,150,255));
			BasicStroke s = new BasicStroke(3);
			g.setStroke(s);
			//draw border
			g.drawRoundRect(5, 5, getWidth()-10, INFO_BOX_HEIGHT-10, 5, 5);
			g.setColor(foreGroundColor);
			g.setStroke(currentStroke);
			g.drawString("Late "+info.getLatePercentage()+"% of time", x1, y);
			g.drawString("Absent "+info.getAbsentPercentage()+"% of time", x2, y);
			
			ArrayList<TimelinessRecord> minutes = new ArrayList<TimelinessRecord>();
			for(TimelinessRecord t: info.getTimestamps()){
				int minutesLate = AnalysisEquation.minutesPastStart(t.getTime());
				if( minutesLate > 0){
					minutes.add(t);
				}
			}
			Collections.sort(minutes);
			
			g.drawString(prefix, x1, y+=LINE_HEIGHT);
			if(!suffix.equals(""))g.drawString(suffix, x1, y+=LINE_HEIGHT);
			int j = 1;
			
			for(int i = minutes.size()-1; i >=0 && j < 5; i--){
				TimelinessRecord tr = minutes.get(i);
				String offender = "";
				if(info instanceof PD){
					offender = ", "+tr.getFirstName()+" "+tr.getLastName();
				}
				g.drawString(GuiUtilities.shortenStringtoFit(g, tr.getMinutesLate()+" min late ("+tr.getFormattedDate()+offender, INFO_BOX_WIDTH)+")", x1+8, y+LINE_HEIGHT*j);
				j++;
			}
			
		}

	}

	public void setInfo(ViewerLabel hovered) {
		this.info = hovered;
		setMarkedForUpdate(true);
	}


}
