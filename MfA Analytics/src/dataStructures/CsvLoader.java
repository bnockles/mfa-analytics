package dataStructures;





import ui.UI;
import ui.VisibleComponent;

public class CsvLoader {

	public static final String sampleAttendance = "SampleAttendance/sampleAttendance.csv";

	
	
	public CsvLoader(UI ui){
		ui.setCsv(new AttendanceCsv(ui, sampleAttendance));
	}



	
}
