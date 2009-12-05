package ch.nksa.pu.g2007e.pingbot;

import java.util.Vector;

import lejos.nxt.comm.Bluetooth;

public class NxtCommunication {
	//Inquire code
	private static byte[] cod = {0,0,0,0}; // 0,0,0,0 picks up every Bluetooth device regardless of Class of Device (cod).
	
	static Vector devList;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}
	
	public static boolean discoverDevices() throws Exception{
		devList = Bluetooth.inquire(5, 10, cod);
		return false;
		
	}
	

}
