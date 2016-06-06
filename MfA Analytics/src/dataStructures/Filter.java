package dataStructures;

import ui.RecordViewer;
import ui.ViewerLabel;

public interface Filter {

	boolean isSatisfied(ViewerLabel l);
	void setParent(RecordViewer rv);
	
}
