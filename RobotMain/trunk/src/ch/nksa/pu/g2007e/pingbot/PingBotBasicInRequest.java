package ch.nksa.pu.g2007e.pingbot;

import ch.nksa.pu.robotics.libs.IncomingRequest;
import ch.nksa.pu.robotics.libs.RequestMode;
import ch.nksa.pu.robotics.libs.RequestStruct;

public class PingBotBasicInRequest extends IncomingRequest {
	protected static PingBotBasicInRequest dummy;
	
	protected PingBotBasicInRequest(RequestStruct req) {
		super(req);
	}
	protected PingBotBasicInRequest(){
		super();
	}
	
	public static void registerRequest(){
		if(dummy == null){
			dummy = new PingBotBasicInRequest();
			IncomingRequest.registerRequest(dummy);
		}
	}
	
	protected PingBotBasicInRequest validate(RequestStruct req){
		System.out.println("Parsing PingBot");
		if("pingbot.basic".equals(req.sender)){
			System.out.println(req.subject);
			if(req.subject.equalsIgnoreCase("SearchLight")){
				SensorMount.getInstance().enablePositionLight(req.data[0][0] == 1?true:false);
			}
			System.out.println("PingBotBasic success.");
			return new PingBotBasicInRequest(req);
		}
		return null;
	}
}
