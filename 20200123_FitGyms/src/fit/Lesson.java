package fit;

import java.util.*;

public class Lesson {
	
	private String activity;
	private Gym gym;
	private int maxParticipants;
	private String[] trainers;
	private String actualTrainer;
	private List<Integer> bookedParticipants = new ArrayList<>();
	
	public Lesson(String activity, Gym gym, int maxParticipants, String ...trainers) {
		this.activity = activity;
		this.gym = gym;
		this.maxParticipants = maxParticipants;
		this.trainers = trainers;
	}
	
	public String getActivity() {
		return activity;
	}
	
	public Gym getGym() {
		return gym;
	}
	
	public int getMaxParticipants() {
		return maxParticipants;
	}
	
	public String getActualTrainer() {
		return actualTrainer;
	}
	
	public void setActualTrainer(String name)  throws FitException {
		if (!this.getTrainers().contains(name))
			throw new FitException();
		
		actualTrainer = name;
	}
	
	public List<String> getTrainers() {
		return Arrays.asList(trainers);
	}
	
	public Lesson addParticipant(int customerid) throws FitException {
		if (bookedParticipants.contains(customerid))
			throw new FitException();
		
		bookedParticipants.add(customerid);
		
		return this;
	}
	
	public int getNumberOfParticipants() {
		return bookedParticipants.size();
	}
	
	public List<Integer> getParticipants() {
		return bookedParticipants;
	}
	
	public int getRemainingSeats() {
		return this.getMaxParticipants() - this.getNumberOfParticipants();
	}

}
