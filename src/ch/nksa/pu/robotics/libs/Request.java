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
	
	public Request(RequestStruct req){
		readFromStruct(req);
	}
	
	protected Request(RequestOwner owner) {
		this.owner = owner;
	}

	private void readFromStruct(RequestStruct req){
		owner = req.owner;
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
		if(reference != null){
			reference.notifyWaitingMonitor();
		}
	}
	
	protected void notifyWaitingMonitor(){
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
