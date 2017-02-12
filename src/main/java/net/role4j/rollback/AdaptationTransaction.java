package net.role4j.rollback;

/**
 * Created by nguonly on 1/12/17.
 */
public class AdaptationTransaction implements AutoCloseable {
    public AdaptationTransaction(){
        enter();
    }

    @Override
    public void close() {
        leave();
    }

    private void enter(){
        ControlUnit.checkpoint();
    }

    private void leave(){

    }
}
