package it.polito.oop.books;

import java.util.*;


public class ExerciseChapter {
	
	private String title;
	private int numPages;
	
	private List<Question> questions = new ArrayList<Question>();
	
	public ExerciseChapter(String title, int numPages) {
		this.title = title;
		this.numPages = numPages;
	}

    public List<Topic> getTopics() {
    	SortedSet<Topic> topics = new TreeSet<Topic>();
    	
    	topics.addAll(questions.stream()
    		.map(Question::getMainTopic).toList());
    	
        return topics.stream().toList();
	};
	

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
    	this.title = newTitle;
    }

    public int getNumPages() {
        return numPages;
    }
    
    public void setNumPages(int newPages) {
    	this.numPages = newPages;
    }
    

	public void addQuestion(Question question) {
		questions.add(question);
	}	
}
