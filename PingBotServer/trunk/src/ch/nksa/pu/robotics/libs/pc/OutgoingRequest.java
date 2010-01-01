package ch.nksa.pu.robotics.libs.pc;

import ch.nksa.pu.robotics.libs.Util;

public class OutgoingRequest extends Request{
	protected boolean hasBeenSent = false;
	protected Thread sentTest;
	protected RequestMode mode;
	
	public enum RequestMode{
		STATELESS,
		RESPONSE,
		FOLLOW_UP,
		BROADCAST;
	}
	
	public OutgoingRequest(Slave owner, String subject, byte[][] data){
		super(owner, subject, data);
		mode = RequestMode.STATELESS;
		initializeRequest(owner, subject, data);
	}
	
	private OutgoingRequest(Slave owner, RequestMode mode, Request reference, String subject, byte[][] data){
		super(owner, subject, data);
		this.mode = mode;
		this.reference = reference;
		initializeRequest(owner, subject, data);
	}
	
	private void initializeRequest(Slave owner, String subject, byte[][] data){
		this.owner = owner;
		this.data = data;
		sentTest = new Thread(){
			public void run(){
				while(!isSent()){}
				System.out.println("Request " + id + " has been sent.");
			}
		};
		sentTest.start();
		owner.registerRequest(this);
	}
	
	protected Thread getSendCheckThread(){
		return sentTest;
	}
	
	public boolean isSent(){
		return hasBeenSent;
	}
	
	public OutgoingRequest followUp(String subject, byte[][] data){
		OutgoingRequest req = new OutgoingRequest(owner, RequestMode.FOLLOW_UP, this, subject, data);
		return req;
	}
	
	public Thread getWaitingThread(){
		Thread temp = new Thread(){
			public void run(){
				//TODO implement waitForreply
			}
		};
		temp.start();
		return temp;
	}
	
	public void waitForReply() throws InterruptedException{
		getWaitingThread().join();
		return;
	}
	
	public byte[][] getHeader(){
		//id
		//RequestMode
		//ReferenceId
		//Sender
		//Nick
		//Subject
		byte[][] header = new byte[8][];
		header[0] = Util.intToBytes(id);
		header[1] = Util.intToBytes(mode.ordinal());
		header[2] = Util.intToBytes(referenceId);
		
		header[3] = Util.stringToBytes(this.getClass().getCanonicalName());

		header[4] = Util.stringToBytes(nick);
		
		header[5] = Util.stringToBytes(subject);
		return data;
	}
}
