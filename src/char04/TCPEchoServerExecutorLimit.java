package char04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TCPEchoServerExecutorLimit {
	private static final int serverPort = 13131;

	public static void main(String[] args) throws IOException {
		ExecutorService exec = Executors.newCachedThreadPool();
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(serverPort);
		Logger logger = Logger.getLogger("practical");
		while (true) {
			Socket clientSocket = serverSocket.accept();
			exec.execute(new TimeLimitEchoProtocol(clientSocket, logger));
			logger.info(" handle clientSocket!");
		}
	}
}
