package com.example.nxjchat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import lejos.nxt.LCD;
import lejos.nxt.comm.*;

public class NxtCommunication {
	//Inquire code
	static BTConnection btc;
	/**
	 * @param args
	 */
	public NxtCommunication(){
		//waiting for connection
		LCD.drawString("waiting for connection....", 0, 0);
		btc = Bluetooth.waitForConnection();
		LCD.drawString("Connected", 0, 1);
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		try {
			byte test = dis.readByte();
			LCD.drawInt(test, 0, 2);
		} catch (IOException e) {}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		
		
	}
	
	
	

}
