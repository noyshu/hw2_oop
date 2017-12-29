package homework2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent an abstract class for pipes
 *  
 **/
public abstract class Pipe<T, O> implements Simulatable<T>{


    protected T label;
    protected double limit;
    protected List<O> operationObjs;
    private boolean isLimited;

    
    
    /**
     * @require pipeLabel != null && limit >= 0
     * @effects init Pipe fields
     */
    public Pipe(T pipeLabel, boolean isLimited_, double limit_) {
        if(pipeLabel == null || limit_ < 0)
            return;

        operationObjs = new ArrayList<O>();
        label = pipeLabel;
        this.limit = limit_;
        this.isLimited = isLimited_;
    }

    

    /**
     * @return return this.label
     */
    public T getLabel() {
        return label;
    }


    /**
     * @return return this.isLimited
     */
    public boolean getIsLimited() {
        return isLimited;
    }

    
    public abstract boolean passOperationObj(O obj);

}
