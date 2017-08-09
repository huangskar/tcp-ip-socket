package char03;

public class VoteMsg {
	private boolean isInquiry; // true 为查询信息 false 为投票
	private boolean isResponse;// true 为响应消息
	private int candidateId;// [0,1000]
	private long voteCount;// notzero in response
	private static final int MAX_CANDIDATE_ID = 1000;

	public VoteMsg(boolean isInquiry, boolean isResponse, int candidateId, long voteCount)
			throws IllegalArgumentException {
		if (!isResponse && voteCount != 0) {
			throw new IllegalArgumentException();
		}
		if (candidateId < 0 || candidateId > MAX_CANDIDATE_ID) {
			throw new IllegalArgumentException();
		}
		if (voteCount < 0) {
			throw new IllegalArgumentException();
		}
		this.isInquiry = isInquiry;
		this.isResponse = isResponse;
		this.candidateId = candidateId;
		this.voteCount = voteCount;
	}

	public boolean isInquiry() {
		return isInquiry;
	}

	public void setInquiry(boolean isInquiry) {
		this.isInquiry = isInquiry;
	}

	public boolean isResponse() {
		return isResponse;
	}

	public void setResponse(boolean isResponse) {
		this.isResponse = isResponse;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

	public long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(long voteCount) {
		this.voteCount = voteCount;
	}

	public String toString() {
		String res = (isInquiry ? "inquiry" : "vote") + "for candidate " + candidateId;
		if (isResponse) {
			res = "response to " + res + " who now has " + voteCount + " vote(s)";
		}
		return res;
	}
}
