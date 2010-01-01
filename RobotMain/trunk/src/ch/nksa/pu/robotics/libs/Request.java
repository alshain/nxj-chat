package ch.nksa.pu.robotics.libs;

public class Request {
	protected int id;
	protected String sender;
	protected String nick;
	protected String subject;
	
	protected byte[][] header;
	protected byte[][] data;
	protected Request reference = null;
	protected Uplink uplink = null;
}
