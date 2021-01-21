import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    private DatagramSocket socket;

    private final int port;

    public UDPServer(int port) {
        this.port = port;
    }

    private static class ServerThread extends Thread {
        private final DatagramPacket packet;
        private final DatagramSocket socket;
        private final String msg;

        public ServerThread(DatagramPacket packet, DatagramSocket socket, String msg) {
            super();
            this.msg = msg;
            this.packet = packet;
            this.socket = socket;
        }


        public void run() {
            try {
                boolean runTCP = false;

                int tcpPort = 1000 + (int)(10000 * Math.random());
                byte[] respBuff;
                if (msg.equals("OK")) {
                    respBuff = msg.getBytes();
                } else {
                    respBuff = ("" + tcpPort).getBytes();
                    runTCP = true;
                }
                int clientPort = packet.getPort();
                InetAddress clientAddress = packet.getAddress();

                DatagramPacket resp = new DatagramPacket(respBuff, respBuff.length, clientAddress, clientPort);
                System.out.println("[UDP-SERVER][SENDING RESPONSE: " + new String(respBuff) + "]");
                socket.send(resp);

                if (runTCP) new TCPServer(tcpPort).start();

            } catch (IOException ignore) {}
        }
    }

    public void start() {
        try {
            socket = new DatagramSocket(port);
        }
        catch (IOException e) {
            System.out.println("[UDP-SERVER][FAILED TO START ON PORT: " + port + "]");
            e.printStackTrace();
        }
        System.out.println("[UDP-SERVER][STARTED ON PORT: " + port + "]");

        while (true) {
            try {
                byte[] buff = new byte[1000];
                final DatagramPacket datagram = new DatagramPacket(buff, buff.length);

                socket.receive(datagram);
                String received = new String(
                        datagram.getData(), 0, datagram.getLength());
                System.out.println("[UDP-SERVER][REQUEST: " + received + "]");
                if (received.equals("LET ME IN!")) {
                    new ServerThread(datagram, socket, "OK").start();
                } else if (received.equals("gimme a port")) {
                    new ServerThread(datagram, socket, "").start();
                }
            }
            catch (IOException e) {
                System.out.println("[UDP-SERVER][FAILED: NO REQUEST]");
            }
        }
    }
}
