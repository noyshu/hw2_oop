package homework2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a participant. participant hold transaction which destinated for him and passes
 * to his children channel other transaction after it takes a fee from their value.
 * each participant contain label, the fee amount, its current balance
 * and list of its transactions.
 * */

public class Participant extends Filter<String, Transaction> {

    private double balance;
    private double fee;

    
    
    /**
     * 
     * @modifies this
     * @require fee > 0 && label != null
     * @effects create new participant
     */
    public Participant(String label, double fee){
        super(label);

        // fee must be positive
        if(fee < 0)
        {
            return;
        }
        this.balance = 0;
        this.fee = fee;
    }

    
    /**
     * @return return participant balance
     */
    public double getBalance() {
    	return balance;
    }
    
    /**
     * @return return storage buffer
     */
    public List<Transaction> getStorageBuff() {
        List<Transaction> bufferList = new ArrayList<>(this.storageBuffer);
        return bufferList;
    }

    /**
     * @modifies this
     * @requires graph !=null
     * @effects if there is transaction with different destination
     *  take fee from it add pass it to the next channel
     */
    public void simulate(BipartiteGraph<String> givenGraph){
    	if(givenGraph == null)
    	{
    		return;
    	}
    	
        List<Object> listChildChannels;

        try {
            listChildChannels = givenGraph.getChildObj(this.getLabel());
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("Illegal arguments");
        }

        if (listChildChannels == null)
        {
            return;
        }

        //one channel child in the list
        int i = 0;
        Channel childChannel = (Channel)listChildChannels.get(0);
        // TODO - not >= ?
        while (this.storageBuffer.size() > i){
            
        	Transaction curTx = this.storageBuffer.get(i);
            // if dest is this, continue
        	if(curTx.getDest().equals(this.getLabel())){
                i++;
                continue;
            }

        	// in case we cant take the whole fee, we dont pass it
            if(this.fee > curTx.getValue()){
                i++;
                continue;
            }

            double val = curTx.getValue()- this.fee;
            String dest = curTx.getDest();
            Transaction newTx = new Transaction(dest, val);
            // in case transaction succeeded
            if(childChannel.passOperationObj(newTx)){
                balance -=curTx.getValue();
                this.storageBuffer.remove(curTx);
            } else {
                i++;
            }
        }
    }
    /**
     * @modifies this
     * @effects adds balance
     */
    public void addBalance(double balance_){
        this.balance += balance_;
    }

    /**
     * @modifies this
     * @effects adds transactions to storage buffer
     */
    public void addToBuffer(List<Transaction> newList){
        storageBuffer.addAll(newList);
    }

}
