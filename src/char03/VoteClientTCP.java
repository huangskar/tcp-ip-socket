package char03;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class VoteClientTCP {
	public static final int CANDIDATEID = 888;

	public static void main(String[] args) throws Exception {
//		if (args.length != 2) {
//			throw new IllegalArgumentException();
//		}
		String destAddr = "127.0.0.1";
//		int port = Integer.parseInt(args[1]);
		int port = 13131;
		Socket socket = new Socket(destAddr, port);
		OutputStream out = socket.getOutputStream();
		InputStream in = socket.getInputStream();

		VoteMsg msg = new VoteMsg(true, false, CANDIDATEID, 0);
		VoteMsgCoder coder = new VoteMsgBinCoder();
		byte[] data = coder.toWire(msg);
		Framer framer = new LengthFramer(in);
		System.out.println("now send query");
		framer.frameMsg(data, out);
		// send a vote msg
		msg.setInquiry(false);
		data = coder.toWire(msg);
		System.out.println("now send vote");
		framer.frameMsg(data, out);

		// recive msg
		data = framer.nextMsg();
		msg = coder.fromWire(data);
		System.out.println("client recive response:" + msg);
		data = framer.nextMsg();
		msg = coder.fromWire(data);
		System.out.println("client recive response:" + msg);
		socket.close();
	}
}
