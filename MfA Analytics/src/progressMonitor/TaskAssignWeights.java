package progressMonitor;

import java.awt.Component;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import dataStructures.AnalysisEquation;
import dataStructures.HolisticDataDisplay;
import dataStructures.PD;
import dataStructures.Teacher;
import ui.RecordViewer;
import ui.UI;

public class TaskAssignWeights extends SwingWorker<Void, Void> {

	private List<Teacher> teachers;
	private List<PD> pds;
	RecordViewer viewer;
	AnalysisEquation eq;
	int totalRecords;
	WeightCalculator component;
	
	int recordCount;
	int absentCount;
	int lateMinutesCount;

	public TaskAssignWeights(RecordViewer viewer, WeightCalculator c,List<Teacher> teachers, List<PD> pds, AnalysisEquation eq){
		this.viewer = viewer;
		this.component = c;
		totalRecords = 0;
		this.teachers = teachers;
		this.pds = pds;
		this.eq = eq;
		this.totalRecords = teachers.size()+pds.size();
		recordCount = 0;
		absentCount = 0;
		lateMinutesCount = 0;
	}


	@Override
	protected Void doInBackground() throws Exception {
		setProgress(0);
		double total= (double)totalRecords;
		int count = 0;
		for(Teacher t: teachers){
			t.updateValue(eq);
			count += 1;
			setProgress((int)(count/total*100.0));
			
		}
		Collections.sort(teachers);
		AnalysisEquation.setInitializing(false);//At this point, AnalysisEquation has collected all attendance data (holistically). It does not need to do it again with PDs
		HolisticDataDisplay.addTotals(teachers.size(), pds.size());
		for(PD pd: pds){
			pd.updateValue(eq);
			count += 1;
			setProgress((int)(count/total*100.0));
		}
		Collections.sort(pds);

		
		viewer.setMarkedForUpdate(true);
		return null;
	}

	@Override
	public void done() {
		Toolkit.getDefaultToolkit().beep();
//		startButton.setEnabled(true);
		component.setCursor(null); //turn off the wait cursor
//		taskOutput.append("Done!\n");
		component.setVisible(false);
		viewer.setMarkedForUpdate(true);

		
	}

}
