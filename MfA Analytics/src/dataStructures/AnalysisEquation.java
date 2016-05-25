package dataStructures;

import java.awt.image.BufferedImage;


import ui.VisibleComponent;

public class AnalysisEquation extends VisibleComponent{

	public static final int WIDTH = 600;
	public static final int HEIGHT = 500;
	
	public static final double MAX_COEF = 1.0;
	public static final double MIN_COEF = -1.0;
	
	private double percentageLateCoef;//multiplied by number of times late
	private double percentagePresenceCoef;//multiplied by percentage of presence
	private double onTimeCoef;//is multiplied by sum of all weighted late times
	private double lateCoef;//is multiplied by sum of all weighted on time times;
	private double absenceCoef;//multiplied by number of absences
	
	
	public AnalysisEquation() {
		super(0, 0, WIDTH, HEIGHT);
		
	}
	
	
	@Override
	public void update() {

	}
	

	
}
