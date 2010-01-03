package ch.nksa.pu.g2007e.pingbot;

import ch.nksa.pu.robotics.libs.BaseUnit;
import ch.nksa.pu.robotics.libs.BasicIncomingPcRequest;
import ch.nksa.pu.robotics.libs.BasicOutgoingNxtRequest;
import ch.nksa.pu.robotics.libs.Request;
import ch.nksa.pu.robotics.libs.RequestMode;
import ch.nksa.pu.robotics.libs.Util;

public class PingBotBasicOutRequest extends BasicOutgoingNxtRequest {

	protected PingBotBasicOutRequest(String sender, String nick, String subject,
			byte[][] data) {
		super(sender, nick, subject, data);
		// TODO Auto-generated constructor stub
	}

	protected PingBotBasicOutRequest(String sender, String subject, byte[][] data) {
		super(sender, subject, data);
		// TODO Auto-generated constructor stub
	}

	protected PingBotBasicOutRequest(RequestMode mode, Request reference,
			String sender, String nick, String subject, byte[][] data) {
		super(mode, reference, sender, nick, subject, data);
		// TODO Auto-generated constructor stub
	}
	
	protected static PingBotBasicOutRequest asReply(BasicIncomingPcRequest reference, byte[][] data){
		return new PingBotBasicOutRequest(RequestMode.RESPONSE, reference, 
				reference.getSender(), reference.getNick(), reference.getSubject(), data);
	}
	
	public static PingBotBasicOutRequest sendDistance(PingBotBasicInRequest ref){
		byte[][] data = new byte[1][];
		data[0] = Util.intToBytes(SensorMount.getInstance().getDistance());
		return PingBotBasicOutRequest.asReply(ref, data);
	}
}
