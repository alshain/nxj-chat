package ch.nksa.pu.robotics.libs;

import lejos.nxt.Sound;

public class IncomingRequest extends Request{
	protected IncomingRequest(RequestOwner owner, int id, RequestMode mode, String sender, String nick,
			String subject, byte[][] data) {
		super(owner, id, mode, sender, nick, subject, data);
	}
	
	public IncomingRequest(RequestOwner owner, byte[][] raw_request){
		super(owner, raw_request);
	}
	
	public IncomingRequest(RequestOwner owner, RequestMode mode, String sender, String nick,
			String subject, byte[][] data) {
		super(owner, mode, sender, nick, subject, data);
	}
	
	public IncomingRequest(RequestOwner owner, RequestStruct req){
		super(owner, req);
	}
	
	protected IncomingRequest(RequestOwner owner){
		super(owner, -1, RequestMode.STATELESS, "", "", "", new byte[0][0]);
	}

	public static void registerRequest(IncomingRequest dummy){
		Util.log("Registering Listener.");
		dummy.owner.registerListener(dummy);
	}
	
	protected IncomingRequest validate(RequestStruct req){
		Util.log("!IncomingRequest.validate.");
		Sound.buzz();
		return null;
	}
	
	protected static boolean headerIsValid(byte[][] raw_request){
		if(raw_request.length < 6){
			Util.log("Request shorter than 6 fields.");
			return false;
		}
		if(raw_request[0].length != 4){
			Util.log("Malformed id.");
			return false;
		}
		
		if(raw_request[1].length == 0){
			Util.log("Malformed mode.");
			return false;
		}
		if(!RequestMode.valueExists(Util.bytesToString(raw_request[1]))){
			Util.log("Mode " + Util.bytesToString(raw_request[1]) + " does not exist.");
			return false;
		}
		
		if(raw_request[2].length != 4){
			Util.log("Malformed reference.");
			return false;
		}
		
		if(raw_request[3].length == 0){
			Util.log("Sender is empty");
			return false;
		}
		
		if(raw_request[4].length == 0){
			Util.log("Nick is empty");
			return false;
		}
		if(raw_request[5].length == 0){
			Util.log("Subject is empty");
			return false;
		}
		return true;	
	}
}