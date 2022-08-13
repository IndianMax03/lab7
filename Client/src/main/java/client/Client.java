package client;

import listening.Request;
import listening.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Client {

	private final byte[] ip = new byte[]{127, 0, 0, 1};
	private static final int PORT = 9000;
	private static final int BUF_SIZE = 32768;
	private static DatagramSocket socket;
	private static DatagramPacket packet;
	private static InetAddress host;

	public Client(){
		try{
			host = InetAddress.getByAddress(ip);
			socket = new DatagramSocket();
			System.out.println("Клиентский модуль начал работу.");
		} catch (SocketException e) {
			System.out.println("Ошибка создания сокета. Широковещательное слушание не может быть начато.");
			System.exit(-1);
		}
		catch (UnknownHostException e) {
			System.out.println("Ошибка подключения: хост не найден. Обратитесь к разработчику программы.");
			System.exit(-1);
		}
	}
//  todo потоки
	public Response recieve(){
		try {
			ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
			packet = new DatagramPacket(buffer.array(), buffer.array().length);
			socket.receive(packet);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
			Response response = (Response) ois.readObject();
			return response;
		} catch (IOException e) {
			System.out.println("Ошибка чтения. Сервер не отвечает.");;
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("Ошибка десериализации. Нарушен кастинг к классу читаемого объекта.");
			return null;
		}
	}
//todo потоки
	public void send(Request request){
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(request);
			socket.send(new DatagramPacket(baos.toByteArray(), baos.toByteArray().length, InetAddress.getByAddress(ip), PORT));
		} catch (IOException e) {
			System.out.println("Ошибка отправки. Запрос не может быть записан и доставлен.");
		}
	}
}
