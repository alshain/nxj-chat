package ch.nksa.pu.robotics.libs.pc;

public class IncomingRequest {
	private String type_;
	private String content_;
	private Slave owner_;
	
	public IncomingRequest(Slave owner, int id, String type, String content){
		owner_ = owner;
		type_ = type;
		content_ = content;
		
	}
}
