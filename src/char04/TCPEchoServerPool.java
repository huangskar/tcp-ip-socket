package char04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPEchoServerPool {
	private static final int serverPort = 13131;

	public static void main(String[] args) throws IOException {
		Logger logger = Logger.getLogger("practical");
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(serverPort);
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							Socket clientSocket = serverSocket.accept();
							System.out.println(Thread.currentThread().getName() + " handle clientSocket");
							EchoProtocol.handleEchoClient(clientSocket, logger);
						} catch (IOException e) {
							logger.log(Level.WARNING, "handle clientSocket failed", e);
						}
					}
				}
			});
			thread.start();
		}
	}
}
