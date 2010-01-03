package ch.nksa.pu.robotics.libs;

import lejos.nxt.Sound;

public class IncomingRequest extends Request{
	protected IncomingRequest(int id, RequestMode mode, String sender, String nick,
			String subject, byte[][] data) {
		super(id, mode, sender, nick, subject, data);
	}
	
	public IncomingRequest(byte[][] raw_request){
		super(raw_request);
	}
	
	public IncomingRequest(RequestStruct req){
		super(req);
	}
	
	protected IncomingRequest(){
		super(-1, RequestMode.STATELESS, "", "", "", new byte[0][0]);
	}

	public static IncomingRequestHelper helper;
	
	public static void registerRequest(IncomingRequest dummy){
		System.out.println("Registering Listener.");
		Uplink.getInstance().registerListener(dummy);
	}
	
	protected IncomingRequest validate(RequestStruct req){
		System.out.println("!IncomingRequest.validate.");
		Sound.buzz();
		return null;
	}
	
	protected static boolean headerIsValid(byte[][] raw_request){
		if(raw_request.length < 6){
			System.out.println("Request shorter than 6 fields.");
			return false;
		}
		if(raw_request[0].length != 4){
			System.out.println("Malformed id.");
			return false;
		}
		
		if(raw_request[1].length == 0){
			System.out.println("Malformed mode.");
			return false;
		}
		if(!RequestMode.valueExists(Util.bytesToString(raw_request[1]))){
			System.out.println("Mode inexistent.");
			System.out.println("Mode sent: " + Util.bytesToString(raw_request[1]));
			return false;
		}
		
		if(raw_request[2].length != 4){
			System.out.println("Malformed reference.");
			return false;
		}
		
		if(raw_request[3].length == 0){
			System.out.println("Sender is empty");
			return false;
		}
		
		if(raw_request[4].length == 0){
			System.out.println("Nick is empty");
			return false;
		}
		if(raw_request[5].length == 0){
			System.out.println("Subject is empty");
			return false;
		}
		
		return true;	
	}
}