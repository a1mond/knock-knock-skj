public class ServerRunner extends Thread {
    private final UDPServer server;

    public ServerRunner(UDPServer server) {
        this.server = server;
        start();
    }

    @Override
    public void run() {
        server.start();
    }
}
