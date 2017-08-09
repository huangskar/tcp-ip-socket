package char04;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main4 {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Runnable() {
			@Override
			public void run() {
				try {
					TCPEchoServerExecutor.main(new String[] { "13131" });
					// TCPEchoServerPool.main(new String[] { "13131" });
					// TCPEchoServerThread.main(new String[] { "13131" });
					// TcpEchoServer.main(new String[] { "13131" });
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		System.out.println("start the server!");
		for (int i = 0; i < 10; i++) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						TcpEchoClient.main(new String[] { "127.0.0.1", "Tcp-sleep-test", "13131" });
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
