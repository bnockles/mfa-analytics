package progressMonitor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public abstract class ProgressBar extends JFrame implements
PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4687540599987241782L;
	protected JProgressBar progressBar;
	protected JLabel taskOutput;



	public ProgressBar(String defaultOutput) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int monitorWidth = (int) screenSize.getWidth();
		int monitorHeight = (int) screenSize.getHeight();
		int windowWidth = defaultOutput.length()*8;
		int windowHeight = 60;
		
		setLocation((monitorWidth-windowWidth)/2, (monitorHeight-windowHeight)/2);
		setUndecorated(true);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		taskOutput = new JLabel(defaultOutput);
	
		
		
		setPreferredSize(new Dimension(windowWidth,windowHeight));

		JPanel progressPanel = new JPanel();
		progressPanel.add(progressBar);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(taskOutput, BorderLayout.PAGE_START);
		panel.add(progressPanel,BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		panel.setOpaque(true); //content panes must be opaque
		setContentPane(panel);

		//Display the window.
		pack();
		setVisible(true);
	}

	/**
	 * Invoked when the user presses the start button.
	 */
	public void start(SwingWorker task) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		//Instances of javax.swing.SwingWorker are not reusuable, so
		//we create new instances as needed.
		task.addPropertyChangeListener(this);
		task.execute();
	}

}


