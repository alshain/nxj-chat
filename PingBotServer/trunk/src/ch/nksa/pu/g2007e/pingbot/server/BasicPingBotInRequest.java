package ch.nksa.pu.g2007e.pingbot.server;

import ch.nksa.pu.robotics.libs.RequestMode;
import ch.nksa.pu.robotics.libs.RequestOwner;
import ch.nksa.pu.robotics.libs.RequestStruct;
import ch.nksa.pu.robotics.libs.pc.BasicIncomingNxtRequest;
import ch.nksa.pu.robotics.libs.pc.Slave;

public class BasicPingBotInRequest extends BasicIncomingNxtRequest {

	public BasicPingBotInRequest(Slave owner, int id, RequestMode mode,
			String sender, String nick, String subject, byte[][] data) {
		super(owner, id, mode, sender, nick, subject, data);
		// TODO Auto-generated constructor stub
	}

	public BasicPingBotInRequest(RequestOwner owner, RequestMode response,
			String sender, String subject, byte[][] data) {
		super(owner, response, sender, subject, data);
		// TODO Auto-generated constructor stub
	}

	public BasicPingBotInRequest(Slave owner, byte[][] rawRequest) {
		super(owner, rawRequest);
		// TODO Auto-generated constructor stub
	}

	public BasicPingBotInRequest(Slave owner, RequestStruct req) {
		super(owner, req);
		// TODO Auto-generated constructor stub
	}
	
	

}
