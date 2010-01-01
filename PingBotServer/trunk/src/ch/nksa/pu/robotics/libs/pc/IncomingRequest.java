import ch.nksa.pu.robotics.libs.pc.IncomingRequest;
import ch.nksa.pu.robotics.libs.pc.IncomingRequestHelper;
import ch.nksa.pu.robotics.libs.pc.Request;

public class IncomingRequest extends Request {
	public static boolean isRegistered = false;
	public static IncomingRequestHelper helper;
	
	public enum RequestMode{
		STATELESS,
		RESPONSE,
		FOLLOW_UP,
		BROADCAST;
	}
	
	
	public static void registerRequest(final Uplink uplink){
		if(!isRegistered){
			helper = new IncomingRequestHelper();
			Thread listener = new Thread(){
				public void run(){
					if(helper.tryToParse){
						validate(uplink.latestRawRequest);
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
	
	static IncomingRequest validate(byte[][] raw_request){
		return null;
	}
}