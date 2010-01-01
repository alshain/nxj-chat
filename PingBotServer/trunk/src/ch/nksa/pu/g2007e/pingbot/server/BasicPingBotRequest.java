package ch.nksa.pu.g2007e.pingbot.server;

import ch.nksa.pu.robotics.libs.pc.OutgoingRequest;
import ch.nksa.pu.robotics.libs.pc.Slave;

public class BasicPingBotRequest extends OutgoingRequest {

	protected BasicPingBotRequest(Slave owner, String subject, byte[][] data) {
		super(owner, subject, data);
		// TODO Auto-generated constructor stub
	}
	
	public static BasicPingBotRequest searchLight(Slave slave, boolean on){
		byte[][] data = new byte[1][1];
		data[0][0] = (byte) (on?1:0);
		return new BasicPingBotRequest(slave, "SearchLight", data);
	}
	
	public static BasicPingBotRequest setPassive(){
		
	}

}
