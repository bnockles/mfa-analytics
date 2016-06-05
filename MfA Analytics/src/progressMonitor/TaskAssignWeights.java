package progressMonitor;

import java.awt.Component;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import dataStructures.AnalysisEquation;
import dataStructures.PD;
import dataStructures.Teacher;
import ui.RecordViewer;

public class TaskAssignWeights extends SwingWorker<Void, Void> {

	private List<Teacher> teachers;
	private List<PD> pds;
	RecordViewer viewer;
	AnalysisEquation eq;
	int totalRecords;
	WeightCalculator component;

	public TaskAssignWeights(RecordViewer viewer, WeightCalculator c,List<Teacher> teachers, List<PD> pds, AnalysisEquation eq){
		this.viewer = viewer;
		this.component = c;
		totalRecords = 0;
		this.teachers = teachers;
		this.pds = pds;
		this.eq = eq;
		this.totalRecords = teachers.size()+pds.size();
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
