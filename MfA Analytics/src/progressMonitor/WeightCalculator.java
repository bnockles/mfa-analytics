package progressMonitor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import dataStructures.AnalysisEquation;
import dataStructures.PD;
import dataStructures.Teacher;
import ui.RecordViewer;

public class WeightCalculator extends JFrame implements
PropertyChangeListener {

	private JProgressBar progressBar;
	private JTextArea taskOutput;
	private TaskAssignWeights task;
	


	public WeightCalculator(RecordViewer viewer) {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int monitorWidth = (int) screenSize.getWidth();
		int monitorHeight = (int) screenSize.getHeight();
		int windowWidth = 400;
		int windowHeight = 100;
		setSize(windowWidth,windowHeight);
		setLocation((monitorWidth-windowWidth)/2, (monitorHeight-windowHeight)/2);
		this.task = new TaskAssignWeights(viewer, this);
		
		JPanel panel = new JPanel(new BorderLayout());


		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5,5,5,5));
		taskOutput.setEditable(false);

		JPanel progressPanel = new JPanel();
		progressPanel.add(progressBar);

		panel.add(progressPanel, BorderLayout.PAGE_START);
		panel.add(new JScrollPane(taskOutput), BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	
		panel.setOpaque(true); //content panes must be opaque
		setContentPane(panel);

		//Display the window.
		pack();
		setVisible(true);
		start();
	}

	/**
	 * Invoked when the user presses the start button.
	 */
	public void start() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		//Instances of javax.swing.SwingWorker are not reusuable, so
		//we create new instances as needed.
		task.addPropertyChangeListener(this);
		task.execute();
	}

	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			taskOutput.append(String.format(
					"Completed %d%% of task.\n", task.getProgress()));
		} 
	}


}