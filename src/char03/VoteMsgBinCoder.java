package char03;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 二进制编码器
 * 
 * joecqupt 下午2:56:20
 */
public class VoteMsgBinCoder implements VoteMsgCoder {
	private static final int MIN_WIRE_LENGTH = 4;
	@SuppressWarnings("unused")
	private static final int MAX_WIRE_LENGTH = 16;
	private static final int MAGIC = 0x5400;
	private static final int MAGIC_MASK = 0xFC00;
	@SuppressWarnings("unused")
	private static final int MAGIC_SHIFT = 8;
	private static final int RESPONSE_FLAG = 0x0200;
	private static final int INQUIRE_FLAG = 0x0100;

	@Override
	public byte[] toWire(VoteMsg msg) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		int magicAndFlags = MAGIC;
		if (msg.isInquiry()) {
			magicAndFlags |= INQUIRE_FLAG;
		}
		if (msg.isResponse()) {
			magicAndFlags |= RESPONSE_FLAG;
		}
		dos.writeShort(magicAndFlags);
		dos.writeShort(msg.getCandidateId());
		if (msg.isResponse()) {
			dos.writeLong(msg.getVoteCount());
		}
		dos.flush();
		dos.close();
		baos.close();
		return baos.toByteArray();
	}

	@Override
	public VoteMsg fromWire(byte[] input) throws IOException {
		if (input.length < MIN_WIRE_LENGTH) {
			throw new IOException("message short");
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(input);
		DataInputStream dis = new DataInputStream(bais);
		short magicAndFlags = dis.readShort();
		if ((magicAndFlags & MAGIC_MASK) != MAGIC) {
			throw new IOException("bad magic");
		}
		boolean reps = (magicAndFlags & RESPONSE_FLAG) != 0;
		boolean inq = (magicAndFlags & INQUIRE_FLAG) != 0;
		int candidateId = dis.readShort();
		// candidate的校验
		long count = 0;
		if (reps) {
			count = dis.readLong();
		}
		// count 的校验
		return new VoteMsg(inq, reps, candidateId, count);
	}

}
