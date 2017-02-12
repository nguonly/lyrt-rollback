# LyRT with Rollback and Recovery Mechanism

[LyRT](http://dl.acm.org/citation.cfm?id=2892664.2892687) is a role-based run-time engine reifying CROM (Compartment, Role and Object Model) 
proposed by [Kühn et al](http://dl.acm.org/citation.cfm?id=2814255). LyRT is designed to support run-time variability through 
dynamic role-playing relations enabling both anticipated and unanticipated adaptation.

In this repository, we demonstrate an extension to LyRT which is a rollback mechanism to 
recover from software bugs. The detail mechanism is illustrated in a submitting paper. 
 Therefore, we only explain the motivating example implementation 
 that has not been well described in the paper due to space limit. 
 
The additional implementations added to LyRT are `AdaptationTransaction` and 
`ControlUnit` class localed in `net.role4j.rollback` package. The bug sensor is 
implemented as part of user applications to customize the recovery process. The sample 
implementation of Bug Sensors for the example is depicted below.

```java
public class BugSensor implements Thread.UncaughtExceptionHandler {
    private Socket client;
    
    public BugSensor(Socket client){
        this.client = client;
    }
    
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(t + " Throwable: " + e);
        System.out.println("Thread status: " + t.getState());
        
        //Registry component in LyRT
        Registry reg = Registry.getRegistry();
        ICompartment comp = reg.getActiveCompartments().get(t.getId());
        
        //Rollback
        ControlUnit.rollback(comp.hashCode());
        
        //Logging
        System.out.println("Rollback >>>");
        AppState.appendMessage(t + " throws " + e);
        AppState.appendMessage(t.getName() + ">>>>> Rollback >>>> ");
        DumpHelper.dumpRelations();
        
        //Restart the client thread
        ServiceHandler handler = new ServiceHandler(client);
        handler.start();
    }
}
```

## Example

The full implementation
 of the example can be found in `demo.rollback` package under the test project. The example contains
 client and server side. In this case, we focus only the server side and thus dynamic adaption is applied
 to server run-time only.
 
#### Definition of Chat Server Example
`Channel` is a core object implementing an `IPlayer` interface, 
`Compression` is a role implementing an `IRole` interface. Both of them implements the
`IChannel` interface by overriding `prepareChannelForSending` and `prepareChannelForReceiving`.

```java
public interface IChannel {
    String prepareChannelForSending(String data);
    String prepareChannelForReceiving(String data);
}
```

```java
public class Channel implements IChannel, IPlayer{
    public String prepareChannelForSending(String data){
        return data;
    }
    
    public String prepareChannelForReceiving(String data){ return data;}
}
```

```java
public class Compression implements IChannel,IRole {
    public String prepareChannelForReceiving(String data){
        //TODO: to be implemented
        return data;
    }
    
    public String prepareChannelForSending(String data){
        String fMsg = getPlayer(IChannel.class).prepareChannelForSending(data);
        return "<C>" + fMsg + "<C>";
    }
}
```

`Encryption` is another role supposed to be added unanticipatedly and it contains a bug, divided-by-zero.

```java
public class Encryption implements IChannel, IRole{
    public String prepareChannelForReceiving(String data){
        //TODO: to be implemented
        return data;
    }
    
    public String prepareChannelForSending(String data){
        String fMsg = getPlayer(IChannel.class).prepareChannelForSending(data);
        int i=1/0; //inject error
        return "<E>" + fMsg + "<E>";
    }
}
```

#### Main Program
The code below is a sample how to construct the binding process with adaptation transaction. 

```java
public class Main{
    public static void main(String... args){
        Registry reg = Registry.getRegistry(); //Mediator
        Compartment ctxLZ = reg.newCompartment();
        Channel channel = reg.newCore(Channel.class);
        
        ctxLZ.activate();
        
        try(AdaptationTransaction at1 = new AdaptationTransaction()){
            channel.bind(Compression.class);
        }
        
        // execute the rest of the program
        
        try(AdaptationTransaction at2 = new AdaptationTransaction()){
            channel.bind(Encryption.class); //that contains an error
        }
        
        //execute with divide-by-zero located in Encryption class
        
        ctxLZ.deactivate();
    }
}
```




