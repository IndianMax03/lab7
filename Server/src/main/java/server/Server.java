package server;

import listening.Request;
import listening.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;
//  todo insert из SQL Shell обходит хеширование паролей

public class Server {

    private static final Logger logger = Logger.getAnonymousLogger();

    private static final int PORT = 9000;
    private static final int BUF_SIZE = 32768;
    private static DatagramChannel channel;

    static {
        try {
            channel = DatagramChannel.open(); // Открыли канал
            channel.bind(new InetSocketAddress(PORT)); // Привязали канал к порту
            channel.configureBlocking(false); // Неблокирующий режим
            logger.info("Сервер начал свою работу");
        } catch (IOException ex) {
            System.out.println("Сервер не может быть запущен.");
        }
    }

    public Optional<Request> recieve() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE); // Буфер, в который пишется входной поток байтов
            SocketAddress clientAddress = channel.receive(buffer); // Адрес возврата
            if (clientAddress == null) {
                return Optional.empty();
            }
            logger.info("Получен новый запрос от клиента с адресом: " + clientAddress);

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array())); // Для
                                                                                                     // десереализации
            Request request = (Request) ois.readObject();
            request.setClientAddres(clientAddress);

            return Optional.of(request);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении данных.");
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            System.out.println("Невозможно восстановить класс запроса. Ошибка кастинга при десериализации.");
            return Optional.empty();
        }
    }

    public void send(Response response, SocketAddress clientAdress) {
        class Action extends RecursiveAction {
            private static final long serialVersionUID = -4212267061849350439L;

            @Override
            protected void compute() {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(response);
                    channel.send(ByteBuffer.wrap(baos.toByteArray()), clientAdress);
                    logger.info("Ответ отправлен клиенту с адресом: " + clientAdress);
                } catch (IOException e) {
                    System.out.println("Ошибка отправки данных клиенту.");
                }
            }
        }
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(new Action());
    }

}
