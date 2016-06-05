package progressMonitor;

import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingWorker;

import dataStructures.AnalysisEquation;
import dataStructures.AttendanceCsv;
import dataStructures.PD;
import dataStructures.Teacher;
import ui.RecordViewer;
import ui.UI;

public class FileLoader extends ProgressBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5082754822207937149L;
	private AttendanceCsv task;
	



	public FileLoader(UI ui, File file) {
		super("Reading files...");//inits visible JFrame
		this.task = new AttendanceCsv(ui, file, this);
		start(task);
		// TODO Auto-generated constructor stub
	}


	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			taskOutput.setText("Read "+progress+" files.");
			progressBar.setValue(progress);
		} 
	}


	public AttendanceCsv getAttendanceCsv() {
		return task;
	}
}
