package char03;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class VoteServerUDP {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		int port = 14141;
		DatagramSocket datagramSocket = new DatagramSocket(port);
		byte[] data = new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH];
		VoteMsgTextCoder coder = new VoteMsgTextCoder();
		VoteService service = new VoteService();
		while (true) {
			DatagramPacket packet = new DatagramPacket(data, data.length);
			datagramSocket.receive(packet);
			byte[] encodeMsg = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
			VoteMsg msg = coder.fromWire(encodeMsg);
			msg = service.handleRequest(msg);
			encodeMsg = coder.toWire(msg);
			packet.setData(encodeMsg);
			datagramSocket.send(packet);
		}
	}
}
