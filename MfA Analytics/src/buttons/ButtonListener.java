package buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class ButtonListener implements MouseListener {

	List<Button> buttons;
	
	public ButtonListener(List<Button> buttons) {
		this.buttons = buttons;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(Button b: buttons){
			if(b.isVisible() && 
					e.getX() > b.getX() && e.getX() < b.getX()+b.getWidth() &&
					e.getY() > b.getY() && e. getY() < b.getY() + b.getHeight()){
				b.setClicked(true);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(Button b: buttons){
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

}
