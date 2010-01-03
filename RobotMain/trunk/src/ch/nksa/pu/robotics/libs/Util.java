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
	
	public synchronized static void log(String to_log){
		System.out.println(to_log);
	}

	public synchronized static void log(float to_log) {
		System.out.println(to_log);
	}
	
	/**
	 * Thread-Safe output on display
	 */
	public synchronized static void log(int to_log) {
		System.out.println(to_log);
	}
	
	/**
	 * Should only be used when Thread is unlikely be interrupted.
	 * Otherwise behaviour is unpredictable
	 * @param milliseconds
	 */
	public static void sleep(long milliseconds){
		try{
			Thread.sleep(milliseconds);
		}
		catch (InterruptedException e) {}
	}
}
