package transactions;

public class Transaction implements Comparable<Transaction> {
	
	private String id;
	private Carrier carrier;
	private Request request;
	private Offer offer;
	private int score = 0;
	
	public Transaction(String id, Carrier carrier, Request request, Offer offer) {
		this.id = id;
		this.carrier = carrier;
		this.request = request;
		this.offer = offer;
	}
	
	public String getId() {
		return id;
	}
	
	public Carrier getCarrier() {
		return carrier;
	}
	
	public Request getRequest() {
		return request;
	}
	
	public Offer getOffer() {
		return offer;
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean setScore(int score) {
		if (score < 1 || score > 10)
			return false;
		this.score = score;
		return true;
	}
	
	@Override
	public int compareTo(Transaction oth) {
		return this.id.compareTo(oth.id);
	}
	
	@Override
	public boolean equals(Object o) {
		Transaction oth = (Transaction)o;
		return this.id.equals(oth.id);
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	
}
