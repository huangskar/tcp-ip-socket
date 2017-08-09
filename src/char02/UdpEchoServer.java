package char02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpEchoServer {
	private static int maxLength = 255;

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException();
		}
		int port = Integer.parseInt(args[0]);
		@SuppressWarnings("resource")
		DatagramSocket datagramSocket = new DatagramSocket(port);
		DatagramPacket packet = new DatagramPacket(new byte[maxLength], maxLength);
		while (true) {
			datagramSocket.receive(packet);
			System.out.println(
					"handle client at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());
			datagramSocket.send(packet);
			packet.setLength(maxLength);
		}
	}
}
