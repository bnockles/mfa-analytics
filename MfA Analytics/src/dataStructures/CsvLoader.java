package dataStructures;





import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import progressMonitor.FileLoader;
import ui.UI;

public class CsvLoader {




	public CsvLoader(UI ui){
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES","csv","csv");
		fc.setFileFilter(filter);
		//In response to a button click:
		int returnVal = fc.showOpenDialog(ui);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			//This is where a real application would open the file.
			FileLoader loader = new FileLoader(ui, file);
			ui.setCsv(loader.getAttendanceCsv());
//			ui.setCsv(new AttendanceCsv(ui, file));
		} else {

		}
	}




}
