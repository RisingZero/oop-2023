package it.polito.oop.books;

import java.util.*;
import static java.util.stream.Collectors.*;

public class Book {
	
	private Map<String,Topic> topics = new HashMap<>();
	private List<Question> questions = new ArrayList<>();
	private List<TheoryChapter> tchapters = new ArrayList<>();
	private List<ExerciseChapter> echapters = new ArrayList<>();

    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */
	public Topic getTopic(String keyword) throws BookException {
		if (keyword == null || keyword.isBlank()) 
			throw new BookException();
	    
		if (topics.containsKey(keyword))
			return topics.get(keyword);
		
		Topic t = new Topic(keyword);
		topics.put(keyword, t);
		return t;
	}

	public Question createQuestion(String question, Topic mainTopic) {
		Question q = new Question(question, mainTopic);
		questions.add(q);
        return q;
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
		TheoryChapter tc = new TheoryChapter(title, numPages, text);
		tchapters.add(tc);
        return tc;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
		ExerciseChapter ec = new ExerciseChapter(title, numPages);
		echapters.add(ec);
        return ec;
	}

	public List<Topic> getAllTopics() {
		SortedSet<Topic> fullTopics = new TreeSet<Topic>();
		
		fullTopics.addAll(tchapters.stream()
			.flatMap((tc) -> tc.getTopics().stream())
			.toList());
		
		fullTopics.addAll(echapters.stream()
			.flatMap((tc) -> tc.getTopics().stream())
			.toList());
		
        return fullTopics.stream().toList();
	}

	public boolean checkTopics() {
		List<Topic> ttopics = tchapters.stream()
				.flatMap((tc) -> tc.getTopics().stream())
				.toList();
		
		List<Topic> etopics = echapters.stream()
				.flatMap((tc) -> tc.getTopics().stream())
				.toList();
		
        return ttopics.containsAll(etopics);
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
		Assignment a = new Assignment(ID, chapter);
        return a;
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
    	return questions.stream()
    		.collect(
    			groupingBy(
    				Question::numAnswers,
    				toList()
    			)
    		);
    }
}
