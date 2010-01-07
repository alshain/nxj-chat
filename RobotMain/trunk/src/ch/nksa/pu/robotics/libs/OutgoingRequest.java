package ch.nksa.pu.robotics.libs;

import ch.nksa.pu.robotics.libs.RequestMode;
import ch.nksa.pu.robotics.libs.Util;

public class OutgoingRequest extends Request{
	protected boolean hasBeenSent = false;
	protected Thread sentTest = null;
	protected boolean replyReceived = false;
	
	public OutgoingRequest(RequestOwner owner, String sender, String nick, String subject, byte[][] data){
		super(owner, sender, nick, subject, data);
		send();
	}
	
	public OutgoingRequest(RequestOwner owner, String sender, String subject, byte[][] data){
		super(owner, sender, "default", subject, data);
		send();
	}

	
	protected OutgoingRequest(RequestOwner owner, RequestMode mode, Request reference, 
							String sender, String nick, String subject, byte[][] data){
		super(owner, mode, reference, sender, nick, subject, data);
		send();
	}
	
	public synchronized void send(){
		if(sentTest == null){
			sentTest = new Thread(){
				public void run(){
					while(!isSent()){}
					Util.log("Request " + id + " has been sent.");
				}
			};
			sentTest.start();
			owner.registerRequest(this);
		}
	}
	
	public OutgoingRequest followUp(byte[][] data){
		return new OutgoingRequest(owner, RequestMode.FOLLOW_UP, this, getSender(), nick, subject, data);
	}
	
	public OutgoingRequest followUp(String new_subject, byte[][] data){
		return new OutgoingRequest(owner, RequestMode.FOLLOW_UP, this, getSender(), nick, new_subject, data);
	}
	
	protected Thread getSendCheckThread(){
		return sentTest;
	}
	
	public boolean isSent(){
		return hasBeenSent;
	}
	
	public void waitForReply() throws InterruptedException{
		waitForMonitor();
	}
	
	public byte[][] getHeader(){
		//id
		//RequestMode
		//ReferenceId
		//Sender
		//Nick
		//Subject
		byte[][] header = new byte[6][];
		header[0] = Util.intToBytes(id);
		header[1] = Util.stringToBytes(mode.toString());
		header[2] = Util.intToBytes(getReferenceId());
		
		header[3] = Util.stringToBytes(getSender());

		header[4] = Util.stringToBytes(nick);
		
		header[5] = Util.stringToBytes(subject);
		return header;
	}
}
