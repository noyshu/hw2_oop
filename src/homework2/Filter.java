package homework2;

import java.util.*;

/**
 * This class represents an abstract class of filters
**/
public abstract class Filter<T, O> implements Simulatable<T>{


	// TODO - is label neccessary
	protected List<O> storageBuffer;
    private T label;

    
    /**
     * @require filterLabel != null
     * @effects init Filter fields
     */
    public Filter(T filterLabel) {
        if(filterLabel == null)
            return;
        this.label = filterLabel;
        storageBuffer = new ArrayList<O>();

    }

    /**
     * @return return this.label
     */
    public T getLabel() {
        return label;
    }

}

