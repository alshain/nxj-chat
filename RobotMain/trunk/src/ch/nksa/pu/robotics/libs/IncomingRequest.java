package ch.nksa.pu.robotics.libs;

public class IncomingRequest extends Request {
	protected RequestMode mode;
	
	public IncomingRequest(int id, RequestMode mode, String sender, String nick,
			String subject, byte[][] data) {
		super(id, mode, sender, nick, subject, data);
	}
	
	public IncomingRequest(byte[][] raw_request){
		super(raw_request);
	}
	
	public IncomingRequest(RequestStruct req){
		super(req);
	}

	public static boolean isRegistered = false;
	public static IncomingRequestHelper helper;
	
	public static void registerRequest(final Uplink uplink){
		if(!isRegistered){
			helper = new IncomingRequestHelper();
			Thread listener = new Thread(){
				public void run(){
					if(helper.tryToParse){
						IncomingRequest req;
						req = validate(uplink.requestStruct);
						if(req != null){
							helper.last = true;
							uplink.incomingRequests.add(req);
						}
						helper.tryToParse = false;
						helper.knownThread.interrupt();
					}
					else{
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {}
					}
				}
			};
			listener.start();
			uplink.registerListener(helper);
		}
	}
	
	static IncomingRequest validate(RequestStruct req){
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