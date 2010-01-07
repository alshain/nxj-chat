package ch.nksa.pu.robotics.libs;

import ch.nksa.pu.robotics.libs.RequestStruct;

public class Request {
	protected RequestOwner owner;
	protected int id;
	protected RequestMode mode;
	protected String sender;
	protected String nick;
	protected String subject;
	protected byte[][] data;
	protected Request reference = null;
	protected Object waitingMonitor = new Object();
	
	public Request(RequestOwner owner, RequestStruct req){
		this.owner = owner;
		readFromStruct(req);
	}
	
	public Request(RequestOwner owner, String sender, String nick,
			String subject, byte[][] data) {
		this.owner = owner;
		this.mode = RequestMode.STATELESS;
		this.sender = sender;
		this.nick = nick;
		this.subject = subject;
		this.data = data;
	}
	
	public Request(RequestOwner owner, RequestMode mode,  Request reference, String sender, String nick,
			String subject, byte[][] data) {
		this.owner = owner;
		this.mode = mode;
		this.sender = sender;
		this.nick = nick;
		this.subject = subject;
		this.data = data;
		this.reference = reference;
	}
	
	public Request(RequestOwner owner, int id, RequestMode mode, String sender, String nick,
			String subject, byte[][] data) {
		this.owner = owner;
		this.id = id;
		this.mode = mode;
		this.sender = sender;
		this.nick = nick;
		this.subject = subject;
		this.data = data;
	}
	/*
	public Request(RequestOwner owner, byte[][] raw_request){
		RequestStruct req = new RequestStruct(raw_request, owner);
		this.owner = owner;
		readFromStruct(req);
	}*/

	private void readFromStruct(RequestStruct req){
		id = req.id;
		mode = req.mode;
		reference = req.reference;
		this.sender = req.sender;
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
	
	public int getId(){
		return id;
	}

	public RequestMode getMode(){
		return mode;
	}

	public String getSender() {
		return sender;
	}

	public String getNick() {
		return nick;
	}

	public String getSubject() {
		return subject;
	}

	public byte[][] getData(){
		return data;
	}
	
	protected void notifyReferenceWaitingMonitor(){
		Util.log("notfiyBeforeNull");
		if(reference != null){
			Util.log("notfiyAfterNull");
			reference.notifyWaitingMonitor();
		}
	}
	
	protected void notifyWaitingMonitor(){
		Util.log("notifying");
		synchronized (waitingMonitor) {
			waitingMonitor.notifyAll();
		}
	}
	
	protected void waitForMonitor() throws InterruptedException{
		synchronized (waitingMonitor) {
			waitingMonitor.wait();
		}
	}
}
