package com.example.nxjchat.client;

import ch.nksa.pu.robotics.libs.BasicOutgoingNxtRequest;
import ch.nksa.pu.robotics.libs.RequestStruct;
import ch.nksa.pu.robotics.libs.Util;

public class PingBotBasicOutRequest extends BasicOutgoingNxtRequest {
	public PingBotBasicOutRequest(RequestStruct req) {
		super(req);
		// TODO Auto-generated constructor stub
	}

	public static PingBotBasicOutRequest sendDistance(PingBotBasicInRequest ref){
		byte[][] data = new byte[1][];
		data[0] = Util.intToBytes(SensorMount.getInstance().getDistance());
		RequestStruct struct = RequestStruct.asReply(ref);
		struct.data = data;
		return new PingBotBasicOutRequest(struct);
	}
}
