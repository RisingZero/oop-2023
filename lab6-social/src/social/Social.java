package social;

import java.util.*;


public class Social {
	
	Map<String,Person> people = new HashMap<String,Person>();
	Map<String,Collection<Person>> groups = new HashMap<String, Collection<Person>>();

	/**
	 * Creates a new account for a person
	 * 
	 * @param code	nickname of the account
	 * @param name	first name
	 * @param surname last name
	 * @throws PersonExistsException in case of duplicate code
	 */
	public void addPerson(String code, String name, String surname)
			throws PersonExistsException {
		if (people.containsKey(code))
			throw new PersonExistsException();
		
		people.put(code, new Person(code, name, surname));
	}

	/**
	 * Retrieves information about the person given their account code.
	 * The info consists in name and surname of the person, in order, separated by blanks.
	 * 
	 * @param code account code
	 * @return the information of the person
	 * @throws NoSuchCodeException
	 */
	public String getPerson(String code) throws NoSuchCodeException {
		if (people.containsKey(code))
			return people.get(code).toString();
		else
			throw new NoSuchCodeException();
	}

	/**
	 * Define a friendship relationship between to people given their codes.
	 * 
	 * Friendship is bidirectional: if person A is friend of a person B, that means that person B is a friend of a person A.
	 * 
	 * @param codePerson1	first person code
	 * @param codePerson2	second person code
	 * @throws NoSuchCodeException in case either code does not exist
	 */
	public void addFriendship(String codePerson1, String codePerson2)
			throws NoSuchCodeException {
		if (!people.containsKey(codePerson1) || !people.containsKey(codePerson2))
			throw new NoSuchCodeException();
		
		Person p1 = people.get(codePerson1);
		Person p2 = people.get(codePerson2);
		
		p1.addFriend(p2);
		p2.addFriend(p1);
	}

	/**
	 * Retrieve the collection of their friends given the code of a person.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return the list of person codes
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> listOfFriends(String codePerson)
			throws NoSuchCodeException {
		if (!people.containsKey(codePerson))
			throw new NoSuchCodeException();
		
		Person p = people.get(codePerson);
		
		return p.getFriends().stream()
			.map(Person::getCode)
			.toList();
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriends(String codePerson)
			throws NoSuchCodeException {
		if (!people.containsKey(codePerson))
			throw new NoSuchCodeException();
		
		Person p = people.get(codePerson);
		Collection<Person> friendsSecondLevel = new ArrayList<Person>();
		
		for (Person f : p.getFriends()) {
			friendsSecondLevel.addAll(f.getFriends());
		}
		
		while (friendsSecondLevel.remove(p)) {};
		
		return friendsSecondLevel.stream()
			.map(Person::getCode)
			.toList();
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * The result has no duplicates.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriendsNoRepetition(String codePerson)
			throws NoSuchCodeException {
		if (!people.containsKey(codePerson))
			throw new NoSuchCodeException();
		
		Person p = people.get(codePerson);
		Collection<Person> friendsSecondLevel = new HashSet<Person>();
		
		for (Person f : p.getFriends()) {
			friendsSecondLevel.addAll(f.getFriends());
		}
		
		friendsSecondLevel.remove(p);
		
		return friendsSecondLevel.stream()
				.map(Person::getCode)
				.toList();
	}

	/**
	 * Creates a new group with the given name
	 * 
	 * @param groupName name of the group
	 */
	public void addGroup(String groupName) {
		groups.put(groupName, new HashSet<Person>());
	}

	/**
	 * Retrieves the list of groups.
	 * 
	 * @return the collection of group names
	 */
	public Collection<String> listOfGroups() {
		return groups.keySet();
	}

	/**
	 * Add a person to a group
	 * 
	 * @param codePerson person code
	 * @param groupName  name of the group
	 * @throws NoSuchCodeException in case the code or group name do not exist
	 */
	public void addPersonToGroup(String codePerson, String groupName) throws NoSuchCodeException {
		if (!people.containsKey(codePerson) || !groups.containsKey(groupName))
			throw new NoSuchCodeException();
		
		Person p = people.get(codePerson);
		groups.get(groupName).add(p);
	}

	/**
	 * Retrieves the list of people on a group
	 * 
	 * @param groupName name of the group
	 * @return collection of person codes
	 */
	public Collection<String> listOfPeopleInGroup(String groupName) {
		if (!groups.containsKey(groupName))
			return null;
		
		return groups.get(groupName).stream()
			.map(Person::getCode)
			.toList();
	}

	/**
	 * Retrieves the code of the person having the largest
	 * group of friends
	 * 
	 * @return the code of the person
	 */
	public String personWithLargestNumberOfFriends() {
		SortedSet<Person> peopleSorted = new TreeSet<Person>(
			new Comparator<Person>() {
				@Override
				public int compare(Person p1, Person p2) {
					return Integer.valueOf(p2.getFriends().size()).compareTo(p1.getFriends().size());
				}
			}
		);
		
		peopleSorted.addAll(people.values());
		return peopleSorted.first().getCode();
	}

	/**
	 * Find the code of the person with largest number
	 * of second level friends
	 * 
	 * @return the code of the person
	 */
	public String personWithMostFriendsOfFriends() {
		SortedSet<Person> peopleSorted = new TreeSet<Person>(
			new Comparator<Person>() {
				@Override
				public int compare(Person p1, Person p2) {
					try {
						return Integer.valueOf(friendsOfFriends(p2.getCode()).size()).compareTo(friendsOfFriends(p1.getCode()).size());
					} catch (NoSuchCodeException e) {return 0;}
				}
			}
		);
		
		peopleSorted.addAll(people.values());
		return peopleSorted.first().getCode();
	}

	/**
	 * Find the name of group with the largest number of members
	 * 
	 * @return the name of the group
	 */
	public String largestGroup() {
		return groups.keySet().stream().max(
			new Comparator<String>() {
				@Override
				public int compare(String g1, String g2) {
					return Integer.valueOf(groups.get(g2).size()).compareTo(groups.get(g1).size());
				}
			}
		).orElse(null);
	}

	/**
	 * Find the code of the person that is member of
	 * the largest number of groups
	 * 
	 * @return the code of the person
	 */
	public String personInLargestNumberOfGroups() {
		Map<String,Integer> groupsPerPerson = new HashMap<String,Integer>();
		
		for (String groupName : groups.keySet()) {
			for (Person p : groups.get(groupName)) {
				groupsPerPerson.put(p.getCode(), Optional.ofNullable(groupsPerPerson.get(p.getCode())).orElse(0) + 1);
			}
		}
		
		return groupsPerPerson.keySet().stream().max(
				new Comparator<String>() {
					@Override
					public int compare(String p1, String p2) {
						return Integer.valueOf(groupsPerPerson.get(p2)).compareTo(groupsPerPerson.get(p1));
					}
				}
			).orElse(null);
	}
}