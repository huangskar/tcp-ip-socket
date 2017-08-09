package char05;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class TCPServerSelector {
	private static final int BUFF_SIZE = 256;
	private static final int TIME_OUT = 3000;
	private static int[] ports = new int[] { 13131, 13132 };

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		for (int i : ports) {
			ServerSocketChannel listnChannel = ServerSocketChannel.open();
			listnChannel.socket().bind(new InetSocketAddress(i));
			listnChannel.configureBlocking(false);
			listnChannel.register(selector, SelectionKey.OP_ACCEPT);
		}
		TCPProtocol protocol = new EchoSelectorProtocol(BUFF_SIZE);
		while (true) {
			if (selector.select(TIME_OUT) == 0) {
				System.out.println("server do something else!");
				continue;
			}
			Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
			while (keyIter.hasNext()) {
				SelectionKey key = keyIter.next();
				if (key.isAcceptable()) {
					protocol.handleAccpet(key);
				}
				if (key.isReadable()) {
					protocol.handleRead(key);
				}
				if (key.isValid() && key.isWritable()) {
					protocol.handleWrite(key);
				}
				keyIter.remove();
			}
		}
	}
}
