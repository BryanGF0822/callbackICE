public class Server
{
    public static void main(String[] args)
    {
        
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.server"))
        {
            
            // Server server
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Server");
            
            MyServerI instance = new MyServerI();
            com.zeroc.Ice.ObjectPrx prx = adapter.add(instance, com.zeroc.Ice.Util.stringToIdentity("server"));
            adapter.activate();
            
            // Server proxy
            Demo.MyServerPrx serverPrx = Demo.MyServerPrx.uncheckedCast(prx);
            instance.setPrxoy(serverPrx);

            System.out.println("Running...");

            communicator.waitForShutdown();            
        }
    }
}