public class Main {
    private static final int[] port_arr = {1355, 1390, 1399, 1358};

    private static final int port_from = 1350, port_to = 1400;

    public static void main(String[] args) {
        for (int i : port_arr)
            new ServerRunner(new UDPServer(i));

        for (int port = port_from; port < port_to; port++)
            new Client().start(port);
    }
}
