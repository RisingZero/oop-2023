package sports;

public class Rating  implements Comparable<Rating> {
	
	private String userName;
	private int stars;
	private String comment;
	
	public Rating(String userName, int stars, String comment) throws SportsException {
		if (stars < 0 || stars > 5)
			throw new SportsException("Rating out of range");
		
		this.userName = userName;
		this.stars = stars;
		this.comment = comment;
	}
	
	public String getUser() {
		return userName;
	}

	public int getStars() {
		return stars;
	}
	
	public String getComment() {
		return comment;
	}
	
	@Override
	public String toString() {
		return String.format("%d : %s", stars, comment);
	}
	
	@Override
	public int compareTo(Rating oth) {
		// reverse natural order of integers
		return oth.stars - this.stars;
	}
}
