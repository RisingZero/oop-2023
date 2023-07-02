package hydraulic;

/**
 * Interface for simulation observers.
 * 
 * Any class interested in being notified about the
 * flow computations performed during the simulation
 * must implement this interface.
 *  
 *
 */
public interface SimulationObserver {
	/**
	 * Constant used to mark a flow as missing,
	 * (e.g. for the classes {@link Source} and {@link Sink})
	 */
	final static double NO_FLOW = Double.NaN;
	
	/**
	 * Notification levels
	 * 
	 */
	enum Level {
		/**
		 * show the information about the status of an element during the simulation
		 */
		Status, 
		/**
		 * notify an error detected on an element during the simulation
		 */
		Error };
	
	/**
	 * General purpose notification method to collect information and errors
	 * during the simulation of an hydraulic system.
	 * 
	 * @param level		kind of notification being sent
	 * @param type 		class name of the element 
	 * @param name 		name of the element
	 * @param inFlow 	input flow for the element
	 * @param flows 	additional information, depending on the kind of notification 
	 */
	public void notify(Level level, String type, String name, double inFlow, double... flows);

	/**
	 * Simulation element info notification method.
	 * 
	 * In case of missing flow (e.g. for the classes {@link Source}
	 * and {@link Sink}) where either the input or output flows
	 * are undefined the constant {@link #NO_FLOW} has to be used.
	 * 
	 * @param type class name of the element 
	 * @param name name of the element
	 * @param inFlow input flow for the element
	 * @param outFlow output flow for the element
	 */
	default void notifyFlow(String type, String name, double inFlow, double... outFlow) {
		notify(Level.Status,type,name,inFlow,outFlow);
	}

	/**
	 * Simulation error notification method.
	 * 
	 * To be used to raise an error if the 
	 * input flow of an element exceeds its maximum defined flow rate.
	 * 
	 * This is a commodity method that invokes the general purpose {@link #notify} method.
	 * 
	 * @param type class name of the element 
	 * @param name name of the element
	 * @param inFlow input flow for the element
	 * @param maxFlow the maximum flow for the element
	 */
	default void notifyFlowError(String type, String name, double inFlow, double maxFlow) {
		notify(Level.Error,type,name,inFlow,maxFlow);
	}

}
