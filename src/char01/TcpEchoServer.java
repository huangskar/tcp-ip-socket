package char01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TcpEchoServer {
	private static int buffer = 64;

	public static void main(String[] args) throws NumberFormatException, IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException();
		}
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
		int temp = 0;
		byte[] data = new byte[buffer];
		while (true) {
			Socket socket = serverSocket.accept();
			SocketAddress socketAddress = socket.getRemoteSocketAddress();
			System.out.println("handle client at " + socketAddress);
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			while ((temp = in.read(data)) != -1) {
				out.write(data, 0, temp);
			}
			socket.close();
		}
		
	}
}
