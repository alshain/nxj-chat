package ch.nksa.pu.robotics.libs.pc;

import ch.nksa.pu.robotics.libs.Request;
import ch.nksa.pu.robotics.libs.RequestMode;
import ch.nksa.pu.robotics.libs.RequestStruct;

public class BasicOutgoingPcRequest extends ch.nksa.pu.robotics.libs.OutgoingRequest{
	
	
	public BasicOutgoingPcRequest(RequestStruct req, Boolean send) {
		super(req, send);
		// TODO Auto-generated constructor stub
	}

	public BasicOutgoingPcRequest(RequestStruct req) {
		this(req, false);
	}

	public BasicOutgoingPcRequest followUp(byte[][] data){
		return new BasicOutgoingPcRequest(
				new RequestStruct(owner, RequestMode.FOLLOW_UP, this, getSender(), nick, subject, data));
	}
	
	public BasicOutgoingPcRequest followUp(String new_subject, byte[][] data){
		return new BasicOutgoingPcRequest(
				new RequestStruct(owner, RequestMode.FOLLOW_UP, this, getSender(), nick, new_subject, data));
	}
}
