package diet;

import java.util.HashMap;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
	
	private String name;
	private Food foodsRef;
	
	HashMap<String,Double> ingredients = new HashMap<String,Double>();
	private double realQuantity = 0;
	
	public Recipe(String name, Food foods) {
		this.name = name;
		this.foodsRef = foods;
	}
	
	/**
	 * Adds the given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		
		realQuantity += quantity;
		ingredients.put(material, quantity);
		
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	
	@Override
	public double getCalories() {
		double calories = 0;
		
		for (String ingName: ingredients.keySet()) {
			NutritionalElement ingredient = foodsRef.getRawMaterial(ingName);
			calories += ingredient.getCalories() / 100 * ingredients.get(ingName);
		}
		
		return calories / realQuantity * 100;
	}
	

	@Override
	public double getProteins() {
		double proteins = 0;
		
		for (String ingName: ingredients.keySet()) {
			NutritionalElement ingredient = foodsRef.getRawMaterial(ingName);
			proteins += ingredient.getProteins() / 100 * ingredients.get(ingName);
		}
		
		return proteins / realQuantity * 100;
	}

	@Override
	public double getCarbs() {
		double carbs = 0;
		
		for (String ingName: ingredients.keySet()) {
			NutritionalElement ingredient = foodsRef.getRawMaterial(ingName);
			carbs += ingredient.getCarbs() / 100 * ingredients.get(ingName);
		}
		
		return carbs / realQuantity * 100;
	}

	@Override
	public double getFat() {
		double fat = 0;
		
		for (String ingName: ingredients.keySet()) {
			NutritionalElement ingredient = foodsRef.getRawMaterial(ingName);
			fat += ingredient.getFat() / 100 * ingredients.get(ingName);
		}
		
		return fat / realQuantity * 100;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
}
