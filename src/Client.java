import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    //UDP-side
    private DatagramSocket socket;
    private InetAddress address;

    //TCP-side
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private final String msg = "LET ME IN!";

    public Client() {
        try {
            address = InetAddress.getByName("localhost");
            socket = new DatagramSocket();
            socket.setSoTimeout(500);
        } catch (IOException ignore) {}
    }

    public void start(int port) {
        try {
            String response = sendUDP("LET ME IN!", port);
            if (response.equals("OK")) {
                response = sendUDP("gimme a port", port);
                sendTCP(Integer.parseInt(response));
            }
        } catch (IOException ignore) {}

        socket.close();
    }
    public void sendTCP(int port) throws IOException {
        clientSocket = new Socket(address.getHostAddress(), port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("    [CLIENT][CONNECTED TO " + address.getHostAddress() + ":" + port + "]");
        String msg = "wow, i actually connected";
        out.println(msg);
        System.out.println("    [CLIENT][SENDING '" + msg + "']");
        String response = in.readLine();
        System.out.println("    [CLIENT][RESPONSE: '" + response + "']");
        System.exit(3);
    }
    public String sendUDP(String message, int port) throws IOException {
        System.out.println("    [CLIENT][SENDING MSG: '" + message + "'][" + address.getHostAddress() + ":" + port + "]");
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
        DatagramPacket received = new DatagramPacket(new byte[500], 500);
        socket.receive(received);

        String response = new String(received.getData(), 0, received.getLength());
        System.out.println("    [CLIENT][RESPONSE: " + response + "]");
        return response;
    }
}