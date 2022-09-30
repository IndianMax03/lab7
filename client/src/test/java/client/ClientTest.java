package client;

import listening.Request;
import listening.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

public class ClientTest {

    private final Client client = new Client();
    private final Response response = new Response("answer", false);
    final int PORT = Integer.parseInt(System.getenv("PORT"));
    private static final int BUF_SIZE = 32768;
    private DatagramChannel channel;

    @Before
    public void before() throws IOException {
        // server listening opened
        channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(PORT));
        channel.configureBlocking(false);
    }

    @After
    public void after() throws IOException {
        channel.close();
    }

    @Test(expected = NoSuchElementException.class)
    public void recieveIfNotSended() {
        client.recieve(); // <--
    }

    @Test
    public void recieveIfSended() {

        client.send(new Request("ignore"));

        // server side
        try {
            // receive request
            ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
            SocketAddress clientAddress = channel.receive(buffer); // got client adress

            // send to client
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            channel.send(ByteBuffer.wrap(baos.toByteArray()), clientAddress);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Optional<Response> receivedResponse = client.recieve(); // <--
        Response result = receivedResponse.get();

        assertEquals(result.getMessage(), response.getMessage());
    }
}