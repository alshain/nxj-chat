package ch.nksa.pu.robotics.libs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Uplink {
	protected BTConnection uplink;
	protected DataInputStream dis;
	protected DataOutputStream dos;
	protected Thread inputThread;
	

	
	public Uplink(boolean connect_now){
		if(connect_now){
			connect(10 * 1000);
		}
		
	}
	
	public boolean connect(int timeout){
		LCD.drawString("Waiting for connection.", 0, 0);
		uplink = Bluetooth.waitForConnection();
		dis = uplink.openDataInputStream();
		dos = uplink.openDataOutputStream();
		LCD.drawString("Connected.", 0, 1);
		inputThread = new Thread(){
			public void run(){
				getRequests();
			}
		};
		inputThread.start();
		return false;
	}
	
	/**
	 * must NOT be invoked manually!
	 */
	protected void getRequests(){
		LCD.drawString("Listening...", 0, 2);
		int request_id = 0;
		int type_length = 0;
		String type;
		int content_length = 0;
		
		String content;
		while(true){
			try {
				request_id = dis.readInt();
				LCD.drawInt(request_id, 0, 3);
				
				
				type_length = dis.readInt();
				
				byte[] type_b = new byte[type_length];
				dis.readFully(type_b, 0, type_length);
				type = new String(type_b);
				LCD.drawString(type, 0, 4);
				LCD.drawInt(dis.available(), 0, 7);
				content_length = dis.readInt();
				LCD.drawInt(content_length, 0, 5);
				byte[] content_b = new byte[content_length];
				dis.readFully(content_b, 0, content_length);
				content = new String(content_b);
				LCD.drawString(content, 0, 6);
				LCD.drawInt(dis.available(), 0, 7);
				
			} catch (IOException e) {
				LCD.drawString("Error!", 0, 5);
				break;
			}
			
		}
	}
}
