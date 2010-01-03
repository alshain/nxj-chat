package ch.nksa.pu.robotics.libs.pc;

import ch.nksa.pu.robotics.libs.BasicIncomingPcRequest;
import ch.nksa.pu.robotics.libs.IncomingRequest;
import ch.nksa.pu.robotics.libs.RequestMode;
import ch.nksa.pu.robotics.libs.RequestOwner;
import ch.nksa.pu.robotics.libs.RequestStruct;

public class BasicIncomingNxtRequest extends IncomingRequest {
	public BasicIncomingNxtRequest(Slave owner, int id, RequestMode mode, String sender,
			String nick, String subject, byte[][] data) {
		super(owner, id, mode, sender, nick, subject, data);
	}
	
	public BasicIncomingNxtRequest(RequestOwner owner, RequestMode response,
			String sender, String subject, byte[][] data) {
		super(owner, response, sender, sender, subject, data);
	}

	public BasicIncomingNxtRequest(Slave owner, byte[][] rawRequest) {
		super(owner, rawRequest);
	}

	public BasicIncomingNxtRequest(Slave owner, RequestStruct req) {
		super(owner, req);
	}

	private BasicIncomingNxtRequest(Slave owner) {
		super(owner);
	}
	
	public BasicOutgoingPcRequest reply(byte[][] data){
		return new BasicOutgoingPcRequest((Slave) owner, RequestMode.RESPONSE, this, 
				this.getSender(), this.nick, this.subject, data);
	}
	
	public BasicOutgoingPcRequest reply(String new_subject, byte [][] data){
		return new BasicOutgoingPcRequest((Slave) owner, RequestMode.RESPONSE, this, 
				this.getSender(), this.nick, new_subject, data);
	}
	
	public static BasicIncomingNxtRequest validate(Slave owner, byte[][] raw_request){
		System.out.println("Basic Request received, parsing.");
		return new BasicIncomingNxtRequest(owner, raw_request);
	}
	
	public static void registerRequest(BasicIncomingPcRequest dummy){
		IncomingRequest.registerRequest(dummy);
	}
}
