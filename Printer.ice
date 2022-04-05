module Demo
{
    
    interface MyServer;

    // Observer
    interface MyClient{
        void update(MyServer* proxy);
        void msg(string msg);
    }

    // Subject
    interface MyServer {
        void attach(MyClient* proxy);
        void detach(MyClient* proxy);
        string getState();
        void msg(MyClient* proxy, string msg);
    }
}