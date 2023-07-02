package hydraulic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {
	
	double[] proportions;

	/**
	 * Constructor
	 * @param name the name of the multi-split element
	 * @param numOutput the number of outputs
	 */
	public Multisplit(String name, int numOutput) {
		super(name);
		this.proportions = new double[numOutput];
		for (int i = 0; i < numOutput; i++) {
			this.downstreams.add(null);
		}
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		for (int i = 0; i < this.proportions.length; i++) {
			this.proportions[i] = proportions[i];
		}
	}
	
	@Override
	public double simulate(SimulationObserver observer, HashSet<Element> observedElements, Element downstreamRefer, boolean enableMaxFlowCheck) {
		this.inFlow = this.upstream.simulate(observer, observedElements, this, enableMaxFlowCheck);
		this.outFlow = this.inFlow;
		
		double[] outFlows = new double[this.downstreams.size()];
		for (int i = 0; i < outFlows.length; i++) {
			outFlows[i] = this.outFlow * this.proportions[i];
		}
		if (!observedElements.contains(this)) {
			if (enableMaxFlowCheck && this.inFlow > this.maxInFlow)
				observer.notifyFlowError(this.getClass().getSimpleName(), getName(), inFlow, maxInFlow);
			observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, outFlows);
			observedElements.add(this);
		}
		
		if (downstreamRefer != null) {
			int indexRefer = this.downstreams.indexOf(downstreamRefer);
			return this.outFlow * this.proportions[indexRefer];
		}
		
		return 0.0;
	}
	
	@Override
	public boolean delete() {
		int cnt = 0;
		for (Element el: this.downstreams) {
			if (el != null)
				cnt++;
		}
		
		if (cnt > 1) {
			return false;
		} else if (cnt == 1) {
			this.upstream.downstreams.set(new ArrayList<Element>(Arrays.asList(this.upstream.getOutputs())).indexOf(this), this.downstreams.get(0));
			this.downstreams.get(0).upstream = this.upstream;
			return true;
		} else {
			this.upstream.downstreams.set(new ArrayList<Element>(Arrays.asList(this.upstream.getOutputs())).indexOf(this), null);
			return true;
		}
	}
	
}
