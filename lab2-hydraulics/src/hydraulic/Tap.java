package hydraulic;

import java.util.HashSet;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {
	
	private boolean open;

	/**
	 * Constructor
	 * @param name name of the tap element
	 */
	public Tap(String name) {
		super(name);
	}

	/**
	 * Set whether the tap is open or not. The status is used during the simulation.
	 *
	 * @param open opening status of the tap
	 */
	public void setOpen(boolean open){
		this.open = open;
	}
	
	@Override
	public double simulate(SimulationObserver observer, HashSet<Element> observedElements, Element downstreamRefer, boolean enableMaxFlowCheck) {
		this.inFlow = this.upstream.simulate(observer, observedElements, this, enableMaxFlowCheck);
		this.outFlow = (this.open) ? this.inFlow : 0;
		if (!observedElements.contains(this)) {
			if (enableMaxFlowCheck && this.inFlow > this.maxInFlow)
				observer.notifyFlowError(this.getClass().getSimpleName(), getName(), inFlow, maxInFlow);
			observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, outFlow);
			observedElements.add(this);
		}
		
		return this.outFlow;
		
	}
	
}
