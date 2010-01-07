package ch.nksa.pu.robotics.libs.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import ch.nksa.pu.robotics.libs.RequestOwner;

import lejos.pc.comm.*;

public class Slave extends RequestOwner {
	protected String name;
	protected String address;
	protected boolean connected = false;
	//protected SlaveState state = SlaveState.UNREACHABLE; 
	protected NXTComm nxtComm;
	protected NXTInfo nxtInfo;
	protected volatile ArrayList<BasicIncomingNxtRequest> incomingRequests = new ArrayList<BasicIncomingNxtRequest>();
	protected volatile ArrayList<BasicOutgoingPcRequest> outgoingRequests = new ArrayList<BasicOutgoingPcRequest>();
	
	/*public enum SlaveState{
		UNREACHABLE,
		BUSY,
		READY;
	}*/
	
	public Slave(String a_name, String an_address){
		name = a_name;
		address = an_address;
	}
	
	public boolean isConnected(){
		return connected;
	}
	
	public boolean connect(){
		if(!connected){
			connected = true;
			try {
				System.out.println("Trying to connect to NXT " + name + ". Please wait...");
				nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
				nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, name, address);
				nxtComm.open(nxtInfo);
				dis = new DataInputStream(nxtComm.getInputStream());
				dos = new DataOutputStream(nxtComm.getOutputStream());
				initializeThreads();
			} catch (NXTCommException e) {
				// TODO Auto-generated catch block
				System.out.println("Error! Could not connect to device.");
				//e.printStackTrace();
				connected = false;
			}
		}
		return connected;
	}
	
	public void disconnect(){
		receivingThread.interrupt();
		sendingThread.interrupt();
		try {
			nxtComm.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	

	
	public BasicOutgoingPcRequest sendGenericRequest(String sender, String nick, String subject, byte[][] data){
		return new BasicOutgoingPcRequest(this, sender, nick, subject, data);
	}
	
	public BasicOutgoingPcRequest sendGenericRequest(String sender, String nick, String subject, String content){
		byte[][] data = new byte[1][];
		data[0] = content.getBytes();
		return new BasicOutgoingPcRequest(this, sender, nick, subject, data);
	}
	
	public BasicOutgoingPcRequest getOutgoingRequest(int id){
		if(outgoingRequests.size() > id){
			return outgoingRequests.get(id);
		}
		return null;
	}
	
	public BasicIncomingNxtRequest getIncomingRequest(int id){
		if(incomingRequests.size() > id){
			return incomingRequests.get(id);
		}
		return null;
	}

	public void registerListener(BasicIncomingNxtRequest l) {
		synchronized (listeners) {
			listeners.add(l);
		}
		System.out.println("Active listeners: " + listeners.size());
	}
}
