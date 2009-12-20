package ch.nksa.pu.robotics.libs.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import lejos.pc.comm.*;

public class Slave {
	protected String name;
	protected String address;
	protected boolean connected = false;
	protected SlaveState state = SlaveState.UNREACHABLE;
	protected NXTComm nxtComm;
	protected NXTInfo nxtInfo;
	protected DataInputStream dis;
	protected DataOutputStream dos;
	protected Vector<OutgoingRequest> requestOutStack = new Vector<OutgoingRequest>();
	protected Vector<IncomingRequest> requestInStack = new Vector<IncomingRequest>();
	protected int requestsSent = 0;
	protected Thread sendingThread;
	protected Thread receivingThread;
	
	
	
	/**
	 * request count is used to identify the sender of a query. Increase each time a request has been made.
	 */
	public enum SlaveState{
		UNREACHABLE,
		BUSY,
		READY;
	}
	
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
				
				sendingThread = new Thread(){
					public void run(){
						sendRequests();
					}
				};
				sendingThread.start();
				
				receivingThread = new Thread(){
					public void run(){
						receiveRequests();
					}
				};
				receivingThread.start();
				
			} catch (NXTCommException e) {
				// TODO Auto-generated catch block
				System.out.println("Error! Could not connect to device.");
				//e.printStackTrace();
				connected = false;
			}
		}
		return connected;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public OutgoingRequest sendRequest(String type, String content){
		OutgoingRequest req = new OutgoingRequest(this, requestOutStack.size(), type, content);
		requestOutStack.add(req);
		
		return req;
		
	}
	
	protected void sendRequests(){
		System.out.print("Launching Thread...");
		while(true){
			//System.out.println(requestStack.size() + " - " + requestsSent);
			if(requestOutStack.size() > requestsSent){
				OutgoingRequest req = requestOutStack.get(requestsSent);
				try {
					/**
					 * Request Protocol Format
					 * 
					 * id
					 * type_length
					 * type
					 * content_length
					 * content
					 */
					dos.writeInt(requestsSent);
					dos.writeInt(req.getType().getBytes().length);
					dos.write(req.getType().getBytes());
					dos.writeInt(req.getContent().getBytes().length);
					dos.write(req.getContent().getBytes());
					req.hasBeenSent = true;
					requestsSent ++;
					dos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	
	
	protected void receiveRequests(){
		System.out.println("Listening to " + name + "...");
		int request_id = 0;
		int type_length = 0;
		String type;
		int content_length = 0;
		
		String content;
		while(true){
			try {
				request_id = dis.readInt();
				type_length = dis.readInt();
				
				byte[] type_b = new byte[type_length];
				dis.readFully(type_b, 0, type_length);
				type = new String(type_b);
				content_length = dis.readInt();
				byte[] content_b = new byte[content_length];
				dis.readFully(content_b, 0, content_length);
				content = new String(content_b);
				
			} catch (IOException e) {
				System.out.println(name + ": Error while receiving requests.");
				break;
			}
			
		}
	}
}
