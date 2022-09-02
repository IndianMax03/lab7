package server;

import listening.Request;
import listening.Response;
import serverLogger.ServerLogger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;
//  todo insert из SQL Shell обходит хеширование паролей

public class Server {

    private static final Logger LOGGER = ServerLogger.getLogger();
    private static final int PORT = 9000;
    private static final int BUF_SIZE = 32768;
    private static DatagramChannel channel;

    static {
        try {
            channel = DatagramChannel.open(); // Открыли канал
            channel.bind(new InetSocketAddress(PORT)); // Привязали канал к порту
            channel.configureBlocking(false); // Неблокирующий режим
            LOGGER.info("Сервер начал свою работу");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Сервер не может быть запущен.", new RuntimeException());
        }
    }

    public Optional<Request> recieve() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE); // Буфер, в который пишется входной поток байтов
            SocketAddress clientAddress = channel.receive(buffer); // Адрес возврата
            if (clientAddress == null) {
                return Optional.empty();
            }
            LOGGER.info("Получен новый запрос от клиента с адресом: " + clientAddress);

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array())); // Для
                                                                                                     // десереализации
            Request request = (Request) ois.readObject();
            request.setClientAddres(clientAddress);

            return Optional.of(request);
        } catch (IOException e) {
            LOGGER.warning("Ошибка при чтении данных.");
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            LOGGER.warning("Невозможно восстановить класс запроса. Ошибка кастинга при десериализации.");
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
                    LOGGER.info("Ответ отправлен клиенту с адресом: " + clientAdress);
                } catch (IOException e) {
                    LOGGER.warning("Ошибка отправки данных клиенту.");
                }
            }
        }
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(new Action());
    }

}
