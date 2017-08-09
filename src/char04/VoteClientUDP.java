package char04;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import char03.VoteMsg;
import char03.VoteMsgCoder;
import char03.VoteMsgTextCoder;
/**
 * 测试广播
 * 
 * joecqupt 下午5:06:30
 */
public class VoteClientUDP {
	public static void main(String[] args) throws IOException {
		// three args
		// String hostName = "127.0.0.1";
		String hostName = "255.255.255.255";
		int candidateId = 888;
		int port = 14141;
		VoteMsgCoder coder = new VoteMsgTextCoder();
		DatagramSocket datagramSocket = new DatagramSocket();
		datagramSocket.connect(InetAddress.getByName(hostName), port);
		VoteMsg msg = new VoteMsg(true, false, candidateId, 0);
		byte[] data = coder.toWire(msg);
		DatagramPacket packet = new DatagramPacket(data, data.length);
		datagramSocket.send(packet);
		packet = new DatagramPacket(new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH], VoteMsgTextCoder.MAX_WIRE_LENGTH);
		datagramSocket.receive(packet);
		data = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
		System.out.println("recive response!");
		msg = coder.fromWire(data);
		System.out.println("recive response:" + msg);
		datagramSocket.close();
	}
}
