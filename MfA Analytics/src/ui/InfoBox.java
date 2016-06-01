package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import dataStructures.PD;
import dataStructures.Teacher;

public class InfoBox extends VisibleComponent {

	public static final int INFO_BOX_WIDTH = RecordViewer.VIEWER_WIDTH;
	public static final int INFO_BOX_HEIGHT = 400;

	private static final int LINE_HEIGHT = 25;
	private static final int y1=25;
	private static final int y2=y1 + LINE_HEIGHT;

	private static final int x1=10;
	private static final int x2=INFO_BOX_WIDTH/2;

	private ViewerLabel info;

	public InfoBox(int x, int y) {
		super(x, y, INFO_BOX_WIDTH, INFO_BOX_HEIGHT);
		backGroundColor = new Color(255,255,255);
		foreGroundColor = (Color.black);
		update();
	}

	@Override
	public void draw() {
		g.setColor(backGroundColor);
		g.fillRect(0, 0, INFO_BOX_WIDTH, INFO_BOX_HEIGHT);

		g.setColor(foreGroundColor);
		if(info instanceof PD){
			PD pd = (PD)info;
			g.drawString(pd.getTitle()+", "+pd.getWorkshop()+" workshops", x1, y1);
		}else if (info instanceof Teacher){
			Teacher t = (Teacher)info;
			g.drawString(t.getFirstName()+" "+t.getLastName(), x1, y1);
			g.drawString("( out of "+t.getTotalAttendance()+" records)", x2, y1);
			
		}
		if(info != null){
			Stroke currentStroke = g.getStroke();
			g.setColor(new Color(100,150,255));
			BasicStroke s = new BasicStroke(3);
			g.setStroke(s);
			g.drawRoundRect(5, 5, getWidth()-10, 100, 5, 5);
			g.setColor(foreGroundColor);
			g.setStroke(currentStroke);
			g.drawString("Late "+info.getLatePercentage()+"% of time", x1, y2);
			g.drawString("Absent "+info.getAbsentPercentage()+"% of time", x2, y2);
			
		}
	}

	public void setInfo(ViewerLabel hovered) {
		this.info = hovered;
		setMarkedForUpdate(true);
	}

}
