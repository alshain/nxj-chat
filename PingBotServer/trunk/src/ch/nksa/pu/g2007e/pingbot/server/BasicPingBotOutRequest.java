package ch.nksa.pu.g2007e.pingbot.server;

import ch.nksa.pu.g2007e.pingbot.enums.PingBotStatus;
import ch.nksa.pu.robotics.libs.RequestStruct;
import ch.nksa.pu.robotics.libs.Util;
import ch.nksa.pu.robotics.libs.pc.BasicOutgoingPcRequest;
import ch.nksa.pu.robotics.libs.pc.Slave;

public class BasicPingBotOutRequest extends BasicOutgoingPcRequest {
	protected BasicPingBotOutRequest(RequestStruct req) {
		super(req);
	}
	
	public static BasicPingBotOutRequest searchLight(Slave slave, boolean on){
		byte[][] data = new byte[1][1];
		data[0][0] =  (byte) (on?1:0);
		BasicPingBotOutRequest r = GetSimple(slave, "SearchLight", data);
		r.send();
		return r;
	}
	
	public static BasicPingBotOutRequest setStatus(Slave slave, PingBotStatus status){
		byte[][] data = new byte[1][];
		data[0] = Util.stringToBytes(status.toString());
		BasicPingBotOutRequest r = GetSimple(slave, "SetStatus", data);
		r.send();
		return r;
	}
	
	public static BasicPingBotOutRequest getDistance(Slave slave){
		byte[][] data = {{0}};
		BasicPingBotOutRequest r = GetSimple(slave, "GetDistance", data);
		r.send();
		return r;
	}
	
	public static BasicPingBotOutRequest findLight(Slave slave){
		byte[][] data = {{0}};
		BasicPingBotOutRequest r = GetSimple(slave, "FindLight", data);
		r.send();
		return r;
	}
	
	public static BasicPingBotOutRequest GetSimple(Slave slave, String subject, byte[][] data){
		return new BasicPingBotOutRequest(
				new RequestStruct(slave, "pingbot.basic", subject, data)
			);
	}
}
