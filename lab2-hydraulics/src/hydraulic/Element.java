package hydraulic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 * 
 * The class is abstract since it is not intended to be instantiated,
 * though all methods are defined to make subclass implementation easier.
 */
public abstract class Element {
	
	private String name;
	protected Element upstream;
	protected ArrayList<Element> downstreams = new ArrayList<Element>();
	
	protected double inFlow;
	protected double maxInFlow;
	protected double outFlow;
	
	
	public Element(String name) {
		this.name = name;
	}
	
	/**
	 * getter method for the name of the element
	 * 
	 * @return the name of the element
	 */
	public String getName() {
		return this.name;
	}
	
	public String getStringRepresentation() {
		StringBuilder str = new StringBuilder("");
		
		str.append("[" + this.name + "]");
		str.append(this.getClass().getSimpleName());
		
		return str.toString();
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * In case of element with multiple outputs this method operates on the first one,
	 * it is equivalent to calling {@code connect(elem,0)}. 
	 * 
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem) {
		if (this.downstreams.size() > 0)
			this.downstreams.set(0, elem);
		else
			this.downstreams.add(elem);
		elem.upstream = this;
	}
	
	/**
	 * Connects a specific output of this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * @param elem the element that will be placed downstream
	 * @param index the output index that will be used for the connection
	 */
	public void connect(Element elem, int index){
		// does nothing by default
	}
	
	/**
	 * Retrieves the single element connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element getOutput(){
		return (this.downstreams.size() > 0) ? this.downstreams.get(0) : null;
	}

	/**
	 * Retrieves the elements connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element[] getOutputs(){
		return this.downstreams.toArray(new Element[this.downstreams.size()]);
	}
	
	public double simulate(SimulationObserver observer, HashSet<Element> observedElements, Element downstreamRefer, boolean enableMaxFlowCheck) {
		// Analyze my state and recursively analyze connected objects
		this.inFlow = this.upstream.simulate(observer, observedElements, this, enableMaxFlowCheck);
		this.outFlow = this.inFlow;
		if (!observedElements.contains(this)) {
			if (enableMaxFlowCheck && this.inFlow > this.maxInFlow)
				observer.notifyFlowError(this.getClass().getSimpleName(), getName(), inFlow, maxInFlow);
			observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, outFlow);
			observedElements.add(this);
		}
		
		return this.outFlow;
	}
	
	public ArrayList<StringBuilder> getLayout() {
		ArrayList<StringBuilder> l = new ArrayList<StringBuilder>();
		String myrepr = this.getStringRepresentation();
		
		if (this.downstreams.size() == 0) {
			l.add(new StringBuilder(""));
			l.get(0).append(myrepr + " -> *");
			return l;
		}
		
		int cnt = 0;
		for (int i = 0; i < this.downstreams.size(); i++) {
			ArrayList<StringBuilder> downl;
			if (this.downstreams.get(i) == null) {
				downl = new ArrayList<StringBuilder>();
				downl.add(new StringBuilder("*"));
			}
			else
				downl = this.downstreams.get(i).getLayout();
			for (int j = 0; j < downl.size(); j++) {
				l.add(new StringBuilder(""));
				
				if (j == 0) {
					if (this.downstreams.size() == 1) {
						l.get(j + cnt).append(myrepr + " -> ");
					} else {
						if (i == 0)
							l.get(j + cnt).append(myrepr + " +-> ");
						else {
							for (int k = 0; k < myrepr.length(); k++)
								l.get(j + cnt).append(" ");
							l.get(j + cnt).append(" +-> ");
						}
					}
				} else {
					if (i != (this.downstreams.size() - 1)) {
						for (int k = 0; k < myrepr.length(); k++)
							l.get(j + cnt).append(" ");
						l.get(j + cnt).append(" |   ");
					}
				}
				
				if (this.downstreams.size() == 1 && j > 0) {
					for (int k = 0; k < myrepr.length() + 4; k++)
						l.get(j + cnt).append(" ");
				}
				l.get(j + cnt).append(downl.get(j).toString());
			}
			if (this.downstreams.size() > 1 && i != (this.downstreams.size() - 1)) {
				StringBuilder pad = new StringBuilder("");
				for (int k = 0; k < myrepr.length(); k++)
					pad.append(" ");
				pad.append(" |   ");
				l.add(pad);
			}
			cnt += downl.size() + 1;
		}
		
		return l;
	}
	
	public boolean delete() {
		if (this.upstream != null && this.downstreams.size() > 0 && this.downstreams.get(0) != null) {
			this.upstream.downstreams.set(new ArrayList<Element>(Arrays.asList(this.upstream.getOutputs())).indexOf(this), this.downstreams.get(0));
			this.downstreams.get(0).upstream = this.upstream;
			return true;
		} else if (this.downstreams.size() == 0 || this.downstreams.get(0) == null) {
			this.upstream.downstreams.set(new ArrayList<Element>(Arrays.asList(this.upstream.getOutputs())).indexOf(this), null);
			return true;
		}
		return false;
	}
	
	/**
	 * Defines the maximum input flow acceptable for this element
	 * 
	 * @param maxFlow maximum allowed input flow
	 */
	public void setMaxFlow(double maxFlow) {
		this.maxInFlow = maxFlow;
	}
}
