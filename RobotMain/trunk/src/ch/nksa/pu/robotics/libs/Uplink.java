package ch.nksa.pu.robotics.libs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Uplink {
	protected BTConnection uplink;
	protected DataInputStream dis;
	protected DataOutputStream dos;
	protected Thread inputThread;
	protected ArrayList<IncomingRequestHelper> listeners = new ArrayList<IncomingRequestHelper>();
	protected ArrayList<byte[][]> rawIncoming = new ArrayList<byte[][]>();
	protected ArrayList<IncomingRequest> incomingRequests = new ArrayList<IncomingRequest>();
	/**
	 * Holds a 2d byte array with the latest incoming request
	 */
	protected byte[][] latestRawRequest;
	
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
	
	public void registerListener(IncomingRequestHelper r){
		this.listeners.add(r);
	}
	
	
	/**
	 * must NOT be invoked manually!
	 */
	protected void getRequests(){
		LCD.drawString("Listening...", 0, 2);
		Thread waiting;
		while(true){
			try {
				int length = 0;
				
				int lines = dis.readInt();
				byte[][] incoming = new byte[lines][];
				for(byte[] b: incoming){
					length = dis.readInt();
					b = new byte[length];
					dis.readFully(b, 0, length);
				}
				this.latestRawRequest = incoming;
				
				for(final IncomingRequestHelper l: listeners){
					waiting = l.makeActive();
					try {
						waiting.join();
					} catch (InterruptedException e) {}
					
				}
				/*
				//Header
				type_length = dis.readInt();
				
				byte[] type_b = new byte[type_length];
				dis.readFully(type_b, 0, type_length);
				type = new String(type_b);
				//LCD.drawString(type, 0, 4);
				//LCD.drawInt(dis.available(), 0, 7);
				content_length = dis.readInt();
				//LCD.drawInt(content_length, 0, 5);
				byte[] content_b = new byte[content_length];
				dis.readFully(content_b, 0, content_length);
				content = new String(content_b);
				//LCD.drawString(content, 0, 6);
				//LCD.drawInt(dis.available(), 0, 7);
				*/
			} catch (IOException e) {
				LCD.drawString("Error!", 0, 5);
				break;
			}
			
		}
	}
}
