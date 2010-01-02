package ch.nksa.pu.robotics.libs;

public class Util {
	public static byte[] intToBytes(int i){
		byte[] result = new byte[4];
		result[0] = (byte) (i >> 24);
		result[1] = (byte) (i >> 16);
		result[2] = (byte) (i >> 8);
		result[3] = (byte) (i >> 0);
		return result;
	}
	
	public static int bytesToInt(byte[] b){
		int result = 0;
		result += b[0] * 256 * 256 * 256;
		result += b[1] * 256 * 256;
		result += b[2] * 256;
		result += b[3];
		return result;
	}
	
	public static byte[] stringToBytes(String s){
		return s.getBytes("US-ASCII");
	}
	
	public static String bytesToString(byte[] b){
		return new String(b);
	}
}
