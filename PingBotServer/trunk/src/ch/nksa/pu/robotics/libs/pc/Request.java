package ch.nksa.pu.robotics.libs.pc;

import ch.nksa.pu.robotics.libs.Util;



public class Request {
	protected Slave owner;
	
	protected int id;
	protected String nick;
	protected String subject;
	protected byte[][] data;
	protected int referenceId = -1;
	protected Request reference = null;
	
	public Request(Slave owner, String subject, byte[][] data){
		this.owner = owner;
		this.subject = subject;
		this.data = data;
	}
	
	public String getNick(){
		return nick;
	}
	
	protected void setNick(String nick) {
		this.nick = nick;
	}
	
	public Request getReference() {
		return reference;
	}
	
	public int getReferenceId(){
		return referenceId;
	}
	
	public Slave getOwner(){
		return owner;
	}
	
	public byte[][] getData(){
		return data;
	}
}
