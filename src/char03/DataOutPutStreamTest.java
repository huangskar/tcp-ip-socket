package char03;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataOutPutStreamTest {
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeByte(101);
		dos.writeShort(10_001);
		dos.writeInt(100_000_001);
		dos.writeLong(1_000_000_000_001L);
		dos.flush();
		dos.close();
		baos.close();
		byte[] data = baos.toByteArray();
		for (byte b : data) {
			System.out.print(b & 0xFF);
			System.out.print(" ");
		}
	}
}
