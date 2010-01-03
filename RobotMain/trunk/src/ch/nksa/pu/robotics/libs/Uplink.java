package ch.nksa.pu.robotics.libs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import ch.nksa.pu.robotics.libs.IncomingRequest;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Uplink extends RequestOwner{
	protected BTConnection uplink;
	protected Thread receivingThread;
	protected Thread sendingThread;
	protected static Uplink instance;
	/**
	 * Holds a 2d byte array with the latest incoming request
	 */
	protected RequestStruct requestStruct;
	
	protected Uplink(boolean connect_now){
		if(connect_now){
			connect(10 * 1000);
		}
	}
	
	//Singleton Patterns
	public static Uplink getInstance(){
		return getInstance(false);
	}
	
	public synchronized static Uplink getInstance(boolean connect_now){
		if(instance == null){
			instance = new Uplink(connect_now);
		}
		return instance;
	}
	//end Singleton
	
	public boolean connect(int timeout){
		Util.log("Waiting for connection...");
		uplink = Bluetooth.waitForConnection();
		dis = uplink.openDataInputStream();
		dos = uplink.openDataOutputStream();
		Util.log("Connection established.");
		initializeThreads();
		return true;
	}
}
