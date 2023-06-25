package it.polito.oop.books;

import java.util.*;


public class TheoryChapter {
	
	private String title;
	private int numPages;
	private String text;
	
	private SortedSet<Topic> topics = new TreeSet<>();
	
	public TheoryChapter(String title, int numPages, String text) {
		this.title = title;
		this.numPages = numPages;
		this.text = text;
	}

    public String getText() {
		return text;
	}

    public void setText(String newText) {
    	this.text = newText;
    }


	public List<Topic> getTopics() {
        return topics.stream().toList();
	}

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
    
    public void addTopic(Topic topic) {
    	Queue<Topic> qtopics = new LinkedList<>();
    	qtopics.add(topic);
    	
    	Topic ctopic;
    	while (qtopics.size() > 0) {
    		ctopic = qtopics.poll();
    		if (ctopic != null) {
    			for (Topic nt: ctopic.getSubTopics()) {
    				if (!this.topics.contains(nt)) {
    					qtopics.add(nt);
    				}
    			}
    			topics.add(ctopic);
    		}
    	}
    }
    
}
