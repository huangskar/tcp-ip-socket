package char05;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EchoSelectorProtocol implements TCPProtocol {
	private int buffSize;// the size of buffer

	public EchoSelectorProtocol(int buffSize) {
		super();
		this.buffSize = buffSize;
	}

	@Override
	public void handleAccpet(SelectionKey key) throws IOException {
		System.out.println("accept");
		SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
		channel.configureBlocking(false);//设置信道为非阻塞
		channel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(buffSize));
		//实际上也只有非阻塞的信道能够被注册 阻塞信道注册将抛出IllegalBlockingModeException异常
	}

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		System.out.println("read");
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = (ByteBuffer) key.attachment();
		long byteRead = channel.read(buffer);
		System.out.println("read:" + new String(buffer.array()));
		if (byteRead == -1) {
			channel.close();//
		} else if (byteRead > 0) {
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
	}

	@Override
	public void handleWrite(SelectionKey key) throws IOException {
		System.out.println("write");
		ByteBuffer buffer = (ByteBuffer) key.attachment();
		buffer.flip();// 准备写
		SocketChannel channel = (SocketChannel) key.channel();
		System.out.println("write:" + new String(buffer.array()));
		channel.write(buffer);
		if (!buffer.hasRemaining()) { // 没有空余的空间 只能写入
			key.interestOps(SelectionKey.OP_READ);
		}
		buffer.compact();// 压缩ByteBuffer
	}

}
