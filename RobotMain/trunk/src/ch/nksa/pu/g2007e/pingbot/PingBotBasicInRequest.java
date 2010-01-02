package ch.nksa.pu.g2007e.pingbot;

import ch.nksa.pu.robotics.libs.IncomingRequest;
import ch.nksa.pu.robotics.libs.RequestStruct;

public class PingBotBasicInRequest extends IncomingRequest {
	
	
	public PingBotBasicInRequest(RequestStruct req) {
		super(req);
	}

	protected static PingBotBasicInRequest validate(RequestStruct req){
		System.out.println(req.sender);
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		if(req.sender == "pingbot.basic"){
			System.out.println(req.subject);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			return new PingBotBasicInRequest(req);
		}
		return null;
	}
}
