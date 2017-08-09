package char03;

import java.io.IOException;

/**
 * 投票信息编码器
 * 
 * joecqupt 下午2:54:42
 */
public interface VoteMsgCoder {
	/**
	 * 编码
	 * 
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	byte[] toWire(VoteMsg msg) throws IOException;

	/**
	 * 解码
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	VoteMsg fromWire(byte[] input) throws IOException;
}
