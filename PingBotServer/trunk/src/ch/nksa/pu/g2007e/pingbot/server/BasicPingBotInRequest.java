package ch.nksa.pu.g2007e.pingbot.server;

import ch.nksa.pu.robotics.libs.Request;
import ch.nksa.pu.robotics.libs.RequestMode;
import ch.nksa.pu.robotics.libs.RequestOwner;
import ch.nksa.pu.robotics.libs.RequestStruct;
import ch.nksa.pu.robotics.libs.Util;
import ch.nksa.pu.robotics.libs.pc.BasicIncomingNxtRequest;
import ch.nksa.pu.robotics.libs.pc.Slave;

public class BasicPingBotInRequest extends BasicIncomingNxtRequest {
	protected static BasicPingBotInRequest dummy;

	public BasicPingBotInRequest(RequestStruct req) {
		super(req);
		// TODO Auto-generated constructor stub
	}
	
	public BasicPingBotInRequest(Slave owner){
		super(owner);
	}
	
	public static void registerListener(Slave slave){
		if(dummy == null){
			dummy = new BasicPingBotInRequest(slave); 
		}
		BasicIncomingNxtRequest.registerRequest(dummy);
	}
	
	public BasicPingBotInRequest validate(RequestOwner owner, RequestStruct req_){
		System.out.println("Parsing PingBot");
		if("pingbot.basic".equals(req_.sender)){
			BasicPingBotInRequest req = new BasicPingBotInRequest(req_);
			System.out.println(req.getSubject());
			if("GetDistance".equalsIgnoreCase(req_.subject)){
				System.out.println("Distance: " + Util.bytesToInt(req_.data[0]));
			}
			System.out.println("PingBotBasic success.");
			return req;
		}
		return null;
	}
}
