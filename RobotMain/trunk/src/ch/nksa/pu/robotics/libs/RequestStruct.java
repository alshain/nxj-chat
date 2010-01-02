package ch.nksa.pu.robotics.libs;

import ch.nksa.pu.robotics.libs.Request.RequestMode;

public class RequestStruct {
	public int id;
	public RequestMode mode;
	public String sender;
	public String nick;
	public String subject;
	public byte[][] data;
	public Request reference = null;
	
	
	public RequestStruct(byte[][] raw_request){
		id = Util.bytesToInt(raw_request[0]);
		
		String _mode = Util.bytesToString(raw_request[1]);
		mode = RequestMode.valueOf(_mode);
		if(mode == RequestMode.FOLLOW_UP){
			reference = Uplink.getInstance().getOutgoingRequest(Util.bytesToInt(raw_request[2]));
		}
		else if(mode == RequestMode.RESPONSE){
			reference = Uplink.getInstance().getIncomingRequest(Util.bytesToInt(raw_request[2]));
		}
		
		sender = Util.bytesToString(raw_request[3]);
		nick =  Util.bytesToString(raw_request[4]);
		subject =  Util.bytesToString(raw_request[5]);
		
		//copy data
		byte[][] data = new byte[raw_request.length - 6][];
		for(int i = 6; i < raw_request.length; i++){
			data[i - 6] = raw_request[i];
		}
	}
}
