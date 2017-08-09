package char03;

public class BruteForceCoding {
	private static byte byteVal = 101; // one hundred and one
	private static short shortVal = 10_001; // ten thousand and one
	private static int intVal = 100_000_001; // one hundred million and one
	private static long longVal = 1_000_000_000_001L;// one trillion and one

	private final static int BSIZE = Byte.SIZE / Byte.SIZE;
	private final static int SSIZE = Short.SIZE / Byte.SIZE;
	private final static int ISIZE = Integer.SIZE / Byte.SIZE;
	private final static int LSIZE = Long.SIZE / Byte.SIZE;

	private final static int BYTEMASK = 0xFF; // 8 bits

	public static String byteArrayToDecimalString(byte[] bArray) {
		// 下载自：http://www.javaxxz.com 最方便的Java学习社区
		StringBuilder rtn = new StringBuilder();
		for (byte b : bArray) {
			rtn.append(b & BYTEMASK).append(" "); 
			//BYTEMASK 的作用是防止在字节数字转换成int类型时，发生符号扩展（sign-extended），即转换成无符号类型。
			//& 0xff 只保留低 8位bit
		}
		return rtn.toString();
	}

	// Warning: Untested preconditions (e.g., 0 <= size <=)

	public static int encodeIntBigEndian(byte[] dst, long val, int offset, int size) {
		for (int i = 0; i < size; i++) {
			dst[offset++] = (byte) (val >> ((size - i - 1) * Byte.SIZE));
		}
		return offset;
	}

	// Warning: Untested preconditions (e.g., 0 <= size <=)

	public static long decodeIntBigEndian(byte[] val, int offset, int size) {
		long rtn = 0;
		for (int i = 0; i < size; i++) {
			rtn = (rtn << Byte.SIZE) | ((long) val[offset + i] & BYTEMASK);
		}
		return rtn;
	}

	public static void main(String[] args) {
		byte[] message = new byte[BSIZE + SSIZE + ISIZE + LSIZE];
		// Encode the fields in the target byte array
		int offset = encodeIntBigEndian(message, byteVal, 0, BSIZE);
		offset = encodeIntBigEndian(message, shortVal, offset, SSIZE);
		offset = encodeIntBigEndian(message, intVal, offset, ISIZE);
		encodeIntBigEndian(message, longVal, offset, LSIZE);
		System.out.println("Encoded message: " + byteArrayToDecimalString(message));

		// Decode several fields
		// 下载自：http://www.javaxxz.com 最方便的Java学习社区
		long value = decodeIntBigEndian(message, BSIZE, SSIZE);
		System.out.println("Decoded short = " + value);
		value = decodeIntBigEndian(message, BSIZE + SSIZE + ISIZE, LSIZE);
		System.out.println("Decoded long = " + value);

		// Demonstrate dangers of conversion
		offset = 4;
		value = decodeIntBigEndian(message, offset, BSIZE);
		System.out.println("Decoded value (offset " + offset + ", size " + BSIZE + ") = " + value);
		byte bVal = (byte) decodeIntBigEndian(message, offset, BSIZE);
		System.out.println("Same value as byte = " + bVal);
	}

}