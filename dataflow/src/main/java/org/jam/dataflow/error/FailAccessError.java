package org.jam.dataflow.error;

/**
 * Created by James on 16/4/19.
 */
public class FailAccessError extends DataflowError{

    public FailAccessError(Exception e){
        super(e);
    }
}
