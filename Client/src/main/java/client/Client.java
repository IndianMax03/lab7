package client;

import clientLogger.ClientLogger;
import listening.Request;
import listening.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private DatagramSocket socket;
    private InetAddress host;
    private static final Logger LOGGER = ClientLogger.getLogger();

    public Client() {
        try {
            host = InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 });
            socket = new DatagramSocket();
            LOGGER.info("Клиентский модуль начал работу.");
        } catch (SocketException e) {
            // can't create DSocket
            LOGGER.log(Level.WARNING, "Широковещательное слушание не может быть начато.", new NoSuchElementException());
        } catch (UnknownHostException e) {
            // can't find host
            LOGGER.log(Level.WARNING, "Хост не найден. Обратитесь к разработчику программы.",
                    new NoSuchElementException());
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
            LOGGER.warning("Сервер не отвечает.");
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            LOGGER.warning("Нарушен кастинг к классу читаемого объекта.");
            return Optional.empty();
        }
    }

    public void send(Request request) {
        final int PORT = 9000; // сделать переменной окружения
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            byte[] sendArray = baos.toByteArray();
            socket.send(new DatagramPacket(sendArray, sendArray.length, host, PORT));
        } catch (IOException e) {
            LOGGER.warning("Запрос не может быть записан и доставлен.");
        }
    }
}
