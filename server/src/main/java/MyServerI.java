
import java.util.ArrayList;

import com.zeroc.Ice.Current;

import Demo.MyClientPrx;
import Demo.MyServer;
import Demo.MyServerPrx;

public class MyServerI implements MyServer{

    public static final String BROADCAST = "BC ";

    private ArrayList<MyClientPrx> observers;

    private String state;

    private MyServerPrx proxy;

    public MyServerI(){

        this.observers = new ArrayList<MyClientPrx>();
        this.proxy = null;
        this.state = null;

    }

    private void notifyObservers(){
        
        for (MyClientPrx proxy : this.observers){

            try {
                proxy.update(this.proxy);
                System.out.println("proxy updated");
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println(e.getStackTrace());
            }

        }

    }   

    @Override
    public void attach(MyClientPrx proxy, Current current) {
        
        if (!observers.contains(proxy)){
            observers.add(proxy);
            System.out.println("Observer Attached");
        }

    }

    @Override
    public void detach(MyClientPrx proxy, Current current) {
    
        if (observers.remove(proxy)){
            System.out.println("Observer detached");
        }
        
    } 

    private void msgHandling(MyClientPrx proxy, String msg){

        if (msg.startsWith(BROADCAST)){
            this.state = msg;
            notifyObservers();
        }else{
            proxy.msg(msg);
        }

    }   

    @Override
    public void msg(MyClientPrx proxy, String msg, Current current) {
        
        Thread t = new Thread(){
            public void run(){
                msgHandling(proxy, msg);
            }
        };
        t.start();
    }

    @Override
    public String getState(Current current) {
        return this.state;
    }
    
    public void setPrxoy(MyServerPrx proxy){
        this.proxy = proxy;
    }

}
