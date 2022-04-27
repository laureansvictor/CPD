import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardSocketOptions;
import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) throws IOException {

        // Constructor to create a datagram socket
        DatagramSocket socket = new DatagramSocket(null);
        socket.setReuseAddress(true); // set reuse address before binding
        socket.bind(new InetSocketAddress(7373)); // bind

        InetAddress mcastaddr = InetAddress.getByName("225.0.0.1");
        InetSocketAddress group = new InetSocketAddress(mcastaddr, 0);
        NetworkInterface netIf = NetworkInterface.getByName("wlp2s0");
        socket.joinGroup(group, netIf);
        Integer counter = 0;
        while(counter < 10){
            byte[] msgBytes = new byte[1024]; // up to 1024 bytes
            DatagramPacket packet = new DatagramPacket(msgBytes, msgBytes.length);
            socket.receive(packet);
            System.out.println(new String(msgBytes, StandardCharsets.UTF_8));
            counter++;
        }
        // eventually leave group
        socket.leaveGroup(group, netIf);

    }

}