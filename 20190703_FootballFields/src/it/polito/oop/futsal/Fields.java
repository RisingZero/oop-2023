package it.polito.oop.futsal;

import java.util.*;
import static java.util.stream.Collectors.*;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {
	
	public static class Features {
		
	    public boolean indoor; // otherwise outdoor
	    public boolean heating = false;
	    public boolean ac = false;
	    
	    public Features(boolean i, boolean h, boolean a) {
	        this.indoor=i;  
	        this.heating=h;
	        this.ac = a;
	    }
	    
	    public boolean isIndoor() {
	    	return indoor;
	    }
	    
	    public boolean hasHeating() {
	    	return heating;
	    }
	    
	    public boolean hasAC() {
	    	return ac;
	    }   
	    
	    public boolean isCompatible(Features required) {
	    	if (required.isIndoor())
	    		if (!this.isIndoor())
	    			return false;
	    	if (required.hasAC())
	    		if (!this.hasAC())
	    			return false;
	    	if (required.hasHeating())
	    		if (!this.hasHeating())
	    			return false;
	    	return true;
	    }
	}
	
	private Map<Integer,Field> fields = new HashMap<>();
	private Map<Integer,Associate> associates = new HashMap<>();
	
	private int openingTime;
	private int closingTime;
	
	private int nextFieldId = 1;
	private int nextAssociateId = 1;
    
    public void defineFields(Features... features) throws FutsalException {
        for (Features f: features) {
        	if (!f.isIndoor() && (f.hasAC() || f.hasHeating()))
        		throw new FutsalException();
        	Field fi = new Field(f, nextFieldId++);
        	fields.put(fi.getId(), fi);
        }
    }
    
    public long countFields() {
        return fields.size();
    }

    public long countIndoor() {
        return fields.values().stream()
        	.filter((Field f) -> f.getFeatures().isIndoor())
        	.count();
    }
    
    public String getOpeningTime() {
    	return Fields.formatTime(openingTime);
    }
    
    public void setOpeningTime(String time) {
    	openingTime = Fields.parseTime(time);
    }
    
    public String getClosingTime() {
    	return Fields.formatTime(closingTime);
    }
    
    public void setClosingTime(String time) {
    	closingTime = Fields.parseTime(time);
    }

    public int newAssociate(String first, String last, String mobile) {
    	Associate a = new Associate(first, last, mobile, nextAssociateId++);
    	associates.put(a.getId(), a);
        return a.getId();
    }
    
    public String getFirst(int associate) throws FutsalException {
    	if (!associates.containsKey(associate))
    		throw new FutsalException();
        return associates.get(associate).getName();
    	
    }
    
    public String getLast(int associate) throws FutsalException {
    	if (!associates.containsKey(associate))
    		throw new FutsalException();
        return associates.get(associate).getSurname();
    }
    
    public String getPhone(int associate) throws FutsalException {
    	if (!associates.containsKey(associate))
    		throw new FutsalException();
        return associates.get(associate).getPhone();
    }
    
    public int countAssociates() {
        return associates.size();
    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
    	if (!fields.containsKey(field) || !associates.containsKey(associate))
    		throw new FutsalException();
    	
    	int tm = Fields.parseTime(time);
    	
    	System.out.println(tm - openingTime);
    	if (tm < openingTime || tm > closingTime || (tm - openingTime) % 60 != 0)
    		throw new FutsalException();
    	
    	Field f = fields.get(field);
    	Associate ass = associates.get(associate);
    	
    	if (this.isBooked(field, time))
    		throw new FutsalException();
    	
    	f.bookSlot(tm, ass);
    }

    public boolean isBooked(int field, String time) {
    	if (fields.containsKey(field))
    		return fields.get(field).isSlotBooked(Fields.parseTime(time));
        return false;
    }
    

    public int getOccupation(int field) {
        return fields.get(field).getReservations().size();
    }
    
    
    public List<FieldOption> findOptions(String time, Features required){
    	
    	class Solution implements FieldOption, Comparable<FieldOption> {
    		private Field field;
    		
    		public Solution(Field field) {
    			this.field = field;
    		}
    		
    		@Override
    		public int getField() {
    			return field.getId();
    		}
    		
    		@Override
    		public int getOccupation() {
    			return field.getReservations().size();
    		}
    		
    		@Override
    		public int compareTo(FieldOption oth) {
    			if (this.getOccupation() == oth.getOccupation()) {
    				return this.getField() - oth.getField();
    			} else {
    				return this.getOccupation() - oth.getOccupation();
    			}
    		}
    	}
    	
    	return fields.values().stream()
    		.filter((Field f) -> !f.isSlotBooked(Fields.parseTime(time)))
    		.filter((Field f) -> f.getFeatures().isCompatible(required))
    		.map(
    			(Field f) -> (FieldOption)new Solution(f)
    		).sorted().toList();
    }
    
    public long countServedAssociates() {
        return associates.values().stream()
        	.filter((Associate a) -> {
        		for (Field f : fields.values()) {
        			if (f.getReservations().values().contains(a))
        				return true;
        		}
        		return false;
        	}).count();
    }
    
    public Map<Integer,Long> fieldTurnover() {
        return fields.values().stream()
        	.collect(
        		toMap(
        			Field::getId,
        			(Field f) -> (long)this.getOccupation(f.getId())
        		)
        	);
    }
    
    public double occupation() {
    	double noReservations = fields.values().stream().collect(
    		summingInt((Field f) -> this.getOccupation(f.getId()))
    	);
    	
    	double noSlots = (closingTime - openingTime) / 60 * fields.size(); 
        return noReservations/noSlots;
    }
    
    public static int parseTime(String time) {
    	Scanner sc = new Scanner(time);
    	sc.useDelimiter(":");
    	int minutes = sc.nextInt() * 60 + sc.nextInt();
    	sc.close();
    	return minutes;
    }
    
    public static String formatTime(int minutes) {
    	return String.format("%02d:%02d", minutes/60, minutes%60);
    }
    
}
