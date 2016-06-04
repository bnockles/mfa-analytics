package buttons;

import java.awt.Color;

import ui.VisibleComponent;

public abstract class HoverComponent extends VisibleComponent{

	protected Color hoverColor;
	protected boolean hovered;

	public HoverComponent(int i, int j, int w, int h) {
		super(i,j,w,h);
		hoverColor = Color.white;
		hovered = false;
	}

	public void setHoverColor(Color c){
		this.hoverColor = c;
	}

	public void setHover(boolean b) {
		if(b != hovered){
			Color background = backGroundColor;
			backGroundColor = hoverColor;
			hoverColor = background;
			hovered = b;
			draw();
		}
	}


}
