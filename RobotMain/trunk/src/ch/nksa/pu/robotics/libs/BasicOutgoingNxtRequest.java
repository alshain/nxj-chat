package ch.nksa.pu.robotics.libs;

public class BasicOutgoingNxtRequest extends OutgoingRequest {
	protected static Uplink owner = Uplink.getInstance();
	
	public BasicOutgoingNxtRequest(RequestStruct req) {
		super(req);
		// TODO Auto-generated constructor stub
	}

	public BasicOutgoingNxtRequest followUp(byte[][] data){
		return new BasicOutgoingNxtRequest(
				new RequestStruct(owner, RequestMode.FOLLOW_UP, this, getSender(), nick, subject, data)
			);
	}
	
	public BasicOutgoingNxtRequest followUp(String new_subject, byte[][] data){
		return new BasicOutgoingNxtRequest(
				new RequestStruct(owner, RequestMode.FOLLOW_UP, this, getSender(), nick, new_subject, data)
			);
	}
}
