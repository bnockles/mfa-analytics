package buttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;

import ui.VisibleComponent;

public class CheckBox extends VisibleComponent implements ActOnClick{

	public static final Color CHECK_COLOR = new Color(50,200,150);
	public static final int CHECKBOX_LENGTH = 15;

	private static final int _SPACE = 5;

	private boolean checked;
	private boolean isClicked;
	private Action action;

	public CheckBox(String text, int x, int y, boolean checked, Action action){
		super(x, y, 10,10);
		g.setFont(baseFont.deriveFont(11.0f));
		FontMetrics fm = g.getFontMetrics();
		initImage(fm.stringWidth(text)+_SPACE+CHECKBOX_LENGTH, CHECKBOX_LENGTH);
		g.setFont(baseFont.deriveFont(11.0f));
		
		this.isClicked = false;
		this.action = action;
		this.checked= checked;

		drawCheck();
		g.setColor(foreGroundColor);
		g.setStroke(new BasicStroke(1));
		g.drawString(text, CHECKBOX_LENGTH+_SPACE, CHECKBOX_LENGTH-3);
	}

	private void drawCheck() {
		g.setColor(Color.white);
		g.fillRect(0, 0, CHECKBOX_LENGTH+2, CHECKBOX_LENGTH);
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(3));
		g.drawRect(2, 2, CHECKBOX_LENGTH-4, CHECKBOX_LENGTH-4);
		g.setStroke(new BasicStroke(5));
		if(checked){
			g.setColor(CHECK_COLOR);
			g.drawLine(0, CHECKBOX_LENGTH/2, CHECKBOX_LENGTH/3, CHECKBOX_LENGTH);
			g.drawLine(CHECKBOX_LENGTH/3, CHECKBOX_LENGTH, CHECKBOX_LENGTH-2, 0);
		}
	}

	@Override
	public void draw() {
		drawCheck();
	}

	@Override
	public void act() {
		checked = !checked;
		setMarkedForUpdate(true);
		action.act();
	}

	@Override
	public boolean isClicked() {
		return isClicked;
	}

	@Override
	public void setClicked(boolean b) {
		isClicked = b;
	}

}
