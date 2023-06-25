package it.polito.oop.books;

import java.util.*;

public class Question {
	
	private String question;
	private Topic topic;
	
	private Set<String> correctAnswers = new HashSet<>();
	private Set<String> incorrectAnswers = new HashSet<>();
	
	public Question(String question, Topic topic) {
		this.question = question;
		this.topic = topic;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public Topic getMainTopic() {
		return topic;
	}

	public void addAnswer(String answer, boolean correct) {
		if (correct)
			correctAnswers.add(answer);
		else
			incorrectAnswers.add(answer);
	}
	
    @Override
    public String toString() {
        return String.format("%s (%s)", question, topic.toString());
    }

	public long numAnswers() {
	    return correctAnswers.size() + incorrectAnswers.size();
	}

	public Set<String> getCorrectAnswers() {
		return correctAnswers;
	}

	public Set<String> getIncorrectAnswers() {
        return incorrectAnswers;
	}
}
