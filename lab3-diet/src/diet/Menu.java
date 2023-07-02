package diet;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	
	private String name;
	private Food foodsRef;
	
	HashMap<String,Double> recipes = new HashMap<String,Double>();
	ArrayList<String> products = new ArrayList<String>();
	
	public Menu(String name, Food foods) {
		this.name = name;
		this.foodsRef = foods;
	}

	/**
	 * Adds a given serving size of a recipe.
	 * The recipe is a name of a recipe defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
    public Menu addRecipe(String recipe, double quantity) {
    	
    	recipes.put(recipe, quantity);
    	
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
    public Menu addProduct(String product) {
    	
    	products.add(product);
    	
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		double calories = 0;
		
		for (String recipeName: recipes.keySet()) {
			NutritionalElement recipe = foodsRef.getRecipe(recipeName);
			
			calories += recipe.getCalories() / 100 * recipes.get(recipeName);
		}
		
		for (String productName: products) {
			NutritionalElement product = foodsRef.getProduct(productName);
			
			calories += product.getCalories();
		}
		
		return calories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		double proteins = 0;
		
		for (String recipeName: recipes.keySet()) {
			NutritionalElement recipe = foodsRef.getRecipe(recipeName);
			
			proteins += recipe.getProteins() / 100 * recipes.get(recipeName);
		}
		
		for (String productName: products) {
			NutritionalElement product = foodsRef.getProduct(productName);
			
			proteins += product.getProteins();
		}
		
		return proteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		double carbs = 0;
		
		for (String recipeName: recipes.keySet()) {
			NutritionalElement recipe = foodsRef.getRecipe(recipeName);
			
			carbs += recipe.getCarbs() / 100 * recipes.get(recipeName);
		}
		
		for (String productName: products) {
			NutritionalElement product = foodsRef.getProduct(productName);
			
			carbs += product.getCarbs();
		}
		
		return carbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		double fat = 0;
		
		for (String recipeName: recipes.keySet()) {
			NutritionalElement recipe = foodsRef.getRecipe(recipeName);
			
			fat += recipe.getFat() / 100 * recipes.get(recipeName);
		}
		
		for (String productName: products) {
			NutritionalElement product = foodsRef.getProduct(productName);
			
			fat += product.getFat();
		}
		
		return fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return false;
	}
}