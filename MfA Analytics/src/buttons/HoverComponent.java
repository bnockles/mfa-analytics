package buttons;

import java.awt.Color;

import ui.VisibleComponent;

public abstract class HoverComponent extends VisibleComponent{

	protected Color backHoverColor;
	protected Color frontHoverColor;
	protected boolean hovered;

	public HoverComponent(int i, int j, int w, int h) {
		super(i,j,w,h);
		backHoverColor = Color.white;
		frontHoverColor = Color.black;
		hovered = false;
	}

	public void setHoverColor(Color c){
		this.backHoverColor = c;
	}

	public void setHover(boolean b) {
		if(b != hovered){
			Color background = backGroundColor;
			backGroundColor = backHoverColor;
			backHoverColor = background;
			
			Color foreground = foreGroundColor;
			foreGroundColor = frontHoverColor;
			frontHoverColor = foreground;
			
			
			hovered = b;
			draw();
		}
	}


}
