package server;

import listening.Request;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.util.Optional;

import static org.junit.Assert.*;

public class ServerTest {

    // client's host
    private final InetAddress host = InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 });

    private final Server server = new Server();
    final int PORT = 9000;
    private Request request;

    public ServerTest() throws UnknownHostException {
    }

    @Before
    public void before() {
        request = new Request("show");
        request.setLogin("test");
        request.setPassword("test");
    }

    @Test
    public void recieveIfNotSended() {
        Optional<Request> result = server.recieve(); // <--
        assertEquals(result, Optional.empty());
    }

    @Test
    public void recieveIfSended() {
        // sending from client
        try {
            DatagramSocket socket = new DatagramSocket();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            byte[] sendArray = baos.toByteArray();
            socket.send(new DatagramPacket(sendArray, sendArray.length, host, PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // receiving from server
        Optional<Request> receivedOptional = server.recieve(); // <--
        Request receivedRequest = receivedOptional.get();
        boolean result = receivedRequest.getCommandName().equals(request.getCommandName()) // same cmdNames
                && receivedRequest.getLogin().equals(request.getLogin()) // same logins
                && receivedRequest.getPassword().equals(request.getPassword()) // same passwords
                && ((InetSocketAddress) receivedRequest.getClientAddres()).getAddress().equals(host); // same
                                                                                                      // InetAdresses
        assertTrue(result); // means that server received valid request with opportunity to send response back
    }

    @Test(expected = BindException.class)
    public void doubleOpening() throws IOException {
        DatagramChannel secondChannel = DatagramChannel.open();
        secondChannel.bind(new InetSocketAddress(PORT));
    }

}