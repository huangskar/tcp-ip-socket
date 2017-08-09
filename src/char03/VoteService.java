package char03;

import java.util.HashMap;
import java.util.Map;

public class VoteService {
	private Map<Integer, Long> result = new HashMap<>();

	public VoteMsg handleRequest(VoteMsg msg) {
		if (msg.isResponse()) {
			return msg;
		}
		msg.setResponse(true);
		int candidate = msg.getCandidateId();
		Long count=result.get(candidate);
		if(count==null){
			count=0L;
		}
		if(!msg.isInquiry()){
			result.put(candidate, ++count);
		}
		msg.setVoteCount(count);
		return msg;
	}
}
