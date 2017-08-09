package char03;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 这个类没有自动转义分割符号的功能
 * 
 * joecqupt 上午10:48:40
 */
public class DelimiterFramer implements Framer {
	private static final byte delimiter = '\n';// 结束标志
	private InputStream in;

	public DelimiterFramer(InputStream in) {
		super();
		this.in = in;
	}

	@Override
	public void frameMsg(byte[] message, OutputStream out) throws Exception {
		for (byte b : message) {
			if (b == delimiter) {
				throw new IllegalStateException();
			}
		}
		out.write(message);
		out.write(delimiter);
		out.flush();
	}

	@Override
	public byte[] nextMsg() throws Exception {
		ByteArrayOutputStream messageBuffer = new ByteArrayOutputStream();
		int read = 0;
		while ((read = in.read()) != delimiter) {
			if (read == -1) {
				if (messageBuffer.size() == 0) {
					return null;
				} else {
					throw new EOFException();
				}
			}
			messageBuffer.write(read);
		}
		return messageBuffer.toByteArray();
	}
}
