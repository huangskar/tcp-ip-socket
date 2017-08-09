package char05;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class UDPEchoServerSelector {
	private static final int TIME_OUT = 3000;
	private static final int BUFF_SIZE = 256;

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		DatagramChannel datagramChannel = DatagramChannel.open();
		datagramChannel.configureBlocking(false);
		datagramChannel.bind(new InetSocketAddress(13131));
		datagramChannel.register(selector, SelectionKey.OP_READ, new DataRecord());
		while (true) {
			if (selector.select(TIME_OUT) == 0) {
				System.out.println("server do something else!");
				continue;
			}
			Iterator<SelectionKey> itr = selector.selectedKeys().iterator();
			while (itr.hasNext()) {
				SelectionKey key = itr.next();
				if (key.isReadable()) {
					handleRead(key);
				}
				if (key.isValid() && key.isWritable()) {
					handleWrite(key);
				}
				itr.remove();
			}
		}
	}

	private static void handleWrite(SelectionKey key) throws IOException {
//		System.out.println("write");
		DatagramChannel channel = (DatagramChannel) key.channel();
		DataRecord record = (DataRecord) key.attachment();
		record.buffer.flip();// 准备读
		int byteSend = channel.send(record.buffer, record.clientAddress);
		if (byteSend != 0) {
			key.interestOps(SelectionKey.OP_READ);
		}
	}

	private static void handleRead(SelectionKey key) throws IOException {
//		System.out.println("read");
		DatagramChannel channel = (DatagramChannel) key.channel();
		DataRecord record = (DataRecord) key.attachment();
		record.buffer.clear();
		record.clientAddress = channel.receive(record.buffer);
//		System.out.println("server receive:" + new String(record.buffer.array()));
		if (record.clientAddress != null) {
			key.interestOps(SelectionKey.OP_WRITE);
		}
	}

	static class DataRecord {
		public ByteBuffer buffer = ByteBuffer.allocate(BUFF_SIZE);
		public SocketAddress clientAddress;
	}
}
