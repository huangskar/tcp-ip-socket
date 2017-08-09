package char04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class TCPEchoServerThread {
	private final static int serverPort = 13131;

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(serverPort);
		Logger logger = Logger.getLogger("practical");
		while (true) {
			Socket socket = serverSocket.accept();
			Thread thread = new Thread(new EchoProtocol(socket, logger));
			thread.start();
			logger.info("Create and started Thread :" + thread.getName());
		}
	}
}
