package ch.nksa.pu.robotics.libs;

import ch.nksa.pu.robotics.libs.RequestMode;
import ch.nksa.pu.robotics.libs.Util;

public class OutgoingRequest extends Request{
	public OutgoingRequest(RequestStruct req) {
		this(req, false);
	}
	
	public OutgoingRequest(RequestStruct req, Boolean send) {
		super(req);
		if(send){
			send();
		}
		// TODO Auto-generated constructor stub
	}

	protected boolean hasBeenSent = false;
	protected Thread sentTest = null;
	protected boolean replyReceived = false;
	
	public synchronized OutgoingRequest send(){
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
		return this;
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
