package ch.nksa.pu.robotics.libs;

import ch.nksa.pu.robotics.libs.RequestStruct;

public class Request {
	protected int id;
	protected RequestMode mode;
	protected String sender;
	protected String nick;
	protected String subject;
	protected byte[][] data;
	protected Request reference = null;
	
	public enum RequestMode{
		STATELESS,
		RESPONSE,
		FOLLOW_UP,
		BROADCAST;
	}
	
	public Request(String sender, String nick,
			String subject, byte[][] data) {
		this.mode = RequestMode.STATELESS;
		this.sender = sender;
		this.nick = nick;
		this.subject = subject;
		this.data = data;
	}
	
	public Request(RequestMode mode, String sender, String nick,
			String subject, byte[][] data) {
		this.mode = mode;
		this.sender = sender;
		this.nick = nick;
		this.subject = subject;
		this.data = data;
	}
	
	public Request(int id, RequestMode mode, String sender, String nick,
			String subject, byte[][] data) {
		this.id = id;
		this.mode = mode;
		this.sender = sender;
		this.nick = nick;
		this.subject = subject;
		this.data = data;
	}
	
	public Request(RequestStruct req){
		readFromStruct(req);
	}
	
	public Request(byte[][] raw_request){
		RequestStruct req = new RequestStruct(raw_request);
		readFromStruct(req);
	}

	private void readFromStruct(RequestStruct req){
		id = req.id;
		mode = req.mode;
		reference = req.reference;
		sender = req.sender;
		nick = req.nick;
		subject = req.subject;
		data = req.data;
	}
	
	public int getReferenceId(){
		if(reference == null){
			return -1;
		}
		return reference.id;
	}
	
	public byte[][] getData(){
		return data;
	}
	
	public RequestMode getMode(){
		return mode;
	}
}
