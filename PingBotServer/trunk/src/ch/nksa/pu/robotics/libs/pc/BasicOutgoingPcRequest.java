package ch.nksa.pu.robotics.libs.pc;

import ch.nksa.pu.robotics.libs.Request;
import ch.nksa.pu.robotics.libs.RequestMode;

public class BasicOutgoingPcRequest extends ch.nksa.pu.robotics.libs.OutgoingRequest{
	public BasicOutgoingPcRequest(Slave owner, String sender, String nick, String subject, byte[][] data){
		super(owner, sender, nick, subject, data);
	}
	
	public BasicOutgoingPcRequest(Slave owner, String sender, String subject, byte[][] data){
		super(owner, sender, "default", subject, data);
	}

	protected BasicOutgoingPcRequest(Slave owner, RequestMode mode, Request reference, 
							String sender, String nick, String subject, byte[][] data){
		super(owner, mode, reference, sender, nick, subject, data);
	}
	
	public BasicOutgoingPcRequest followUp(byte[][] data){
		return new BasicOutgoingPcRequest((Slave) owner, RequestMode.FOLLOW_UP, this, getSender(), nick, subject, data);
	}
	
	public BasicOutgoingPcRequest followUp(String new_subject, byte[][] data){
		return new BasicOutgoingPcRequest((Slave) owner, RequestMode.FOLLOW_UP, this, getSender(), nick, new_subject, data);
	}
}
