package ch.nksa.pu.g2007e.pingbot;

import ch.nksa.pu.robotics.libs.BasicIncomingPcRequest;
import ch.nksa.pu.robotics.libs.RequestStruct;

public class PingBotBasicInRequest extends BasicIncomingPcRequest {
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
			BasicIncomingPcRequest.registerRequest(dummy);
		}
	}
	
	protected PingBotBasicInRequest validate(RequestStruct req){
		System.out.println("Parsing PingBot");
		if("pingbot.basic".equals(req.sender)){
			PingBotBasicInRequest req_ = new PingBotBasicInRequest(req);
			System.out.println(req.subject);
			if(req.subject.equalsIgnoreCase("SearchLight")){
				SensorMount.getInstance().enablePositionLight(req.data[0][0] == 1?true:false);
			}
			else if("GetDistance".equalsIgnoreCase(req.subject)){
				PingBotBasicOutRequest.sendDistance(this);
			}
			System.out.println("PingBotBasic success.");
			return req_;
		}
		return null;
	}
}
