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
import java.util.ResourceBundle;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;
//  todo insert из SQL Shell обходит хеширование паролей

public class Server {

    private static final Logger LOGGER = ServerLogger.getLogger();
    private static final ResourceBundle RB = ResourceBundle.getBundle("server");
    private static final int BUF_SIZE = 32768;
    private static DatagramChannel channel;

    static {
        try {
            int PORT = Integer.parseInt(System.getenv("PORT"));
            channel = DatagramChannel.open(); // Открыли канал
            channel.bind(new InetSocketAddress(PORT)); // Привязали канал к порту
            channel.configureBlocking(false); // Неблокирующий режим
            LOGGER.info(RB.getString("start"));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, RB.getString("cantStart"), new RuntimeException());
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.SEVERE, RB.getString("badPort"));
            LOGGER.info(RB.getString("end"));
            System.exit(0);
        }
    }

    public Optional<Request> recieve() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE); // Буфер, в который пишется входной поток байтов
            SocketAddress clientAddress = channel.receive(buffer); // Адрес возврата
            if (clientAddress == null) {
                return Optional.empty();
            }
            LOGGER.info(RB.getString("newReqWithAddr") + " : " + clientAddress);

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array())); // Для
                                                                                                     // десереализации
            Request request = (Request) ois.readObject();
            request.setClientAddres(clientAddress);

            return Optional.of(request);
        } catch (IOException e) {
            LOGGER.warning(RB.getString("readingEx"));
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            LOGGER.warning(RB.getString("castingEx"));
            return Optional.empty();
        } catch (NullPointerException ex) {
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
                    LOGGER.info(RB.getString("newResWithAddr") + ": " + clientAdress);
                } catch (IOException e) {
                    LOGGER.warning(RB.getString("respEx"));
                }
            }
        }
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(new Action());
    }

}
