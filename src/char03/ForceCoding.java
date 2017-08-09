package char03;

public class ForceCoding {
	private static final int BSIZE = Byte.SIZE / Byte.SIZE;
	private static final int SSIZE = Short.SIZE / Byte.SIZE;
	private static final int ISIZE = Integer.SIZE / Byte.SIZE;
	private static final int LSIZE = Long.SIZE / Byte.SIZE;

	private static final int BYTEMASK = 0xFF;

	private static byte byteVal = 101;
	private static short shortVal = 10_001;
	private static int intVal = 100_000_001;
	private static long longVal = 1_000_000_000_001L;

	public static String byteArrayToDecimalString(byte[] bArray) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bArray) {
			sb.append(b & BYTEMASK).append(" ");
		}
		return sb.toString();
	}

	public static int encodeIntBigEndian(byte[] dst, long val, int offset, int size) {
		for (int i = 0; i < size; i++) {
			dst[offset++] = (byte) (val >> ((size - i - 1) * Byte.SIZE));
		}
		return offset;
	}

	public static long decodeIntBigEndian(byte[] val, int offset, int size) {
		long result = 0;
		//这段代码不好的问题是 他会改变offset的值
		// for (int i = size; i > 0; i--) {
		// result = result | (val[offset++] << ((i - 1) * Byte.SIZE));
		// }
		for (int i = 0; i < size; i++) {
			result = result << Byte.SIZE | ((long)val[offset + i] & BYTEMASK);
		}
		return result;
		
	}

	public static void main(String[] args) {
		byte[] message = new byte[BSIZE + SSIZE + ISIZE + LSIZE];

		int offset = encodeIntBigEndian(message, byteVal, 0, BSIZE);
		System.out.println(byteArrayToDecimalString(message));
		System.out.println(decodeIntBigEndian(message, 0, BSIZE));

		offset = encodeIntBigEndian(message, shortVal, offset, SSIZE);
		System.out.println(byteArrayToDecimalString(message));
		System.out.println(decodeIntBigEndian(message, 1, SSIZE));
		
		offset = encodeIntBigEndian(message, intVal, offset, ISIZE);
		System.out.println(byteArrayToDecimalString(message));
		System.out.println(decodeIntBigEndian(message, 3, ISIZE));
		
		offset = encodeIntBigEndian(message, longVal, offset, LSIZE);
		System.out.println(byteArrayToDecimalString(message));
		System.out.println(decodeIntBigEndian(message, 7, LSIZE));
	}
}
