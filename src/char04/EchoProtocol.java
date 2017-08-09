package char04;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoProtocol implements Runnable {
	private static final int BUFSIZE = 32;
	private Socket clientSocket;
	private Logger logger;

	public EchoProtocol(Socket clientSocket, Logger logger) {
		super();
		this.clientSocket = clientSocket;
		this.logger = logger;
	}

	public static void handleEchoClient(Socket clientSocket, Logger logger) {
		try {
			InputStream in = clientSocket.getInputStream();
			OutputStream out = clientSocket.getOutputStream();
			int recive = 0;
			byte[] reciveBuff = new byte[BUFSIZE];
			int reciveTotal = 0;

			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			while ((recive = in.read(reciveBuff)) != -1) {
				out.write(reciveBuff, 0, recive);
				reciveTotal += recive;
			}
			System.out.println("recive total bytes: " + reciveTotal);
		} catch (IOException e) {
			e.printStackTrace();
			logger.log(Level.WARNING, "exception in echo protocol", e);
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void run() {
		handleEchoClient(clientSocket, logger);
	}

}
