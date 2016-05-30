package dataStructures;


/**
 * @author bnockles
 *
 */

import java.util.ArrayList;
import java.util.Date;

import ui.ViewerLabel;

public class Teacher extends ViewerLabel{



	private String lastName;
    private String firstName;




    public Teacher(String ln,String fn){
		super(ln+", "+fn,0,0);
    	lastName = ln;
        firstName = fn;
        
       
    }

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

    
	
	public String toString(){
		return firstName + " " + lastName;
	}

	public String getName() {
		return firstName+ " "+lastName;
	}
	
	public boolean equals(Object t){
		if(t instanceof Teacher){
			if(((Teacher)t).getName().equals(getName()))return true;
		}
		return false;
		
	}
	


    
}
