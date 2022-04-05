import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.zeroc.IceInternal.Ex;

public class Client
{
    
    public static final String EXIT = "exit";
    
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.client",extraArgs))
        {
             
            // Client Server
            
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Client");
            adapter.add(new MyClientI(), com.zeroc.Ice.Util.stringToIdentity("client"));
            adapter.activate();
            

            // Client (Self) Proxy
            
            Demo.MyClientPrx clientProx = Demo.MyClientPrx.uncheckedCast(
                adapter.createProxy(com.zeroc.Ice.Util.stringToIdentity("client"))
            );
            if (clientProx == null){
                throw new Error("Invalid Proxy");
            }
            
            // Server proxy
            Demo.MyServerPrx serverProxy = Demo.MyServerPrx.checkedCast(
                communicator.propertyToProxy("Server.Proxy")).ice_twoway().ice_secure(false);

            if(serverProxy == null)
            {
                throw new Error("Invalid proxy");
            }

            // Logic
            
            String hostname = communicator.getProperties().getProperty("Ice.Default.Host");

            // Step 1: Attach to subject

            serverProxy.attach(clientProx);

            // Step 2: Messages

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while(true){
                
                try {
                    System.out.println("Digite msg");
                    String line = br.readLine();

                    if (line.equalsIgnoreCase(EXIT)){
                        break;
                    }
                        
                    serverProxy.msg(clientProx, line);
                    
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            serverProxy.detach(clientProx);
        }
    }
}