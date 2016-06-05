package search;

import java.awt.Color;

import buttons.HoverComponent;
import ui.UI;
import ui.VisibleComponent;

public class Result extends HoverComponent {

	protected String description;
	protected int requiredMode;
	protected int viewerIndex;
	protected int resultIndex;
	
	public static final int RESULT_WIDTH = SearchWindow.SEARCH_WIDTH;
	public static final int RESULT_HEIGHT = 25;
	
	public Result(String description, int resultNumber, int viewerIndex, int viewMode){
		super(0,resultNumber*RESULT_HEIGHT, RESULT_WIDTH, RESULT_HEIGHT);
		setHoverColor(UI.ACCENT_COLOR);
		this.description = description;
		this.requiredMode = viewMode;
		this.viewerIndex = viewerIndex;
		this.resultIndex = resultNumber;
		draw();
	}
	
	public String toString(){
		return description;
	}
	


	public int getRequiredMode() {
		return requiredMode;
	}

	public int getViewerIndex() {
		return viewerIndex;
	}

	public int getResultIndex() {
		return resultIndex;
	}

	@Override
	public void draw() {
		g.setColor(backGroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(foreGroundColor);
		g.drawString(description, 5, RESULT_HEIGHT-2);
	}



}
