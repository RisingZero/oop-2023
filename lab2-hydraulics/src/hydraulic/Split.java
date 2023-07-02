package hydraulic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {

	/**
	 * Constructor
	 * @param name name of the split element
	 */
	public Split(String name) {
		super(name);
	}
	
	@Override
	public void connect(Element elem, int index){
		if (index > this.downstreams.size()) {
			return;
		} else if (index == this.downstreams.size()) {
			this.downstreams.add(elem);
		} else {
			this.downstreams.set(index, elem);
		}
		elem.upstream = this;
	}
	
	@Override
	public double simulate(SimulationObserver observer, HashSet<Element> observedElements, Element downstreamRefer, boolean enableMaxFlowCheck) {
		this.inFlow = this.upstream.simulate(observer, observedElements, this, enableMaxFlowCheck);
		this.outFlow = this.inFlow / this.downstreams.size();
		
		double[] outFlows = new double[this.downstreams.size()];
		for (int i = 0; i < outFlows.length; i++) {
			outFlows[i] = this.outFlow;
		}
		if (!observedElements.contains(this)) {
			if (enableMaxFlowCheck && this.inFlow > this.maxInFlow)
				observer.notifyFlowError(this.getClass().getSimpleName(), getName(), inFlow, maxInFlow);
			observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, outFlows);

			observedElements.add(this);
		}
		
		return this.outFlow;
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
