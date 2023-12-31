package Tut8JavaMulticastSockets.MulticastingthroughDatagramChannels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.lang.Thread;
public class MulticastPublisher {
    private static final String MULTICAST_INTERFACE = "en0";
    private static final int MULTICAST_PORT = 4567;
    private static final String MULTICAST_IP = "230.0.0.0";
    //int n=1;
    public void sendMessage(String ip, String iface, int port,String message) throws IOException {
        DatagramChannel datagramChannel=DatagramChannel.open();
        datagramChannel.bind(null);
        NetworkInterface networkInterface=NetworkInterface.getByName(iface);
        datagramChannel.setOption(StandardSocketOptions.IP_MULTICAST_IF,networkInterface);
        ByteBuffer byteBuffer=ByteBuffer.wrap(message.getBytes());
        InetSocketAddress inetSocketAddress=new InetSocketAddress(ip,port);
        datagramChannel.send(byteBuffer,inetSocketAddress);
    }
    public static void main(String[] args) throws IOException {
        MulticastPublisher mp=new MulticastPublisher();
        int n = 1;
        while(true) {
            String s = "Hi there! This is the message number ";

            s = s + Integer.toString(n) + " from publisher!";
            System.out.println(s);
            mp.sendMessage(MULTICAST_IP,MULTICAST_INTERFACE,MULTICAST_PORT,s);
            n++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

