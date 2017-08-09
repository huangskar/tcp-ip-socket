package char05;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface TCPProtocol {
	void handleAccpet(SelectionKey key) throws IOException;

	void handleRead(SelectionKey key) throws IOException;

	void handleWrite(SelectionKey key) throws IOException;
}
