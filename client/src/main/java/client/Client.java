package client;

import clientLogger.ClientLogger;
import listening.Request;
import listening.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private DatagramSocket socket;
    private InetAddress host;
    final int PORT = Integer.parseInt(System.getenv("PORT"));
    private static final Logger LOGGER = ClientLogger.getLogger();
    private final ResourceBundle RB = ResourceBundle.getBundle("client");

    public Client() {
        try {
            host = InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 });
            socket = new DatagramSocket();
            socket.setSoTimeout(7000);
            LOGGER.info(RB.getString("start"));
        } catch (SocketException e) {
            // can't create DSocket
            LOGGER.log(Level.WARNING, RB.getString("socketEx"), new NoSuchElementException());
        } catch (UnknownHostException e) {
            // can't find host
            LOGGER.log(Level.WARNING, RB.getString("unknHost"), new NoSuchElementException());
        }
    }

    public Optional<Response> recieve() {
        final int BUF_SIZE = 1024 * 1024; // 1MiB
        try {
            ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
            final byte[] array = buffer.array();
            DatagramPacket packet = new DatagramPacket(array, array.length);
            socket.receive(packet);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(array));
            return Optional.of((Response) ois.readObject());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, RB.getString("ignore"));
            throw new NoSuchElementException();
        } catch (ClassNotFoundException e) {
            LOGGER.warning(RB.getString("badCasting"));
            return Optional.empty();
        }
    }

    public void send(Request request) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            byte[] sendArray = baos.toByteArray();
            socket.send(new DatagramPacket(sendArray, sendArray.length, host, PORT));
        } catch (IOException e) {
            LOGGER.warning(RB.getString("cantSend"));
        }
    }
}
