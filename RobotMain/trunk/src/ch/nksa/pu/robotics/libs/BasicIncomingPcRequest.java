package ch.nksa.pu.robotics.libs;


public class BasicIncomingPcRequest extends IncomingRequest {
	//!warning: hides non-static owner of super! 
	protected static Uplink owner = Uplink.getInstance();

	public BasicIncomingPcRequest(RequestStruct req) {
		super(req);
	}
	
	public BasicIncomingPcRequest() {
		super(owner);
	}
	
	public BasicIncomingPcRequest reply(byte[][] data){
		return new BasicIncomingPcRequest(
				new RequestStruct(owner, RequestMode.RESPONSE, this, this.getSender(), nick, this.subject, data));
	}
	
	
	public BasicIncomingPcRequest reply(String new_subject, byte [][] data){
		return new BasicIncomingPcRequest(
				new RequestStruct(owner, RequestMode.RESPONSE, this, this.getSender(), nick, new_subject, data));
	}

	public static void registerRequest(BasicIncomingPcRequest dummy){
		IncomingRequest.registerRequest(dummy);
	}
}
