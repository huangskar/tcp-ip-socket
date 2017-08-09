package char05;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPEchoClientNonblocking {
	private static String hostName = "127.0.0.1";
	private static int port = 13132;
	private static String message = "nonBlock-test";

	public static void main(String[] args) throws IOException {
		byte[] data = message.getBytes();
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		if (!socketChannel.connect(new InetSocketAddress(hostName, port))) {
			// 如果没有连上
			while (!socketChannel.finishConnect()) {
				// socketChannel 没有结束连接
				System.out.println("client do something else!");
			}
		}
		ByteBuffer writeBuffer = ByteBuffer.wrap(data);
		ByteBuffer readBuffer = ByteBuffer.allocate(data.length);
		int totalByteReceive = 0;
		int receive = 0;
		while (totalByteReceive < data.length) {
			if (writeBuffer.hasRemaining()) {
				socketChannel.write(writeBuffer);
			}
			if ((receive = socketChannel.read(readBuffer)) == -1) {
				throw new SocketException();
			}
			totalByteReceive += receive;
		}
		System.out.println("receive response:" + new String(readBuffer.array()));
		socketChannel.close();
	}
}
