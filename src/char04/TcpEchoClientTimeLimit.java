package char04;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class TcpEchoClientTimeLimit {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		if (args.length < 2 || args.length > 3) {
			throw new IllegalArgumentException();
		}
		String server = args[0];
		byte[] data = args[1].getBytes();
		int serverPort = Integer.parseInt(args[2]);
		@SuppressWarnings("resource")
		Socket socket = new Socket(server, serverPort);
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		TimeUnit.SECONDS.sleep(4);
		out.write(data);

		// in.read(data);//就算知道服务器回回来的数据长度，这里也不能使用一个read直接读，因为可能会被Tcp分割成多个部分

		int totalrecived = 0;
		int recived;
		while (totalrecived < data.length) {
			if ((recived = in.read(data, totalrecived, data.length - totalrecived)) == -1) {
				throw new IllegalStateException();
			}
			totalrecived += recived;
		}

		System.out.println("received:" + new String(data));
		socket.close();
	}
}
