package char04;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class TimeLimitEchoProtocol implements Runnable {

	private static final int TIME_LIMIT = 3000;// 超时时间3000毫秒
	private static final int BUFF_SIZE = 32;

	private Socket socket;
	private Logger logger;

	public TimeLimitEchoProtocol(Socket socket, Logger logger) {
		super();
		this.socket = socket;
		this.logger = logger;
	}

	public static void handleEchoClient(Socket clientSocket, Logger logger) {
		try {
			InputStream in = clientSocket.getInputStream();
			OutputStream out = clientSocket.getOutputStream();
			int recive = 0;
			@SuppressWarnings("unused")
			int reciveTotal = 0;
			byte[] buffer = new byte[BUFF_SIZE];
			long endTime = System.currentTimeMillis() + TIME_LIMIT;
			int timeBoundMillis = TIME_LIMIT;
			clientSocket.setSoTimeout(timeBoundMillis);
			while ((timeBoundMillis > 0) && ((recive = in.read(buffer)) != -1)) {
				out.write(buffer, 0, recive);
				reciveTotal += recive;
				timeBoundMillis = (int) (endTime - System.currentTimeMillis());
				clientSocket.setSoTimeout(timeBoundMillis);
			}
		} catch (IOException e) {
			logger.warning("Exception :" + e);
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		handleEchoClient(socket, logger);
	}

}
