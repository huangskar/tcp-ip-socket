package char03;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VoteServerTCP {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			throw new IllegalArgumentException();
		}
		int port = Integer.parseInt(args[0]);
		@SuppressWarnings("resource")
		ServerSocket serverSocket =new ServerSocket(port);
		VoteMsgCoder coder=new VoteMsgBinCoder();
		VoteService service=new VoteService();
		while(true){
			Socket socket=serverSocket.accept();
			InputStream in=socket.getInputStream();
			OutputStream out=socket.getOutputStream();
			System.out.println("server handle "+socket.getInetAddress().getHostName());
			Framer framer=new LengthFramer(in);
			byte[] req;
			while((req=framer.nextMsg())!=null){
				VoteMsg responseMsg=service.handleRequest(coder.fromWire(req));
				framer.frameMsg(coder.toWire(responseMsg), out);
			}
		}
	}
}
