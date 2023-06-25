package it.polito.oop.books;

import java.util.*;

public class Topic implements Comparable<Topic> {
	
	private String keyword;
	private SortedSet<Topic> subtopics = new TreeSet<>();
	
	
	public Topic(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
        return keyword;
	}
	
	@Override
	public String toString() {
	    return keyword;
	}

	public boolean addSubTopic(Topic topic) {
		return subtopics.add(topic);
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
        return subtopics.stream().toList();
	}
	
	
	@Override
	public int compareTo(Topic oth) {
		return this.keyword.compareTo(oth.keyword);
	}
	
	@Override
	public boolean equals(Object o) {
		Topic oth = (Topic)o;
		return this.keyword.equals(oth.keyword);
	}
	
	@Override
	public int hashCode() {
		return this.keyword.hashCode();
	}
}
