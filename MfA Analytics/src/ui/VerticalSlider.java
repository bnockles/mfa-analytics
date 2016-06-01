package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;

public class VerticalSlider extends VisibleComponent{

	private Action action;
	private double max;
	private double min;
	private double value;
	private boolean dragging;

	public static final int TOP_MARGIN = 20;
	public static final int BOTTOM_MARGIN = 10;
	
	private int _Y_MAX;
	private int _Y_MIN;
	private int _LENGTH;


	private static final int _SLIDER_HEIGHT = 20;
	private static final Color _SLOT_COLOR = new Color(120,120,120);
	private static final Color _SLIDER_COLOR = new Color(200,200,200);


	public VerticalSlider(int x, int y, int width, int height,Action action) {
		super(x,y,width,height);
		this.action = action;
		setBackGroundColor(Color.red);
		value = 0.0;
		max = 1.0;
		min = -1.0;
		_Y_MAX = 5 + TOP_MARGIN;
		_Y_MIN = height-5;
		_LENGTH = _Y_MAX - _Y_MIN;
		dragging = false;
		update();
	}

	public void setMax(double max){
		this.max = max;
	}

	public void setMin(double min){
		this.min = min;
	}

	public double getValue(){
		return value;
	}

	@Override
	public void draw() {
		g.setColor(backGroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		int sliderWidth = 6;
		g.setColor(_SLOT_COLOR);
		g.fillRoundRect((getWidth()-sliderWidth)/2, _Y_MAX, sliderWidth, getHeight()-TOP_MARGIN-BOTTOM_MARGIN*2,3,3);

		g.setColor(_SLIDER_COLOR);
		int spaceFromEdge = 5;
		g.fillRoundRect(spaceFromEdge, getSliderCoordinate(), getWidth()-2*spaceFromEdge, _SLIDER_HEIGHT, 5, 5);

		g.setColor(foreGroundColor);
	}

	private int getSliderCoordinate() {
		return (int) (_Y_MIN+_LENGTH*(value-min)/(max-min))-_SLIDER_HEIGHT/2;
	}


	private double getSliderValue(int y) {
		int difference = y - _Y_MAX;
		return _Y_MIN +(max-min)*difference/(_Y_MAX-_Y_MIN);
	}

	public void notifyDrag(int ry) {
		int sliderY= getSliderCoordinate();
		System.out.println(dragging);
		if(ry > sliderY && ry < sliderY + _SLIDER_HEIGHT){
			dragging = true;
			value = getSliderValue(ry);
			setMarkedForUpdate(true);
		}
	}



}
