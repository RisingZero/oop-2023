package sports;

import java.util.List;
import java.util.Arrays;

public class Category  implements Comparable<Category> {
	
	private String name;
	private List<String> linkedActivities;
	
	public Category(String name, String ...activities) {
		this.name = name;
		linkedActivities = Arrays.asList(activities);
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getActivities() {
		return linkedActivities;
	}
	
	public boolean equals(Object o) {
		Category oth = (Category)o;
		return this.name.equals(oth.name);
	}

	@Override
	public int compareTo(Category oth) {
		return this.name.compareTo(oth.name);
	}
	
}
