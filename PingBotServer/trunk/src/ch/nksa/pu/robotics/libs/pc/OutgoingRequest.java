package ch.nksa.pu.robotics.libs.pc;

public class OutgoingRequest {
	protected boolean hasBeenSent = false;
	protected Thread sentTest;
	private String type_;
	private String content_;
	private Slave owner_;
	
	public OutgoingRequest(Slave owner, int id, String type, String content){
		owner_ = owner;
		type_ = type;
		content_ = content;
		
		sentTest = new Thread(){
			public void run(){
				while(!isSent()){}
				System.out.println("Sent");
			}
		};
		sentTest.start();
	}
	
	protected Thread getSendCheckThread(){
		return sentTest;
	}
	
	public boolean isSent(){
		return hasBeenSent;
	}
	
	public String getContent(){
		return content_;
	}
	
	public String getType(){
		return type_;
	}
	
	public Slave getOwner(){
		return owner_;
	}
}
