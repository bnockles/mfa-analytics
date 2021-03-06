package progressMonitor;

import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.util.List;


import dataStructures.AnalysisEquation;
import dataStructures.Filter;
import dataStructures.HolisticDataDisplay;
import dataStructures.PD;
import dataStructures.Teacher;
import ui.RecordViewer;
import ui.UI;

public class WeightCalculator extends ProgressBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5082754822207937149L;
	private TaskAssignWeights task;
	


	public WeightCalculator(RecordViewer viewer, List<Teacher> teachers, List<PD> pds, AnalysisEquation eq, List<Filter> filters) {
		super("Updating attendance values");//inits visible JFrame
		
		this.task = new TaskAssignWeights(viewer, this,teachers, pds, eq, filters);
//		taskOutput.setText("Updating attendance values");
		
		start(task);
	}


	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
		} 
	}


}