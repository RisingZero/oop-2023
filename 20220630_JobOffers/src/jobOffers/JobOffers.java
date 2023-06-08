package jobOffers; 
import java.util.*;
import static java.util.stream.Collectors.*;

public class JobOffers  {
	
	Set<String> skills = new HashSet<String>();
	Map<String,Collection<SkillWithLevel>> positions = new HashMap<String,Collection<SkillWithLevel>>();
	Map<String,Collection<SkillWithLevel>> candidates = new HashMap<String,Collection<SkillWithLevel>>();
	TreeMap<String,List<String>> applications = new TreeMap<String,List<String>>();		// position -> candidates
	Map<String,Collection<SkillWithLevel>> consultants = new HashMap<String,Collection<SkillWithLevel>>();
	
//R1
	public int addSkills (String... skills) {
		this.skills.addAll(Arrays.asList(skills));
		return this.skills.size();
	}
	
	public int addPosition (String position, String... skillLevels) throws JOException {
		if (positions.containsKey(position))
			throw new JOException("Position already entered");
		
		Collection<SkillWithLevel> skillsRequired = new ArrayList<SkillWithLevel>();
		
		String skillName;
		Integer skillLevel;
		for (String sl : skillLevels) {
			try {
				Scanner sc = new Scanner(sl).useDelimiter(":");
				skillName = sc.next();
				skillLevel = sc.nextInt();
				sc.close();
			} catch (Exception e) {
				throw new JOException("Skill bad formatted");
			}
			if (!skills.contains(skillName))
				throw new JOException("Skill not entered");
			skillsRequired.add(new SkillWithLevel(skillName, skillLevel, false));
		}
		
		positions.put(position, skillsRequired);
		
		return (int)Math.floor(skillsRequired.stream()
			.collect(
					averagingInt(SkillWithLevel::getLevel)
			));
	}
	
//R2	
	public int addCandidate (String name, String... skills) throws JOException {
		if (candidates.containsKey(name))
			throw new JOException("Candidate already entered");
		
		Collection<SkillWithLevel> skillsOfCandidate = new ArrayList<SkillWithLevel>();
		
		for (String s : skills) {
			if (!this.skills.contains(s))
				throw new JOException("Skill not entered");
			skillsOfCandidate.add(new SkillWithLevel(s));
		}
		
		candidates.put(name, skillsOfCandidate);
		
		return skillsOfCandidate.size();
	}
	
	public List<String> addApplications (String candidate, String... positions) throws JOException {
		if (!candidates.containsKey(candidate))
			throw new JOException("Candidate not entered");
		
		Collection<SkillWithLevel> candidateSkills = this.candidates.get(candidate);
		Map<String,Collection<String>> validApplications = new HashMap<String,Collection<String>>();
		
		for (String pos : positions) {
			if (!this.positions.containsKey(pos))
				throw new JOException("Position not entered");
			
			TreeSet<String> positionOpenApplications = new TreeSet<>(Optional.ofNullable(this.applications.get(pos)).orElse(new ArrayList<String>()));
			
			for (SkillWithLevel skill : this.positions.get(pos)) {
				if (!candidateSkills.contains(skill))
					throw new JOException(String.format("Candidate doesn't have required skills for position %s, missing %s", pos, skill));
			}
			
			positionOpenApplications.add(candidate);
			validApplications.put(pos,positionOpenApplications);
		}
		
		validApplications.forEach(
			(String pos, Collection<String> app) -> {
				this.applications.put(pos,app.stream().toList());			
			}
		);
		
		TreeMap<String,TreeSet<String>> posForCand = new TreeMap<String,TreeSet<String>>();
		for (String pos: this.applications.keySet()) {
			for (String cand : this.applications.get(pos)) {
				if (!posForCand.containsKey(cand))
					posForCand.put(cand, new TreeSet<String>());
				
				posForCand.get(cand).add(pos);
			}
		}
		
		List<String> out = new ArrayList<String>();
		posForCand.forEach(
			(String c, Collection<String> pList) -> {
				pList.forEach((String p) -> {
					if (c.equals(candidate))
						out.add(String.format("%s:%s", c, p));
				});
			}
		);
		
		return out;
	} 
	
