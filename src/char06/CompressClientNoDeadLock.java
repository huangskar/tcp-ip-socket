package char06;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class CompressClientNoDeadLock {
	public static final int BUFF_SIZE = 256;
	public static String fileName = "E:/CompressTest.txt";

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 13131);
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file + ".gz");

		// 让读写在两个线程中 避免死锁的可能
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				writeByte(socket, fis);
			}
		});
		thread.start();

		InputStream in = socket.getInputStream();
		byte[] buffer = new byte[BUFF_SIZE];
		int temp = 0;
		while ((temp = in.read(buffer)) != -1) {
			fos.write(buffer, 0, temp);
		}
		System.out.println("获取压缩文件！");
		fos.close();
		socket.close();
	}

	private static void writeByte(Socket socket, FileInputStream fis) {
		byte[] buffer = new byte[BUFF_SIZE];
		int receive = 0;
		OutputStream out;
		try {
			out = socket.getOutputStream();
			while ((receive = fis.read(buffer)) != -1) {
				out.write(buffer, 0, receive);
			}
			socket.shutdownOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
