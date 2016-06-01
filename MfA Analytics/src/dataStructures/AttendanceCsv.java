package dataStructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ui.ErrorMessage;
import ui.UI;



/**
 * 
 * @author bnockles
 *
 *For information about files, see: https://vaadin.com/docs/-/part/framework/application/application-resources.html
 */
public class AttendanceCsv implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7908632964480728026L;
	List<TimelinessRecord> allAttendanceRecords;
	ArrayList<PD> loadedPDs;
	ArrayList<Teacher> teachers;
	ArrayList<String> locations;


	public final static int FIRST_INDEX = 0;
	public final static int LAST_INDEX = 1;
	public final static int ID_INDEX = 2;
	public final static int DATE_INDEX = 3;
	public final static int COURSE_INDEX = 4;
	public final static int WORKSHOP_INDEX = 5;
	public final static int LOCATION_INDEX = 6;
	public final static int ATTENDANCE_INDEX = 7;
	public final static int TIMESTAMP_INDEX = 8;
	public final static int ATTENDANCE_CONFIRMED_INDEX = 9;
	public final static int LATE_INDEX = 10;
	public final static String TIMESTAMP_FORMAT = "MM/dd/yyyy kk:mm:ss a";
	public final static int UTC_TIME_DIFFERENCE = 4;//UTC is 4 hours ahead
	//basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();  


/**
 * 
 * @param ui The JFrame where error messages can be displayed
 * @param saveFileName
 */
	public AttendanceCsv(UI ui, String saveFileName){
		allAttendanceRecords = new ArrayList<TimelinessRecord>();
		loadedPDs=new ArrayList<PD>();
		teachers=new ArrayList<Teacher>();
		locations=new ArrayList<String>();
		try {
			loadCSVFile(ui, new FileReader(saveFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
	}
	
	public AttendanceCsv(UI ui, File csvFile){
		allAttendanceRecords = new ArrayList<TimelinessRecord>();
		loadedPDs=new ArrayList<PD>();
		teachers=new ArrayList<Teacher>();
		locations=new ArrayList<String>();
		try {
			loadCSVFile(ui, new FileReader(csvFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
	}


	public void loadCSVFile(UI ui, FileReader fileReader){
		BufferedReader br = null;
		String line = "";
		int foundContent = 0;

		try {

			br = new BufferedReader(fileReader);
			while ((line = br.readLine()) != null) {


				try{
					String[] row = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);//split only a comma that has an even number of quotes ahead of it
					//					System.out.print("Loaded a line: ");
					//					for(String s: row){
					//						System.out.print(s+" --- ");
					//					}
					//					System.out.println("");

					//parse the date from this entry
					DateFormat format = new SimpleDateFormat("MM/dd/yy");
					Date date = null;
					try{
						date = format.parse(row[DATE_INDEX].replaceAll("\"", ""));
					}catch (ParseException e) {
						e.printStackTrace();
					}
					
					
					//Start by checking whether or not the PD has already been loaded	
					
					PD rowPD = loadAndAddNewPD(row, date);
					Teacher rowTeacher = loadAndAddNewTeacehr(row);
					
					//Start by checking whether or not the location has already been loaded
					String location = row[6].replaceAll("\"", "");
					boolean locationWasLoaded = false;
					for(String l: locations){
						if(location.equals(l)){
							locationWasLoaded=true;
						}
					}
					if(!locationWasLoaded){
						locations.add(location);
					}

					//parse timestamp and status
					String status = "";
					DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
					Date timeStamp = null;
					try{
						status=row[ATTENDANCE_INDEX].replaceAll("\"", "");
						String stamp = row[TIMESTAMP_INDEX].replaceAll("\"", "");
						timeStamp=df.parse(stamp);						
					}catch(ArrayIndexOutOfBoundsException e){
						//will always throw error unless format of csv is changed to include status
					}catch(ParseException e){
						//will be thrown unless record contains a valid date
					}catch(NumberFormatException e){
						//thrown when int is parsed from the "Workshop" header
					}


					TimelinessRecord thisRecord = new TimelinessRecord(rowTeacher,rowPD,row[ID_INDEX].replaceAll("\"", ""),status,timeStamp);
					allAttendanceRecords.add(thisRecord);
					//add the record to the PD as well
					rowPD.addTimestamp(thisRecord);	
					rowTeacher.addTimestamp(thisRecord);
					foundContent++;

				}catch(ArrayIndexOutOfBoundsException e){
					//this exception is thrown at the end of the document, which contains document information
					e.printStackTrace();
				}catch(Exception e){
					//this exception is thrown aduring the first line, since it is the header row
					e.printStackTrace();
				}


			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					if(foundContent<=1)new ErrorMessage(ui, "Loading Error","There is no content in the selected file or no file was loaded. You should reload this page.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	private Teacher loadAndAddNewTeacehr(String[] row) {
		Teacher t = new Teacher(row[LAST_INDEX].replaceAll("\"", ""),row[FIRST_INDEX].replaceAll("\"", ""));
		Teacher alreadyLoaded=t;
		boolean wasLoaded = false;
		for(Teacher tch: teachers){
			if(t.equals(tch)){
				alreadyLoaded=tch;
				wasLoaded=true;
				break;
			}
		}
		if(!wasLoaded)teachers.add(t);
		return alreadyLoaded;
	}


	private PD loadAndAddNewPD(String[] row, Date date){
		int workshopNumber = Integer.parseInt(row[WORKSHOP_INDEX].replace("Workshop ","").replace("\"", ""));
		String location = row[LOCATION_INDEX].replaceAll("\"", "");
		PD pd = new PD(row[COURSE_INDEX].replaceAll("\"", ""), workshopNumber, date, location);
		PD alreadyLoaded=pd;
		boolean wasLoaded = false;
		for(PD p: loadedPDs){
			if(pd.equals(p)){
				alreadyLoaded=p;
				wasLoaded=true;
				alreadyLoaded.addDate(date);
				alreadyLoaded.addLocation(location);
				alreadyLoaded.updateWorkshopNumber(workshopNumber);
				break;
			}
		}
		if(!wasLoaded)loadedPDs.add(pd);
		return alreadyLoaded;
	}
	
	public List<TimelinessRecord> getRecords(){
		return allAttendanceRecords;
	}

	public ArrayList<PD> getPDs(){
		return loadedPDs;
	}


	public ArrayList<String> getLocations(){
		return locations;
	}


	public List<Teacher> getTeachers() {
		return teachers;
	}



}
