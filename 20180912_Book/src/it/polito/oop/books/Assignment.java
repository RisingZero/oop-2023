package it.polito.oop.books;

import java.util.*;
import static java.util.stream.Collectors.*;

public class Assignment {
	
	private String id;
	private ExerciseChapter chapter;
	
	private Map<Question,List<String>> answers = new HashMap<>();
	
	public Assignment(String id, ExerciseChapter chapter) {
		this.id = id;
		this.chapter = chapter;
	}

    public String getID() {
        return id;
    }

    public ExerciseChapter getChapter() {
        return chapter;
    }
    
    private double computeScore(Question q) {
    	List<String> ans = answers.get(q);
    	Set<String> cans = q.getCorrectAnswers();
    	Set<String> wans = q.getIncorrectAnswers();
    	
    	double N = q.numAnswers();
    	
    	Set<String> fpSet = new HashSet<>(ans);
    	fpSet.retainAll(wans);
    	double FP = fpSet.size();   
    	
    	Set<String> fnSet = new HashSet<>(cans);
    	fnSet.removeAll(ans);
    	double FN = fnSet.size();
    	
    	return (N - FP - FN) / N;
    	
    }

    public double addResponse(Question q,List<String> answers) {
    	this.answers.put(q, answers);
        return this.computeScore(q);
    }
    
    public double totalScore() {
    	return answers.keySet().stream()
    		.collect(
    			summingDouble((a) -> this.computeScore(a))
    		);
    }

}
