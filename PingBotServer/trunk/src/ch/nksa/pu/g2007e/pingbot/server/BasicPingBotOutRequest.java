package ch.nksa.pu.g2007e.pingbot.server;

import ch.nksa.pu.g2007e.pingbot.enums.PingBotStatus;
import ch.nksa.pu.robotics.libs.Util;
import ch.nksa.pu.robotics.libs.pc.BasicOutgoingPcRequest;
import ch.nksa.pu.robotics.libs.pc.Slave;

public class BasicPingBotOutRequest extends BasicOutgoingPcRequest {
	protected BasicPingBotOutRequest(Slave owner, String subject, byte[][] data) {
		super(owner, "pingbot.basic", subject, data);
	}
	
	public static BasicPingBotOutRequest searchLight(Slave slave, boolean on){
		byte[][] data = new byte[1][1];
		data[0][0] =  (byte) (on?1:0);
		return new BasicPingBotOutRequest(slave, "SearchLight", data);
	}
	
	public static BasicPingBotOutRequest setStatus(Slave slave, PingBotStatus status){
		byte[][] data = new byte[1][];
		data[0] = Util.stringToBytes(status.toString());
		return new BasicPingBotOutRequest(slave, "SetStatus", data);
	}
}
