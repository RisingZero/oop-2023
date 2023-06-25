package transactions;
import java.util.*;
import java.util.Map.Entry;

import static java.util.stream.Collectors.*;

//import static java.util.Comparator.*;

public class TransactionManager {
	
	private Set<String> places = new HashSet<>();
	private Map<String,Region> regions = new HashMap<>();
	private Map<String,Carrier> carriers = new HashMap<>();
	
	private Map<String,Request> requests = new HashMap<>();
	private Map<String,Offer> offers = new HashMap<>();
	
	private Map<String,Transaction> transactions = new HashMap<>();
//R1
	public List<String> addRegion(String regionName, String... placeNames) {
		Region r = new Region(regionName);
		regions.put(regionName, r);
		
		for (String place: placeNames) {
			r.addPlace(place);
			places.add(place);
		}
		
		return r.getPlaces();
	}
	
	public List<String> addCarrier(String carrierName, String... regionNames) {
		Carrier c = new Carrier(carrierName);
		carriers.put(carrierName, c);
		
		for (String rName: regionNames) {
			if (regions.)
			c.addRegion(regions.get(rName));
		}

		return c.getServedRegions().stream().map(Region::getName).toList();
	}
	
	public List<String> getCarriersForRegion(String regionName) {
		return carriers.values().stream().sorted()
			.filter((c) -> c.getServedRegions().contains(regions.get(regionName)))
			.map(Carrier::getName).toList();
	}
	
//R2
	public void addRequest(String requestId, String placeName, String productId) 
			throws TMException {
		if (!places.contains(placeName))
			throw new TMException();
		
		if (requests.containsKey(requestId))
			throw new TMException();
		
		Request rq = new Request(requestId, placeName, productId);
		requests.put(requestId, rq);
	}
	
	public void addOffer(String offerId, String placeName, String productId) 
			throws TMException {
		if (!places.contains(placeName))
			throw new TMException();
		
		if (offers.containsKey(offerId))
			throw new TMException();
		
		Offer of = new Offer(offerId, placeName, productId);
		offers.put(offerId, of);
	}
	

//R3
	public void addTransaction(String transactionId, String carrierName, String requestId, String offerId) 
			throws TMException {
		Carrier carrier = carriers.get(carrierName);
		Request request = requests.get(requestId);
		Offer offer = offers.get(offerId);
		
		if (request.getProductId() != offer.getProductId())
			throw new TMException();
		
		List<String> servedPlaces = carrier.getServedRegions().stream()
			.flatMap((r) -> r.getPlaces().stream())
			.toList();
		
		if (!servedPlaces.contains(request.getPlace()) || !servedPlaces.contains(offer.getPlace()))
			throw new TMException();
		
		if (transactions.values().stream()
			.filter((t) -> t.getRequest().equals(request) || t.getOffer().equals(offer))
			.count() > 0)
			throw new TMException();
		
		Transaction t = new Transaction(transactionId, carrier, request, offer);
		transactions.put(transactionId, t);
	}
	
	public boolean evaluateTransaction(String transactionId, int score) {
		return transactions.get(transactionId).setScore(score);
	}
	
//R4
	public SortedMap<Long, List<String>> deliveryRegionsPerNT() {
		return transactions.values().stream()
			.collect(
				groupingBy(
					(Transaction t) -> {
						return this.regions.values().stream().filter(r -> r.getPlaces().contains(t.getRequest().getPlace())).toList().get(0).getName();
					},
					counting()
				)
			).entrySet().stream()
			.collect(
				groupingBy(
					Entry::getValue,
					TreeMap::new,
					mapping(
						Entry::getKey,
						toList())
				)
			);
	}
	
	public SortedMap<String, Integer> scorePerCarrier(int minimumScore) {
		return transactions.values().stream()
			.filter(t ->  t.getScore() >= minimumScore)
			.collect(
				groupingBy(
					t -> t.getCarrier().getName(),
					TreeMap::new,
					summingInt(Transaction::getScore)
				)
			);
	}
	
	public SortedMap<String, Long> nTPerProduct() {
		return transactions.values().stream()
			.collect(
				groupingBy(
					t -> t.getRequest().getProductId(),
					TreeMap::new,
					counting()
				)
				
			);
	}
	
	
}

