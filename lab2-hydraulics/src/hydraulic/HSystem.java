package hydraulic;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Main class that acts as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	private ArrayList<Element> components = new ArrayList<Element>();
	

// R1
	/**
	 * Adds a new element to the system
	 * @param elem the new element to be added to the system
	 */
	public void addElement(Element elem){
		this.components.add(elem);
	}
	
	/**
	 * returns the element added so far to the system
	 * @return an array of elements whose length is equal to 
	 * 							the number of added elements
	 */
	public Element[] getElements(){
		Element[] componentsArray = this.components.toArray(new Element[this.components.size()]);
		return componentsArray;
	}

// R4
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		HashSet<Element> observedElements = new HashSet<Element>();
		for (Element e: this.components) {
			e.simulate(observer, observedElements, null, false);
		}
	}

// R6
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		ArrayList<StringBuilder> output = new ArrayList<StringBuilder>();
		
		for (Element el: this.components) {
			if (el instanceof Source) {
				ArrayList<StringBuilder> outlist = el.getLayout();
				StringBuilder x = new StringBuilder("");
				for (StringBuilder row: outlist) {
					x.append(row.toString() + "\n");
				}
				output.add(x);
			}
		}
		
		StringBuilder o = new StringBuilder("");
		for (StringBuilder row: output) {
			o.append(row.toString());
		}
		
		return o.toString();
	}

// R7
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public boolean deleteElement(String name) {
		for (Element el: this.components) {
			if (el.getName() == name) {
				if (el.delete()) {
					this.components.remove(el);
					return true;
				} else
					return false;
			}
		}
		return false;
	}

// R8
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		HashSet<Element> observedElements = new HashSet<Element>();
		for (Element e: this.components) {
			e.simulate(observer, observedElements, null, enableMaxFlowCheck);
		}
	}
}
