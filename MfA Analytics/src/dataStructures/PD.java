package dataStructures;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.management.RuntimeErrorException;

import ui.ViewerLabel;
import ui.VisibleComponent;


public class PD extends ViewerLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5836136225145243870L;
	String title;
	int numberOfWorkshops;
	ArrayList<Date> dates;//for multiple session PDs
	ArrayList<String> locations;
	private int numberOfParticipants;
	
	
	/**
	 * constructor for single multi-session PDs
	 * @param name
	 * @param number number of session (session 1, session 2, etc)
	 * @param records
	 * @param date
	 */
	public PD(String name, int number, Date date,  String location){
		super(name,0,0);
		numberOfParticipants = 0;
		title = name;
		dates = new ArrayList<Date>();
		locations = new ArrayList<String>();
		dates.add(date);
		numberOfWorkshops = number;
		locations.add(location);
	}

	public String getTitle() {
		return title;
	}
	
	public String toString(){
		return title;
	}

	
	public List<Date> getDate() {
		return dates;
	}
	
	public String getDateString(int i) {
		if(i< 0 || i> dates.size())throw new RuntimeException("This PD met only "+numberOfWorkshops+" time(s). You cannot request the date of meeting #"+(i+1)+".");
		else{
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
			return format.format(dates.get(i));
		}
	}
	
	public int getWorkshop(){
		return numberOfWorkshops;
	}
	
	@Override
	public boolean equals(Object record2) {
		if(record2 instanceof PD && ((PD) record2).getTitle().equals(title))return true;
		else return false;
	}

	public List<String> getLocation() {
		return locations;
	}

	public void addDate(Date date) {
		if(!dates.contains(date))
		dates.add(date);
	}
	
	public void addLocation(String location) {
		if(!locations.contains(location))
			locations.add(location);;
	}

	public void updateWorkshopNumber(int workshopNumber) {
		if(workshopNumber > this.numberOfWorkshops){
			this.numberOfWorkshops = workshopNumber;
		}
		
	}

	public void countParticipants() {
		List<String> distinctNames = new ArrayList<String>();
		for(TimelinessRecord t: timestamps){
			if(!distinctNames.contains(t.getFirstName()+" "+t.getLastName())){
				distinctNames.add(t.getFirstName()+" "+t.getLastName());
			}
		}
		numberOfParticipants= distinctNames.size();
	}
	
	public int getNumberOfParticipants(){
		return numberOfParticipants;
	}


}
