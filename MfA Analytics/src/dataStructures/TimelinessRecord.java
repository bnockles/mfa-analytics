package dataStructures;


import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ui.GuiUtilities;
import ui.VisibleComponent;


public class TimelinessRecord{



	
	Teacher teacher;
	PD pd;
	String teacherName;
	String identifier;
	String status;
	Date time;
	



	public static final String ATTENDED= "Attended";
	public static final String ABSENT= "Absent";
	public static final String EXCUSED= "Excused";
	public static final String CANCELLED= "Cancelled";


	public TimelinessRecord(Teacher t, PD p, String id, String status, Date time){		

		this.teacher = t;
		teacherName=t.getFirstName()+" "+t.getLastName();
		pd = p;
		identifier = id;
		this.status=status;
		this.time = time;

		
	}



	public String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
		return format.format(pd.getDate());
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public String getID() {
		return identifier;
	}

	public String getStatus() {
		if(status.equals(""))status = ABSENT;
		return status;
	}

	public String getLastName() {
		return teacher.getLastName();
	}

	public String getFirstName() {
		return teacher.getFirstName();
	}


	public PD getPd() {
		return pd;
	}
	
	public int getWorkshop() {
		return pd.getWorkshop();
	}

	public Date getTime() {
		return time;
	}


	
	/**
	 * 
	 * @return the time the teacher checked in, formatted to EST time zone, even though actual data is UTC
	 */
	public String getFormattedTime() {
		if(time != null){
			DateFormat df = new SimpleDateFormat(AttendanceCsv.TIMESTAMP_FORMAT);
			return df.format(time);
		}
		else return "";
	}

	@Override
	public boolean equals(Object record2) {
		if(record2 instanceof TimelinessRecord && ((TimelinessRecord) record2).getID().equals(identifier))return true;
		else return false;
	}



	/**
	 *  this method sets the attendance status when a file is being loaded, so a Date is required so that it pulls from the record
	 * @param status
	 * @param date
	 */
	public void setStatus(String status) {
		this.status = status;


	}





}
