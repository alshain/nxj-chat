package ch.nksa.pu.robotics.libs;

import lejos.nxt.Sound;

public class IncomingRequest extends Request{
	protected IncomingRequest(RequestOwner owner){
		super(owner);
	}

	public IncomingRequest(RequestStruct requestStruct) {
		super(requestStruct);
	}

	protected static void registerRequest(IncomingRequest dummy){
		Util.log("Registering Listener.");
		dummy.owner.registerListener(dummy);
	}
	
	protected IncomingRequest validate(RequestOwner owner, RequestStruct req){
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