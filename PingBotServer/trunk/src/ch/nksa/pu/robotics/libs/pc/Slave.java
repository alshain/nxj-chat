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
	//protected SlaveState state = SlaveState.UNREACHABLE; 
	protected NXTComm nxtComm;
	protected NXTInfo nxtInfo;
	protected DataInputStream dis;
	protected DataOutputStream dos;
	protected Vector<OutgoingRequest> requestOutStack = new Vector<OutgoingRequest>();
	protected Vector<IncomingRequest> requestInStack = new Vector<IncomingRequest>();
	protected int requestsSent = 0;
	protected Thread sendingThread;
	protected Thread receivingThread;
	
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
	
	public OutgoingRequest sendRequest(String subject, String content){
		byte[][] data = new byte[1][];
		data[0] = content.getBytes();
		OutgoingRequest req = new OutgoingRequest(this, subject, data);
		requestOutStack.add(req);
		return req;
	}
	
	public void sendRequest(OutgoingRequest req){
		requestOutStack.add(req);
	}
	
	protected void sendRequests(){
		System.out.print("Launching Request-Out-Thread...");
		while(true){
			if(requestOutStack.size() > requestsSent){
				OutgoingRequest req = requestOutStack.get(requestsSent);
				try {
					/**
					 * Request Protocol Format
					 * 
					 *id
					 * RequestMode
					 * ReferenceId
					 * SenderLength
					 * Sender
					 * NickLength
   					 * Nick
					 * SubjectLength
					 * Subject
					 */
					byte[][] header;
					header = req.getHeader();
					byte[][] data;
					data = req.getData();
					dos.writeInt(header.length + data.length);
					for(byte[] b: header){
						dos.writeInt(b.length);
						dos.write(b);
					}
					
					for(byte[] b: data){
						dos.writeInt(b.length);
						dos.write(b);
					}
					dos.flush();
					req.hasBeenSent = true;
					requestsSent ++;
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
	
	public OutgoingRequest getOutgoingRequest(int id){
		if(requestOutStack.size() > id){
			return requestOutStack.get(id);
		}
		
		return null;
	}
	
	protected void registerRequest(OutgoingRequest req){
		req.id = requestOutStack.size();
		requestOutStack.add(req);
	}
	
	protected void receiveRequests(){
		System.out.println("Listening to " + name + "...");
		int request_id = 0;
		int data_length = 0;
		int field_count;
		byte[][] data = new byte[4][];
		
		while(true){
			try {
				request_id = dis.readInt();
				field_count = dis.readInt();
				for(int i = 0; i < field_count; i++){
					data_length = dis.readInt();
					data[i] = new byte[data_length];
					dis.readFully(data[i], 0, data_length);
				}
				//TODO: CHECK HERE!!!
				//IncomingRequest req = new IncomingRequest(this, request_id, data);
				
			} catch (IOException e) {
				System.out.println(name + ": Error while receiving request.");
				break;
			}
			
		}
	}
}
