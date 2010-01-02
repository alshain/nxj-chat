package ch.nksa.pu.robotics.libs;

import lejos.nxt.Sound;

public class IncomingRequest extends Request{
	protected IncomingRequest dummy;
	
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

	public static boolean isRegistered = false;
	public static IncomingRequestHelper helper;
	
	public static void registerRequest(){
		System.out.println("Registering Listener.");
		if(!isRegistered){
			System.out.println("Create helper.");
			helper = new IncomingRequestHelper();
			Thread listener = new Thread(){
				public void run(){
					while(true){
						if(helper.tryToParse){
							System.out.println("tryToParse: True");
							IncomingRequest req;
							req = validate(Uplink.getInstance().requestStruct);
							if(req != null){
								helper.last = true;
								Uplink.getInstance().incomingRequests.add(req);
							}
							helper.tryToParse = false;
							System.out.println("Interrupting Thread.");
							helper.knownThread.interrupt();
						}
						else{
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								System.out.println(">Exception: Interrupted.");
							}
						}
					}
				}
			};
			listener.start();
			Uplink.getInstance().registerListener(helper);
			isRegistered = true;
		}
	}
	
	protected static IncomingRequest validate(RequestStruct req){
		System.out.println("!!IncomingRequest.validate.");
		Sound.buzz();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
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