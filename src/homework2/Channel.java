package homework2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implement a channel. channel is used to pass transactions
 * between different participants.
 * channel has a sum value which is the sum of its transactions values.
 * channel cannot hold a sum more than its limit (defined at it's ancestor)
 * 
 * */

public class Channel extends Pipe<String, Transaction> {

	
    private double sum;

    /**
     * @requires limit > 0 &label != null & 
     * @effects construct new channel
     * 
     */
    public Channel(String label, double limit){
        super(label, true, limit);
        this.sum = 0;
    }

    /**
     * @modifies this
     * @effects running over all the transaction list and try to
     * transfer them to the next participant
     */
    public void simulate(BipartiteGraph<String> graph){
    	
    	List<Object> childrenParticipant;
    	try {
    		childrenParticipant = graph.getChildObj(this.label);
    	}
    	catch (Exception exception) {
    		throw new IllegalArgumentException("Illegal arguments");
    	}
    	
    	// in case there are no children
    	if (childrenParticipant == null){
    		
    		// TODO - is neccassary?
//            sum = 0;
//            operationObjs.clear();
    		return;
    	}
    	
    	//one child in the list
    	Participant childParticipant = (Participant)childrenParticipant.get(0);
    	childParticipant.addToBuffer(this.operationObjs);
    	childParticipant.addBalance(this.sum);
    	// after moving forward, clear the list
    	this.operationObjs.clear();
    	this.sum = 0;
    }
    
    /**
     * @requires transaction != null
     * @modifies this
     * @effects if the total value is lower than limit, adds the transaction to the list, otherwise do nothing
     * @return true if added to list false otherwise
     */
    public boolean passOperationObj(Transaction trans){

    	// in case current channel will pass its sum with the given trans
        if (this.sum + trans.getValue() > this.limit)
            return false;

        // otherwise, add cur trans to channel transaction list
        this.operationObjs.add(trans);
        this.sum += trans.getValue();
        return true;
    }

    /**
     * @return list of the transaction
     * @effect none
     */
    public List<Transaction> getTransactionList(){
    	// 
        List<Transaction> transactionList = new ArrayList<>(operationObjs);
        return transactionList;
    }

}