	public TreeMap<String, List<String>> getCandidatesForPositions() {
		return this.applications;
	}
	
	
//R3
	public int addConsultant (String name, String... skills) throws JOException {
		if (consultants.containsKey(name))
			throw new JOException("Consultant already entered");
		
		Collection<SkillWithLevel> consultantSkills = new ArrayList<SkillWithLevel>();
		
		for (String s : skills) {
			if (!this.skills.contains(s))
				throw new JOException("Skill not entered");
			consultantSkills.add(new SkillWithLevel(s));
		}
		
		consultants.put(name, consultantSkills);
		
		return consultantSkills.size();
	}
	
	public Integer addRatings (String consultant, String candidate, String... skillRatings)  throws JOException {
		if (!consultants.containsKey(consultant))
			throw new JOException("Consultant not entered");
		
		if (!candidates.containsKey(candidate))
			throw new JOException("Candidate not entered");
		
		Collection<SkillWithLevel> skillsRating = new ArrayList<SkillWithLevel>();
		
		String skillName;
		Integer skillLevel;
		for (String sr : skillRatings) {
			try {
				Scanner sc = new Scanner(sr).useDelimiter(":");
				skillName = sc.next();
				skillLevel = sc.nextInt();
				sc.close();
			} catch (Exception e) {
				throw new JOException("Skill bad formatted");
			}
			if (!skills.contains(skillName))
				throw new JOException("Skill not entered");
			
			if (!consultants.get(consultant).contains(new SkillWithLevel(skillName)))
				throw new JOException("The consultant doesn't have the skill " + skillName);
			skillsRating.add(new SkillWithLevel(skillName, skillLevel, true));
		}
		
		Collection<SkillWithLevel> candSkills = new ArrayList<SkillWithLevel>(this.candidates.get(candidate));
		Collection<SkillWithLevel> consSkills = new ArrayList<SkillWithLevel>(this.consultants.get(consultant));
		if (!consSkills.containsAll(candSkills))
			throw new JOException("Consultant doesn't have all candidate skills");
	
		skillsRating.forEach((SkillWithLevel s) -> {
			this.candidates.get(candidate).forEach((SkillWithLevel cs) -> {
				try {
					if (cs.equals(s))
						cs.update(s);
				} catch (JOException e) {}
			});
		});
		
		return (int)Math.floor(skillsRating.stream()
			.collect(
					averagingInt(SkillWithLevel::getLevel)
			));
	}
	
//R4
	public List<String> discardApplications() {
		Map<String,Collection<String>> discarded = new TreeMap<String,Collection<String>>();		// candidate -> positions
		
		for (String position : applications.keySet()) {
			List<SkillWithLevel> posSkills = positions.get(position).stream().toList();
			for (String candidate : applications.get(position)) {
				List<SkillWithLevel> candSkills = candidates.get(candidate).stream().toList();
				
				boolean eligible = true;
				for (SkillWithLevel skill: posSkills) {
					if (!candSkills.contains(skill)) {
						eligible = false;
						break;
					}
					
					if (candSkills.get(candSkills.indexOf(skill)).getLevel() < skill.getLevel()) {
						eligible = false;
						break;
					}
				}
				if (!eligible) {
					if (!discarded.containsKey(candidate))
						discarded.put(candidate, new TreeSet<String>());
					
					discarded.get(candidate).add(position);
				}
			}
		}
		
		List<String> out = new ArrayList<String>();
		discarded.forEach(
				(String c, Collection<String> pList) -> {
					pList.forEach((String p) -> {
						out.add(String.format("%s:%s", c, p));
					});
				}
			);
		return out;
	}
	
	 
	public List<String> getEligibleCandidates(String position) {
		TreeSet<String> eligibles = new TreeSet<String>();
		
		List<SkillWithLevel> posSkills = positions.get(position).stream().toList();
		for (String candidate : applications.get(position)) {
			List<SkillWithLevel> candSkills = candidates.get(candidate).stream().toList();
			
			boolean eligible = true;
			for (SkillWithLevel skill: posSkills) {
				if (!candSkills.contains(skill)) {
					eligible = false;
					break;
				}
				
				if (candSkills.get(candSkills.indexOf(skill)).getLevel() < skill.getLevel()) {
					eligible = false;
					break;
				}
			}
			if (eligible) {
				eligibles.add(candidate);
			}
		}
		
		return eligibles.stream().toList();
	}
	

	
}

		
