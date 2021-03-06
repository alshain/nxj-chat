package ch.nksa.pu.robotics.libs.pc;

import java.util.ArrayList;
import ch.nksa.pu.robotics.libs.BasicIncomingPcRequest;
import ch.nksa.pu.robotics.libs.IncomingRequest;
import ch.nksa.pu.robotics.libs.RequestMode;
import ch.nksa.pu.robotics.libs.RequestStruct;

public class BasicIncomingNxtRequest extends IncomingRequest {
	public BasicIncomingNxtRequest(RequestStruct req) {
		super(req);
	}

	protected BasicIncomingNxtRequest(Slave owner) {
		super(owner);
	}
	
	protected static boolean listenerExists(ArrayList<BasicIncomingNxtRequest> list, Slave slave){
		for(BasicIncomingNxtRequest r: list){
			if(slave == r.owner){
				return true;
			}
		}
		return false;
	}
	
	public BasicOutgoingPcRequest reply(String new_subject, byte [][] data){
		return new BasicOutgoingPcRequest(
				new RequestStruct(owner, RequestMode.FOLLOW_UP, this, getSender(), nick, new_subject, data)
			);
	}
	
	public BasicIncomingNxtRequest validate(Slave owner, byte[][] raw_request){
		System.out.println("Basic Request received, parsing.");
		return new BasicIncomingNxtRequest(new RequestStruct(owner, raw_request));
	}
	
	protected static void registerRequest(BasicIncomingPcRequest dummy){
		IncomingRequest.registerRequest(dummy);
	}
}
