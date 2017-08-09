package char03;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * 文本编码器
 * 
 * joecqupt 下午2:55:15
 */
public class VoteMsgTextCoder implements VoteMsgCoder {
	private static final String DELIMSTR = " ";
	public static final int MAX_WIRE_LENGTH = 2000;
	@SuppressWarnings("unused")
	private static final String CHARSETNAME = "US-ASCII";
	private static final String MAGIC = "Voting";
	private static final String VOTESTR = "v";
	private static final String INQSTR = "i";
	private static final String RESPONSETER = "R";

	@Override
	public byte[] toWire(VoteMsg msg) throws IOException {
		String msgString = MAGIC + DELIMSTR + (msg.isInquiry() ? INQSTR : VOTESTR) + DELIMSTR
				+ (msg.isResponse() ? RESPONSETER : "") + DELIMSTR + msg.getCandidateId() + DELIMSTR
				+ msg.getVoteCount();

		return msgString.getBytes();
	}
	
	@SuppressWarnings("resource")
	@Override
	public VoteMsg fromWire(byte[] input) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(input);
		Scanner scanner = new Scanner(bais);
		String magic = scanner.next();
		if (!magic.equals(MAGIC)) {
			throw new IOException("bad magic!");
		}
		boolean inInquiry;
		boolean isResponse;
		int candidateId;
		long voteCount;
		String token = null;
		token = scanner.next();
		if (token.equals(VOTESTR)) {
			inInquiry = false;
		} else if (!token.equals(INQSTR)) {
			throw new IOException();
		} else {
			inInquiry = true;
		}
		token = scanner.next();
		if (token.equals(RESPONSETER)) {
			isResponse = true;
			token = scanner.next();
		} else {
			isResponse = false;
		}
		candidateId = Integer.parseInt(token);
		if (isResponse) {
			token = scanner.next();
			voteCount = Long.parseLong(token);
		} else {
			voteCount = 0;
		}
		scanner.close();
		return new VoteMsg(inInquiry, isResponse, candidateId, voteCount);
	}

}
