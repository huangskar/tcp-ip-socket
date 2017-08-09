package char05;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Runnable() {
			@Override
			public void run() {
				try {
					TCPServerSelector.main(null);// 开启 nonblocking 的服务器
				} catch (Exception e) {
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
						TCPEchoClientNonblocking.main(null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
