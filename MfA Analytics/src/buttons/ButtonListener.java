package buttons;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import ui.UI;

public class ButtonListener implements MouseListener, MouseMotionListener {

	List<ActOnClick> buttons;
	UI ui;

	public ButtonListener(List<ActOnClick> buttons) {
		this.buttons = buttons;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(ActOnClick b: buttons){
			if(b.isVisible() && 
					e.getX() > b.getX() && e.getX() < b.getX()+b.getWidth() &&
					e.getY() > b.getY() && e. getY() < b.getY() + b.getHeight()){
				b.setClicked(true);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(ActOnClick b: buttons){
			if(b.isClicked()){
				b.act();
				b.setClicked(false);
			}

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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		boolean hoveringSomething = false;
		for(ActOnClick b: buttons){
			if(b instanceof Button){
				Button button  = (Button)b; 
				if(button.isEnabled() && button.isVisible() && 
						e.getX() > button.getX() && e.getX() < button.getX()+button.getWidth() &&
						e.getY() > button.getY() && e. getY() < button.getY() + button.getHeight()){
					button.setHover(true);
					hoveringSomething = true;
				}else{
					button.setHover(false);
				}
			}
		}

		if(hoveringSomething) e.getComponent().setCursor (Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else e.getComponent().setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));


	}

}
