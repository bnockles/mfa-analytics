package dataStructures;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import buttons.Action;
import buttons.CheckBox;
import ui.UI;
import ui.ViewerLabel;
import ui.VisibleComponent;
import weightEditor.WeightVersusTimeGrid;

public class MinRecordFilter extends VisibleComponent implements Action, Filter, MouseListener {

	public static final int FILTER_WIDTH = 205;
	public static final int FILTER_HEIGHT = 25;
	
	private static final int _ARROW_WIDTH = 20;
	private static final int _ARROW_HEIGHT = FILTER_HEIGHT/2-1;
	
	private boolean turnedOn;
	private int min;
	private FontMetrics fm;
	private CheckBox checkbox;
	
	public MinRecordFilter(int x, int y){
		super(x,y,FILTER_WIDTH,FILTER_HEIGHT);
		fm = g.getFontMetrics();
		checkbox = new CheckBox("Only records exceeding ", 0, 0, false, this);
		draw();
	}
	
	@Override
	public boolean isSatisfied(ViewerLabel l) {
		if(turnedOn){
			if(l instanceof Teacher){
				if(l.getTotalRecords() > min)return true;
				else return false;
			}else return true;
		}else return true;
	}

	@Override
	public void act() {
		turnedOn = !turnedOn;
	}

	@Override
	public void draw() {
		g.setColor(backGroundColor);
		g.fillRect(0, 0, FILTER_WIDTH, FILTER_HEIGHT);
		g.setColor(foreGroundColor);
		String s = "" + min;
		g.drawImage(checkbox.getImage(), checkbox.getX(), checkbox.getY(), null);
		g.drawString(s, checkbox.getWidth(), FILTER_HEIGHT-8);
		g.drawImage(getArrow(true), checkbox.getWidth()+ fm.stringWidth("s")+5, 0, null);
		g.drawImage(getArrow(false), checkbox.getWidth() + fm.stringWidth("s")+5, FILTER_HEIGHT/2, null);
		
		
	}

	private BufferedImage getArrow(boolean up){
		BufferedImage icon = new BufferedImage(_ARROW_WIDTH, _ARROW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = icon.createGraphics();
		int yBase = (up)? _ARROW_HEIGHT : 0;
		int yTip = (up)?  0: _ARROW_HEIGHT;
		int[] xs =  {1,_ARROW_WIDTH-1,_ARROW_WIDTH/2};
		int[] ys =  {yBase,yBase,yTip};
		Polygon arrow = new Polygon(xs,ys, 3);//down arrow
		g.setColor(Color.BLACK);
		GradientPaint bluetowhite = new GradientPaint(0,yBase,UI.HIGHLIGHT_COLOR,0,yTip,Color.WHITE);
		g.setPaint(bluetowhite);
		g.fillPolygon(arrow);
		g.setStroke(new BasicStroke(2));
		g.setColor(UI.HIGHLIGHT_COLOR);
		g.drawPolygon(arrow);
		g.setStroke(new BasicStroke(1));
		return icon;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX()-getX();
		int my = e.getY() - getY();
		int arrowX = checkbox.getWidth() + fm.stringWidth(""+min);
		if(mx > arrowX && mx < arrowX+_ARROW_WIDTH){
			if(my > 0 && my < FILTER_HEIGHT/2){
				//hovering over top arrow
				min ++;
				setMarkedForUpdate(true);
			}else if(my > FILTER_HEIGHT/2 && my < FILTER_HEIGHT && min > 0){
				//hovering over bottom arrow
				min --;
				setMarkedForUpdate(true);
			}
		}
		else if(mx > 0 && mx < CheckBox.CHECKBOX_LENGTH && my > 0 && my < CheckBox.CHECKBOX_LENGTH){
			
			checkbox.act();
			checkbox.update();
			update();
			setMarkedForUpdate(true);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
