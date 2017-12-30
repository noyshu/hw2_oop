package homework2;

import java.util.*;

/**
 * This class implements a testing driver for Simulator. The driver manages
 * Simulators for payment channels
 */
public class SimulatorTestDriver {

	private Map<String, Simulator<String, Transaction>> simulators;

	/**
	 * @modifies this
	 * @effects Constructs a new test driver.
	 */
	public SimulatorTestDriver() {
		// init simulators data structure
        simulators = new HashMap<>();
	}

	/**
	 * @requires simName != null
	 * @modifies this
	 * @effects Creates a new simulator named simName. The simulator's graph is
	 *          initially empty.
	 */
	public void createSimulator(String simName) {
	    Simulator simulator = new Simulator(simName);
	    // insert to map
	    simulators.put(simName, simulator);
	}

	/**
	 * @requires createSimulator(simName) 
     *           && channelName != null && channelName has
	 *           not been used in a previous addChannel()  or
	 *           addParticipant() call on this object
	 *           limit > 0
	 * @modifies simulator named simName
	 * @effects Creates a new Channel named by the String channelName, with a limit, and add it to
	 *          the simulator named simName.
	 */
	public void addChannel(String simName, String channelName, double limit) {
		Simulator simulator;

	    if (simName == null || !simulators.containsKey(simName) || 
	    		channelName == null || limit < 0){
			System.out.println("Illegal arguments");
			
			return;
		}

	    // extract appropriate simulator from map
		simulator = simulators.get(simName);

		Channel channel = new Channel(channelName, limit);
		try {
			simulator.addPipe(channelName, channel);
		}
		catch (Exception exception){
			System.out.println("Illegal arguments");
		}
	

	}

	/**
	 * @requires createSimulator(simName) && participantName != null 
	 *           && participantName has not been used in a previous addParticipant(), addChannel()
	 *           call on this object
	 *           fee > 0
	 * @modifies simulator named simName
	 * @effects Creates a new Participant named by the String participantName and add
	 *          it to the simulator named simName.
	 */
	public void addParticipant(String simName, String participantName, double fee) {
		Simulator simulator;

		if (simName == null || !simulators.containsKey(simName) ||
				participantName == null || fee < 0){
			System.out.println("Illegal arguments");
			return;
		}

		simulator = simulators.get(simName);

		Participant participant = new Participant(participantName, fee);
		try {
			simulator.addFilter(participantName, participant);
		}

		catch (Exception ex){
			System.out.println("Illegal arguments");
		}
	

	}

	/**
	 * @requires createSimulator(simName) && ((addPipe(parentName) &&
	 *           addFilter(childName)) || (addFilter(parentName) &&
	 *           addPipe(childName))) && edgeLabel != null && node named
	 *           parentName has no other outgoing edge labeled edgeLabel 
	 *           && node named childName has no other incoming edge labeled edgeLabel
	 * @modifies simulator named simName
	 * @effects Adds an edge from the node named parentName to the node named
	 *          childName in the simulator named simName. The new edge's label
	 *          is the String edgeLabel.
	 */
	public void addEdge(String simName, String parentName, String childName, String edgeLabel) {
		if (simName == null || !simulators.containsKey(simName) ||
				parentName == null || childName == null || edgeLabel == null){
			System.out.println("Illegal arguments");
			return;
		}

		Simulator simulator = simulators.get(simName);
		try {
			simulator.addEdge(parentName, childName, edgeLabel);
		}
		catch (Exception ex){
			System.out.println("Illegal arguments");
		}
		
	}

	/**
	 * @requires createSimulator(simName) && addChannel(channelName)
	 *           A transaction Transaction != null
	 * @modifies channel named channelName
	 * @effects pushes the Transaction into the channel named channelName in the
	 *          simulator named simName.
	 */
	public void sendTransaction(String simName, String channelName, Transaction tx) {
		if (simName == null || !simulators.containsKey(simName) ||
				channelName == null || tx == null) {
			System.out.println("Illegal arguments");
			return;
		}

        Simulator simulator = simulators.get(simName);

		try {
			simulator.sendTransaction(channelName, tx);
		}
		catch (IllegalArgumentException ex){
			System.out.println("Illegal arguments");
		}
	}
	
	
	/**
	 * @requires addChannel(channelName)
	 * @return a space-separated list of the Transaction values currently in the
	 *         channel named channelName in the simulator named simName.
	 */
	public String listContents(String simName, String channelName) {
		if (simName == null || !simulators.containsKey(simName) || channelName == null ) {
			System.out.println("Illegal arguments");
			return null;
		}

		Simulator simulator = simulators.get(simName);
		Channel channel;
		String textVal = "";

		try {
			channel = (Channel) simulator.getPipeObj(channelName);
			List<Transaction> txList = channel.getTransactionList();
			List<String> txListString = new ArrayList<String>();

			for (Transaction tx: txList) {
				txListString.add(String.valueOf(tx.getValue()));
			}

			textVal = String.join(" ", txListString);
		}
		catch (Exception ex){
			System.out.println("Illegal arguments");
		}
		

		return textVal;
	}

	/**
	 * @requires addParticipant(participantName)
	 * @return The sum of all  Transaction values stored in the storage of the participant participantName in the simulator simName
	 */
	public double getParticipantBalace(String simName, String participantName) {
		if (simName == null || !simulators.containsKey(simName) || participantName == null ) {
			System.out.println("Illegal arguments");
			return -1;
		}

		Participant participant;
		double balance = 0;
		Simulator simulator = simulators.get(simName);

		try {
			participant = (Participant) simulator.getFilterObj(participantName);
			balance = participant.getBalance();
		}

		catch (Exception ex){
			System.out.println("Illegal arguments");
		}

		return balance;
	}
	
	/**
	 * @requires createSimulator(simName)
	 * @modifies simulator named simName
	 * @effects runs simulator named simName for a single time slice.
	 */
	public void simulate(String simName) {
		if (simName == null || !simulators.containsKey(simName)) {
			System.out.println("Illegal arguments");
			return;
		}

		Simulator simulator = simulators.get(simName);
		try {
			simulator.simulate();
		}
		catch (IllegalArgumentException ex){
			System.out.println("Illegal arguments");
		}

	}

	/**
	 * Prints the all edges.
	 *
	 * @requires simName the sim name
	 * @effects Prints the all edges.
	 */
	public void printAllEdges(String simName) {
		if (simName == null || !simulators.containsKey(simName)) {
			System.out.println("Illegal arguments");
			return;
		}

		Simulator simulator = simulators.get(simName);
		List<String> edgeListCopy = new ArrayList<>(simulator.getEdgeList());
		String edgesList = String.join(" ", edgeListCopy);

		System.out.println("Edges names are:\n" + edgesList);
	}

}
