package ch.nksa.pu.robotics.libs;

public class BasicIncomingRequest extends IncomingRequest {
	public BasicIncomingRequest(int id, RequestMode mode, String sender, String nick, String subject, byte[][] data){
		super(id, mode, sender, nick, subject, data);
	}
	
	public BasicIncomingRequest(byte[][] raw_request){
		super(raw_request);
	}
	
	public static BasicIncomingRequest validate(byte[][] raw_request){
		System.out.println("Basic Request received, parsing.");
		if(!IncomingRequest.headerIsValid(raw_request)){
			return null;
		}
		return new BasicIncomingRequest(raw_request);
	}
}
