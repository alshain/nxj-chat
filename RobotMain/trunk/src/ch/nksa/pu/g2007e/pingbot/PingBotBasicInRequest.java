package ch.nksa.pu.g2007e.pingbot;

import ch.nksa.pu.robotics.libs.IncomingRequest;
import ch.nksa.pu.robotics.libs.RequestStruct;

public class PingBotBasicInRequest extends IncomingRequest {
	
	
	public PingBotBasicInRequest(RequestStruct req) {
		super(req);
	}

	public PingBotBasicInRequest validate(RequestStruct req){
		if(req.sender == "pingbot.basic"){
			System.out.println(req.subject);
			return new PingBotBasicInRequest(req);
		}
		
		return null;
		
	}
}
