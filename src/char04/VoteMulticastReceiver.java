package char04;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

import char03.VoteMsgCoder;
import char03.VoteMsgTextCoder;

public class VoteMulticastReceiver {
	private static String multiAddress = "239.255.255.254";// 多播的地址
	private static int port = 13133;

	public static void main(String[] args) throws Exception {
		InetAddress inetAddress = InetAddress.getByName(multiAddress);
		if (!inetAddress.isMulticastAddress()) {
			throw new Exception("这不是一个多播地址");
		}
		MulticastSocket socket = new MulticastSocket(port);
		socket.joinGroup(inetAddress);
		VoteMsgCoder coder = new VoteMsgTextCoder();
		DatagramPacket packet = new DatagramPacket(new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH],
				VoteMsgTextCoder.MAX_WIRE_LENGTH);
		socket.receive(packet);
		byte[] enCodeMsg = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
		System.out.println("receive msg:" + coder.fromWire(enCodeMsg));
		socket.close();
	}
}
