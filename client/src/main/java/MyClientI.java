
import com.zeroc.Ice.Current;

import Demo.MyClient;
import Demo.MyServerPrx;

public class MyClientI implements MyClient {

    @Override
    public void update(MyServerPrx proxy, Current current) {
        String state = proxy.getState();
        System.out.println(state);
    }

    @Override
    public void msg(String msg, Current current) {
        System.out.println(msg);
    }
    
}
