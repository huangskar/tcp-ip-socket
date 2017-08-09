package char04;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import char03.VoteMsg;
import char03.VoteMsgCoder;
import char03.VoteMsgTextCoder;

public class VoteMulticastSender {
	private static String multiAddress = "239.255.255.254";// 多播的地址
	private static int port = 13133;// 设置端口
	private static int ttl = 1;// 设置跳数

	public static void main(String[] args) throws Exception {
		InetAddress inetAddress = InetAddress.getByName(multiAddress);
		if (!inetAddress.isMulticastAddress()) {
			throw new Exception("这不是一个多播地址");
		}
		MulticastSocket socket = new MulticastSocket();
		socket.setTimeToLive(ttl);
		VoteMsgCoder coder = new VoteMsgTextCoder();
		VoteMsg msg = new VoteMsg(true, false, 888, 0);
		byte[] enCodeMsg = coder.toWire(msg);
		DatagramPacket packet = new DatagramPacket(enCodeMsg, enCodeMsg.length, inetAddress, port);
		socket.send(packet);
		socket.close();
	}
}
