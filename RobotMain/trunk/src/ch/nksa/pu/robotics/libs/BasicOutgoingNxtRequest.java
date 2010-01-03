package ch.nksa.pu.robotics.libs;

public class BasicOutgoingNxtRequest extends OutgoingRequest {
	protected static Uplink owner = Uplink.getInstance();
	protected BasicOutgoingNxtRequest(String sender,
			String nick, String subject, byte[][] data) {
		super(owner, sender, nick, subject, data);
	}

	protected BasicOutgoingNxtRequest(String sender,
			String subject, byte[][] data) {
		super(owner, sender, subject, data);
	}

	protected BasicOutgoingNxtRequest(RequestMode mode, Request reference, 
			String sender, String nick, String subject,
			byte[][] data) {
		super(owner, mode, reference, sender, nick, subject, data);
	}
	
	public BasicOutgoingNxtRequest followUp(byte[][] data){
		return new BasicOutgoingNxtRequest(RequestMode.FOLLOW_UP, this, getSender(), nick, subject, data);
	}
	
	public BasicOutgoingNxtRequest followUp(String new_subject, byte[][] data){
		return new BasicOutgoingNxtRequest(RequestMode.FOLLOW_UP, this, getSender(), nick, new_subject, data);
	}
}
