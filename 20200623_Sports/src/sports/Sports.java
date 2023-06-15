package sports;
import java.util.*;
import static java.util.stream.Collectors.*;

 
/**
 * Facade class for the research evaluation system
 *
 */
public class Sports {
	
	private SortedSet<String> activities = new TreeSet<>();
	private Map<String,Category> categories = new HashMap<>();
	private Map<String,Product> products = new HashMap<>();

    //R1
    /**
     * Define the activities types treated in the portal.
     * The method can be invoked multiple times to add different activities.
     * 
     * @param actvities names of the activities
     * @throws SportsException thrown if no activity is provided
     */
    public void defineActivities (String... activities) throws SportsException {
    	if (activities == null || activities.length == 0)
    		throw new SportsException("No activities passed");
    	
    	for (String a: activities) {
    		this.activities.add(a);
    	}
    }

    /**
     * Retrieves the names of the defined activities.
     * 
     * @return activities names sorted alphabetically
     */
    public List<String> getActivities() {
        return activities.stream().toList();
    }


    /**
     * Add a new category of sport products and the linked activities
     * 
     * @param name name of the new category
     * @param activities reference activities for the category
     * @throws SportsException thrown if any of the specified activity does not exist
     */
    public void addCategory(String name, String... linkedActivities) throws SportsException {
    	if (!activities.containsAll(Arrays.asList(linkedActivities)))
    		throw new SportsException("Some activites are not inserted");
    	
    	Category c = new Category(name, linkedActivities);
    	categories.put(name, c);
    }

    /**
     * Retrieves number of categories.
     * 
     * @return categories count
     */
    public int countCategories() {
        return categories.size();
    }

    /**
     * Retrieves all the categories linked to a given activity.
     * 
     * @param activity the activity of interest
     * @return list of categories (sorted alphabetically)
     */
    public List<String> getCategoriesForActivity(String activity) {
    	if (!activities.contains(activity))
    		return new ArrayList<>();
    	
    	return categories.values().stream()
    		.filter((Category c) -> {
    			return c.getActivities().contains(activity);
    		})
    		.sorted()
    		.map(Category::getName).toList();
    }

    //R2
    /**
     * Add a research group and the relative disciplines.
     * 
     * @param name name of the research group
     * @param disciplines list of disciplines
     * @throws SportsException thrown in case of duplicate name
     */
    public void addProduct(String name, String activityName, String categoryName) throws SportsException {
    	if (products.containsKey(name))
    		throw new SportsException("Product exists");
    	
    	Product p = new Product(name, activityName, categories.get(categoryName));
    	products.put(name, p);
    }

    /**
     * Retrieves the list of products for a given category.
     * The list is sorted alphabetically.
     * 
     * @param categoryName name of the category
     * @return list of products
     */
    public List<String> getProductsForCategory(String categoryName){
    	if (!categories.containsKey(categoryName))
    		return new ArrayList<>();
    	
    	Category c = categories.get(categoryName);
    	
    	return products.values().stream()
    		.filter((Product p) -> {return p.getCategory().equals(c);})
    		.sorted()
    		.map(Product::getName)
    		.toList();
    }

    /**
     * Retrieves the list of products for a given activity.
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @return list of products
     */
    public List<String> getProductsForActivity(String activityName){
    	if (!activities.contains(activityName))
    		return new ArrayList<>();
    	
    	return products.values().stream()
    		.filter((Product p) -> {return p.getActivity().equals(activityName);})
    		.sorted()
    		.map(Product::getName)
    		.toList();
    }

    /**
     * Retrieves the list of products for a given activity and a set of categories
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @param categoryNames names of the categories
     * @return list of products
     */
    public List<String> getProducts(String activityName, String... categoryNames){
    	if (!activities.contains(activityName))
    		return new ArrayList<>();
    	
    	List<String> searchCategoriesNames = Arrays.asList(categoryNames);
    	List<Category> searchCategories = categories.values().stream()
    		.filter((Category c) -> {return searchCategoriesNames.contains(c.getName());})
    		.toList();
    	
    	return products.values().stream()
    		.filter((Product p) -> {
    			return (p.getActivity().equals(activityName) || searchCategories.contains(p.getCategory()));
    		})
    		.sorted()
    		.map(Product::getName)
    		.toList();
    }

    //    //R3
    /**
     * Add a new product rating
     * 
     * @param productName name of the product
     * @param userName name of the user submitting the rating
     * @param numStars score of the rating in stars
     * @param comment comment for the rating
     * @throws SportsException thrown numStars is not correct
     */
    public void addRating(String productName, String userName, int numStars, String comment) throws SportsException {
    	if (products.containsKey(productName)) {
    		products.get(productName).addRating(userName, numStars, comment);
    	}
    }



    /**
     * Retrieves the ratings for the given product.
     * The ratings are sorted by descending number of stars.
     * 
     * @param productName name of the product
     * @return list of ratings sorted by stars
     */
    public List<String> getRatingsForProduct(String productName) {
    	if (!products.containsKey(productName))
    		return new ArrayList<>();
    	
    	return products.get(productName).getRatings().stream()
    		.sorted()
    		.map(Rating::toString)
    		.toList();
    }


    //R4
    /**
     * Returns the average number of stars of the rating for the given product.
     * 
     * 
     * @param productName name of the product
     * @return average rating
     */
    public double getStarsOfProduct (String productName) {
    	if (!products.containsKey(productName))
    		return 0;
    	
    	return products.get(productName).getRatings().stream()
    		.collect(
    			averagingInt(Rating::getStars)
    		);
    }

    /**
     * Computes the overall average stars of all ratings
     *  
     * @return average stars
     */
    public double averageStars() {
    	return products.values().stream()
    		.flatMap((Product p) -> p.getRatings().stream())
    		.collect(
    			averagingInt(Rating::getStars)
    		);
    }

    //R5 Statistiche
    /**
     * For each activity return the average stars of the entered ratings.
     * 
     * Activity names are sorted alphabetically.
     * 
     * @return the map associating activity name to average stars
     */
    public SortedMap<String, Double> starsPerActivity() {
    	SortedMap<String,Double> activityRatings = new TreeMap<>();
    	
    	 Map<String,List<Product>> lp = products.values().stream()
 		.filter((Product p) -> p.getRatings().size() > 0)
 		.collect(
 			groupingBy(Product::getActivity)
 		);
    	for (String act: lp.keySet()) {
    		activityRatings.put(act, lp.get(act).stream()
    			.flatMap((Product p) -> p.getRatings().stream())
    			.collect(
    	    			averagingInt(Rating::getStars)
    	    		));
    	}
        return activityRatings;
    }

    /**
     * For each average star rating returns a list of
     * the products that have such score.
     * 
     * Ratings are sorted in descending order.
     * 
     * @return the map linking the average stars to the list of products
     */
    public SortedMap<Double, List<String>> getProductsPerStars () {
    	SortedMap<Double, List<String>> sp = new TreeMap<Double,List<String>>(Comparator.reverseOrder());
    	
    	Map<String,Double> pr = products.values().stream()
    		.filter((Product p) -> p.getRatings().size() > 0)
    		.collect(
    			toMap(	
    				Product::getName,
    				(Product p) -> p.getAverageRating()
    			)
    		);
    	
    	for (String p : pr.keySet()) {
    		if (!sp.containsKey(pr.get(p)))
    			sp.put(pr.get(p), new ArrayList<String>());
    		sp.get(pr.get(p)).add(p);
    	}
  
        return sp;
    }
}