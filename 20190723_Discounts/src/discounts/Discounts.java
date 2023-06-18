package discounts;
import java.util.*;
import static java.util.stream.Collectors.*;

public class Discounts {
	
	private HashMap<Integer,String> issuedCards = new HashMap<>(); 
	private int nextCardId = 1;
	
	private HashMap<String,Product> products = new HashMap<>();
	private HashMap<String, Category> categories = new HashMap<>(); 
	
	private List<Purchase> purchases = new ArrayList<>();
	private int nextPurchaseId = 1;
	
	//R1
	public int issueCard(String name) {
		int cardId = nextCardId++;
		issuedCards.put(cardId, name);
	    return cardId;
	}
	
    public String cardHolder(int cardN) {
        return issuedCards.get(cardN);
    }
    

	public int nOfCards() {
	       return issuedCards.size();
	}
	
	//R2
	public void addProduct(String categoryId, String productId, double price) 
			throws DiscountsException {
		if (products.containsKey(productId))
			throw new DiscountsException();
		
		Category c;
		if (categories.containsKey(categoryId))
			c = categories.get(categoryId);
		else { 
			c = new Category(categoryId);
			categories.put(categoryId,c);
		} 
		
		Product p = new Product(productId, c, price);
		products.put(productId, p);

	}
	
	public double getPrice(String productId) 
			throws DiscountsException {
		if (!products.containsKey(productId))
			throw new DiscountsException();
		
        return products.get(productId).getPrice();
	}

	public int getAveragePrice(String categoryId) throws DiscountsException {
		if (!categories.containsKey(categoryId))
			throw new DiscountsException();
		
		Category cat = categories.get(categoryId);
		
        return Math.round(products.values().stream()
        	.filter((Product p) -> p.getCategory().equals(cat))
        	.collect(
        		averagingDouble(Product::getPrice)
        	).floatValue());
	}
	
	//R3
	public void setDiscount(String categoryId, int percentage) throws DiscountsException {
		if (!categories.containsKey(categoryId))
			throw new DiscountsException();
		
		categories.get(categoryId).setDiscount(percentage);		
	}

	public int getDiscount(String categoryId) {
        return categories.get(categoryId).getDiscount();
	}

	//R4
	public int addPurchase(int cardId, String... items) throws DiscountsException {
		if (!issuedCards.containsKey(cardId))
			throw new DiscountsException();
		
		List<Product> purchaseProducts = new ArrayList<>();
		List<Integer> purchaseQuantities = new ArrayList<>();
		
		String productId;
		int quantity;
		for (String item: items) {
			try {
				Scanner sc = new Scanner(item);
				sc.useDelimiter(":");
				productId = sc.next();
				quantity = sc.nextInt();
				sc.close();
			} catch (Exception e){
				throw new DiscountsException();
			}
			if (!products.containsKey(productId))
				throw new DiscountsException();
			
			purchaseProducts.add(products.get(productId));
			purchaseQuantities.add(quantity);
		}
		
		Purchase purchase = new Purchase(nextPurchaseId++, purchaseProducts, purchaseQuantities, cardId);
		purchases.add(purchase);
		
        return purchase.getId();
	}

	public int addPurchase(String... items) throws DiscountsException {
		List<Product> purchaseProducts = new ArrayList<>();
		List<Integer> purchaseQuantities = new ArrayList<>();
		
		String productId;
		int quantity;
		for (String item: items) {
			try {
				Scanner sc = new Scanner(item);
				sc.useDelimiter(":");
				productId = sc.next();
				quantity = sc.nextInt();
				sc.close();
			} catch (Exception e){
				throw new DiscountsException();
			}
			if (!products.containsKey(productId))
				throw new DiscountsException();
			
			purchaseProducts.add(products.get(productId));
			purchaseQuantities.add(quantity);
		}
		
		Purchase purchase = new Purchase(nextPurchaseId++, purchaseProducts, purchaseQuantities);
		purchases.add(purchase);
		
        return purchase.getId();
	}
	
	public double getAmount(int purchaseCode) {
		return purchases.get(purchaseCode - 1).getDiscountedAmount();
	}
	
	public double getDiscount(int purchaseCode)  {
        return purchases.get(purchaseCode - 1).getDiscountQuantity();
	}
	
	public int getNofUnits(int purchaseCode) {
        return purchases.get(purchaseCode -1).getUnits();
	}
	
	//R5
	public SortedMap<Integer, List<String>> productIdsPerNofUnits() {
		Map<String,Integer> unitsPerProduct = products.values().stream()
			.collect(
				toMap(
					(Product p) -> p.getCode(),
					(Product p) -> {
						return purchases.stream()
							.collect(
								summingInt((Purchase pur) -> pur.getProductUnits(p))
							);
					}
				)
			);
		
		SortedMap<Integer,List<String>> productsPerUnit = products.values().stream().sorted()
		.collect(
			groupingBy (
				(Product p) -> unitsPerProduct.get(p.getCode()),
				TreeMap::new,
				mapping(Product::getCode, toList())
			)
		);
		
		productsPerUnit.remove(0);
        return productsPerUnit;
	}
	
	public SortedMap<Integer, Integer> totalPurchasePerCard() {
		return purchases.stream()
		.filter(Purchase::hasCard)
		.collect(
			groupingBy(
				Purchase::getCard,
				TreeMap::new,
				collectingAndThen(
					summingDouble(
						Purchase::getDiscountedAmount
					),
					(Double d) -> (int)Math.round(d)
				)
			)	
		);
	}
	
	public int totalPurchaseWithoutCard() {
		return purchases.stream()
		.filter((Purchase p) -> !p.hasCard())
		.collect(
			collectingAndThen(
				summingDouble(
					Purchase::getTotAmount
				),
				(Double d) -> (int)Math.round(d)
			)	
		);
	}
	
	public SortedMap<Integer, Integer> totalDiscountPerCard() {
		SortedMap<Integer,Integer> discountsPerCard = purchases.stream()
		.filter(Purchase::hasCard)
		.collect(
			groupingBy(
				Purchase::getCard,
				TreeMap::new,
				collectingAndThen(
					summingDouble(
						Purchase::getDiscountQuantity
					),
					(Double d) -> (int)Math.round(d)
				)
			)
		);
		
		for (int card: discountsPerCard.keySet()) {
			if (discountsPerCard.get(card) == 0)
				discountsPerCard.remove(card);
		}
        return discountsPerCard;
	}


}
