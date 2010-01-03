package ch.nksa.pu.robotics.libs;


public class BasicIncomingPcRequest extends IncomingRequest {
	//!warning: hides non-static owner of super! 
	protected static Uplink owner = Uplink.getInstance();
	public BasicIncomingPcRequest(int id, RequestMode mode, String sender,
			String nick, String subject, byte[][] data) {
		super(owner, id, mode, sender, nick, subject, data);
	}
	
	public BasicIncomingPcRequest(RequestMode response, String sender, 
			String subject, byte[][] data) {
		super(owner, response, sender, sender, subject, data);
	}

	public BasicIncomingPcRequest(byte[][] rawRequest) {
		super(owner, rawRequest);
	}

	public BasicIncomingPcRequest(RequestStruct req) {
		super(owner, req);
	}
	
	public BasicIncomingPcRequest() {
		super(owner);
	}
	
	public BasicIncomingPcRequest reply(byte[][] data){
		return new BasicIncomingPcRequest(RequestMode.RESPONSE, this.getSender(), this.subject, data);
	}
	
	
	public BasicIncomingPcRequest reply(String new_subject, byte [][] data){
		return new BasicIncomingPcRequest(RequestMode.RESPONSE, this.getSender(), new_subject, data);
	}

	public static BasicIncomingPcRequest validate(byte[][] raw_request){
		//System.out.println("Basic Request received, parsing.");
		if(!IncomingRequest.headerIsValid(raw_request)){
			return null;
		}
		return new BasicIncomingPcRequest(raw_request);
	}
	
	public static void registerRequest(BasicIncomingPcRequest dummy){
		IncomingRequest.registerRequest(dummy);
	}
}
