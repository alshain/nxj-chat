package ch.nksa.pu.robotics.libs.pc;

import java.util.Vector;

public class Broadcast{
	protected MasterMind mind;
	protected String subject;
	protected byte[][] data;
	protected Vector<BasicOutgoingPcRequest> requests = new Vector<BasicOutgoingPcRequest>();
	
	public Broadcast(MasterMind mind, String subject, byte[][] data) {
		this.mind = mind;
		for(Slave s: mind.connectedNxts){
			//s.sendRequest(type, content)
		}
	}
	
	public MasterMind getMind(){
		return mind;
	}
	

}
