import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final int port;

    public TCPServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        System.out.println("[TCP-SERVER][STARTED ON PORT: " + port + "]");
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String message = in.readLine();
        System.out.println("[TCP-SERVER][REQUEST: " + message + "]");
        out.println("no prob buddy :)");
        System.out.println("[TCP-SERVER][RESPONSE: 'no prob buddy :)']");

        stop();
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
