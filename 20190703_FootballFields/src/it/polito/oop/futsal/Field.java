package it.polito.oop.futsal;

import java.util.*;
import it.polito.oop.futsal.Fields.Features;

public class Field {
	
	private int id;
	private Features features;
	
	private Map<Integer,Associate> bookedSlots = new HashMap<>();
	
	public Field(Features f, int id) {
		this.id = id;
		this.features = f;
	}
	
	public int getId() {
		return id;
	}
	
	public Features getFeatures() {
		return features;
	}
	
	public void bookSlot(int time, Associate ass) {
		bookedSlots.put(time,ass);
	}
	
	public boolean isSlotBooked(int time) {
		return bookedSlots.containsKey(time);
	}
	
	public Map<Integer,Associate> getReservations() {
		return bookedSlots;
	}

}
