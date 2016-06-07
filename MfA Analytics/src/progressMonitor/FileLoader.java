package progressMonitor;

import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
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
	private int numberOfRecords;
	



	public FileLoader(UI ui, File file) {
		super("Reading files...");//inits visible JFrame
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			numberOfRecords = countLines(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.task = new AttendanceCsv(ui, file, this);
		start(task);
		// TODO Auto-generated constructor stub
	}

	
	public int countLines(FileReader reader) {
		int lines = 0;
		LineNumberReader  lnr = new LineNumberReader(reader);
		try {
			lnr.skip(Long.MAX_VALUE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lines =  lnr.getLineNumber() + 1;
	        setMaximum(lines);
		return lines;
	}

	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			taskOutput.setText("Read "+progress+"/"+numberOfRecords+" records.");
			progressBar.setValue((int)((double)progress/numberOfRecords * 100));
		} 
	}


	public void setNumberOfRecords(int n) {
		numberOfRecords = n;
	}
	
	public AttendanceCsv getAttendanceCsv() {
		return task;
	}
}
