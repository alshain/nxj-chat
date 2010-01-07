package ch.nksa.pu.g2007e.pingbot;

import ch.nksa.pu.robotics.libs.BasicIncomingPcRequest;
import ch.nksa.pu.robotics.libs.RequestOwner;
import ch.nksa.pu.robotics.libs.RequestStruct;
import ch.nksa.pu.robotics.libs.Uplink;
import ch.nksa.pu.robotics.libs.Util;

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
		}
		BasicIncomingPcRequest.registerRequest(dummy);
	}
	
	protected PingBotBasicInRequest validate(RequestOwner owner, RequestStruct req){
		if("pingbot.basic".equals(req.sender)){
			PingBotBasicInRequest req_ = new PingBotBasicInRequest(req);
			if(req.subject.equalsIgnoreCase("SearchLight")){
				SensorMount.getInstance().enablePositionLight(req.data[0][0] == 1?true:false);
			}
			else if("GetDistance".equalsIgnoreCase(req.subject)){
				Util.log("ID: "+req.id);
				PingBotBasicOutRequest.sendDistance(req_);
			}
			else if("FindLight".equalsIgnoreCase(req.subject)){
				try {
					PingBot.searchTheLight();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
			}
			return req_;
		}
		return null;
	}
}
