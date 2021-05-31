public class Main {

    public static void main(String[] args) throws InvalidArgumentsException {
        int port_server;
        int port_from;
        int port_to;
        try {
            port_server = Integer.parseInt(args[0]);
            port_from = Integer.parseInt(args[1]);
            port_to = Integer.parseInt(args[2]);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidArgumentsException("Invalid or no arguments have been provided, the format should be\n" +
                    "\"*server_port*, *knock_port_from*, *knock_port_to*\"");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid argument have been provided, consider using only numbers");
        }

        new ServerRunner(new UDPServer(port_server));

        for (int port = port_from; port < port_to; port++)
            new Client().start(port);
        System.out.println("Knocking is finished");
        System.exit(0);
    }
}
