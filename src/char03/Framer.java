package char03;

import java.io.OutputStream;

/**
 * 帧
 * 
 * joecqupt 下午11:33:36
 */
public interface Framer {
	/**
	 * 使消息 变成帧
	 * 
	 * @param message
	 * @param out
	 * @throws Exception
	 */
	void frameMsg(byte[] message, OutputStream out) throws Exception;

	/**
	 * 从帧中获取消息
	 * 
	 * @return
	 * @throws Exception
	 */
	byte[] nextMsg() throws Exception;
}
