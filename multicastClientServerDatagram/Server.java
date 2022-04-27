import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardSocketOptions;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {

        DatagramSocket sender = new DatagramSocket(new InetSocketAddress(0));
        NetworkInterface outgoingIf = NetworkInterface.getByName("wlp2s0");
        sender.setOption(StandardSocketOptions.IP_MULTICAST_IF, outgoingIf);

        // optionally configure multicast TTL; the TTL defines the scope of a
        // multicast datagram, for example, confining it to host local (0) or
        // link local (1) etc...
        int ttl = 30; // a number betwen 0 and 255
        sender.setOption(StandardSocketOptions.IP_MULTICAST_TTL, ttl);

        // send a packet to a multicast group
        byte[] msgBytes = "uma qualquer".getBytes();
        InetAddress mcastaddr = InetAddress.getByName("225.0.0.1");
        int port = 7373;
        InetSocketAddress dest = new InetSocketAddress(mcastaddr, port);
        while (true) {
            DatagramPacket hi = new DatagramPacket(msgBytes, msgBytes.length, dest);
            sender.send(hi);
            System.out.println(new String(msgBytes));
            Thread.sleep(5000);
        }

    }

}
