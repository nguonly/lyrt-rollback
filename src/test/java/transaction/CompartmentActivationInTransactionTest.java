package transaction;

import net.role4j.Compartment;
import net.role4j.Registry;
import net.role4j.trans.ConsistencyBlock;
import org.junit.Test;
import rolefeature.BaseTest;

/**
 * Created by nguonly on 10/18/16.
 */
public class CompartmentActivationInTransactionTest extends BaseTest {

    @Test(expected = RuntimeException.class)
    public void testCompartmentActivatedInTransactionThrowsError() throws Throwable{
        Registry reg = Registry.getRegistry();

        Compartment comp = reg.newCompartment(Compartment.class);
        comp.activate();

        try(ConsistencyBlock tx = new ConsistencyBlock()){
            comp.activate();
        }

    }

    @Test(expected = RuntimeException.class)
    public void testCompartmentDeactivatedInTransactionThrowsError() throws Throwable{
        Registry reg = Registry.getRegistry();

        Compartment comp = reg.newCompartment(Compartment.class);
        comp.activate();

        try(ConsistencyBlock tx = new ConsistencyBlock()){
            comp.deactivate();
        }
    }
}
