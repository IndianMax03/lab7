package client;

import listening.Request;
import listening.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private DatagramSocket socket;
    private InetAddress host;
    private static final Logger logger = Logger.getAnonymousLogger();

    public Client() {
        try {
            host = InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 });
            socket = new DatagramSocket();
            logger.info("Клиентский модуль начал работу.");
        } catch (SocketException e) {
            logger.log(Level.WARNING, "Широковещательное слушание не может быть начато.");
            System.exit(-1); // cause can't create DSocket
        } catch (UnknownHostException e) {
            logger.log(Level.WARNING, "Хост не найден. Обратитесь к разработчику программы.");
            System.exit(1); // cause can't find host
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
            logger.warning("Сервер не отвечает.");
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            logger.warning("Нарушен кастинг к классу читаемого объекта.");
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
            logger.warning("Запрос не может быть записан и доставлен.");
        }
    }
}
