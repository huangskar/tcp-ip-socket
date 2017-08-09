package char04;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

public class CompressProtocol implements Runnable {

	private Socket socket;
	private static final int BUFF_SIZE = 256;

	public CompressProtocol(Socket socket) {
		super();
		this.socket = socket;
	}

	public static void handleCompressClient(Socket socket) {
		try {
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			GZIPOutputStream gos = new GZIPOutputStream(out);
			byte[] buffer = new byte[BUFF_SIZE];
			int temp = 0;
			while ((temp = in.read(buffer)) != -1) {
				gos.write(buffer, 0, temp);
			}
			gos.finish();
			socket.close();//close socket
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		handleCompressClient(socket);
	}

}
