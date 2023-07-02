package hydraulic;

import java.util.HashSet;
import java.util.ArrayList;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	/**
	 * Constructor
	 * @param name name of the sink element
	 */
	public Sink(String name) {
		super(name);
	}
	
	@Override
	public void connect(Element elem) {
		return;
	}
	
	@Override
	public ArrayList<StringBuilder> getLayout() {
		ArrayList<StringBuilder> l = new ArrayList<StringBuilder>();
		l.add(new StringBuilder(""));
		
		l.get(0).append(this.getStringRepresentation());

		return l;
	}
	
	@Override
	public double simulate(SimulationObserver observer, HashSet<Element> observedElements, Element downstreamRefer, boolean enableMaxFlowCheck) {
		this.inFlow = this.upstream.simulate(observer, observedElements, this, enableMaxFlowCheck);
		if (!observedElements.contains(this)) {
			if (enableMaxFlowCheck && this.inFlow > this.maxInFlow)
				observer.notifyFlowError(this.getClass().getSimpleName(), getName(), inFlow, maxInFlow);
			observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, SimulationObserver.NO_FLOW);
			observedElements.add(this);
		}
		
		return SimulationObserver.NO_FLOW;
	}
}
