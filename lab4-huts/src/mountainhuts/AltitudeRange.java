package mountainhuts;

public class AltitudeRange implements Comparable<AltitudeRange> {
	
	private int low;
	private int high;
	
	public AltitudeRange(int low, int high) {
		this.low = low;
		this.high = high;
	}
	
	public AltitudeRange(String range) {
		low = Integer.parseInt(range.split("-")[0]);
		high = Integer.parseInt(range.split("-")[1]);
	}
	
	public int getLowerBound() {
		return low;
	}
	
	public int getHigherBound() {
		return high;
	}
	
	public boolean checkRange(int altitude) {
		return altitude > low && altitude <= high;
	}
	
	@Override
	public String toString() {
		return String.format("%d-%d", low, high);
	}

	@Override
	public int compareTo(AltitudeRange o) {
		if (this.high <= o.low)
			return -1;
		else if (this.low >= o.high)
			return 1;
		else
			return 0;
	}
}
