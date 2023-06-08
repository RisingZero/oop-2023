package jobOffers;

public class SkillWithLevel {
	private String skill;
	private int level;
	
	public SkillWithLevel(String skill, int level, boolean isConsultant) throws JOException {
		if (level < 4 || level > (isConsultant ? 10 : 8))
			throw new JOException("Skill level not in range");
		
		this.skill = skill;
		this.level = level;
	}
	
	public SkillWithLevel(String skill) {
		this.skill = skill;
		this.level = 0;
	}
	
	public String getSkill() {
		return skill;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void update(SkillWithLevel skill) throws JOException {
		if (!this.equals(skill))
			throw new JOException("Skills are not matching");
		
		this.level = skill.level;
	}
	
	@Override
	public boolean equals(Object o) {
		SkillWithLevel oth = (SkillWithLevel)o;
		return this.skill.equals(oth.skill);
	}
	
	@Override
	public String toString() {
		return String.format("%s:%d", skill,level);
	}
}
