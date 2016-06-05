package buttons;

import java.awt.image.BufferedImage;

public interface ActOnClick {
	void act();
	boolean isClicked();
	void setClicked(boolean b);
	boolean isVisible();
	int getX();
	int getY();
	int getWidth();
	int getHeight();
	BufferedImage getImage();
}
