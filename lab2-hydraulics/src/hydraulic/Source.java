package hydraulic;

import java.util.HashSet;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * Lo status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {

	/**
	 * constructor
	 * @param name name of the source element
	 */
	public Source(String name) {
		super(name);
	}

	/**
	 * Define the flow of the source to be used during the simulation
	 *
	 * @param flow flow of the source (in cubic meters per hour)
	 */
	public void setFlow(double flow){
		this.outFlow = flow;
	}
	
	@Override
	public double simulate(SimulationObserver observer, HashSet<Element> observedElements, Element downstreamRefer, boolean enableMaxFlowCheck) {
		if (!observedElements.contains(this)) {
			observer.notifyFlow(this.getClass().getSimpleName(), getName(), SimulationObserver.NO_FLOW, this.outFlow);
			observedElements.add(this);
		}
		return this.outFlow;
	}
	
	@Override
	public void setMaxFlow(double maxFlow) {
		return;
	}

}
