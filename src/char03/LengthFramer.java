package char03;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 长度帧
 * 
 * joecqupt 下午2:49:23
 */
public class LengthFramer implements Framer {
	private static final int MAX_LENGTH = 65535;
	private static final int BYTE_MASK = 0xFF;
	private static final int BYTE_SHIFT = 8;
	@SuppressWarnings("unused")
	private static final int SHORT_MASK = 0xFFFF;
	private DataInputStream in;

	public LengthFramer(InputStream in) {
		super();
		this.in = new DataInputStream(in);
	}

	@Override
	public void frameMsg(byte[] message, OutputStream out) throws Exception {
		if (message.length > MAX_LENGTH) {
			throw new IOException("message too long!");
		}
		out.write((message.length >> BYTE_SHIFT) & BYTE_MASK);
		out.write(message.length & BYTE_MASK);
		//写入消息长度
		out.write(message);
		out.flush();
	}

	@Override
	public byte[] nextMsg() throws Exception {
		int messageLength = 0;
		try {
			messageLength = in.readUnsignedShort();
		} catch (EOFException e) {
			return null;
		}
		byte[] msg = new byte[messageLength];
		in.readFully(msg);
		//使用 dataInputStream 读取帧数据
		return msg;
	}

}
