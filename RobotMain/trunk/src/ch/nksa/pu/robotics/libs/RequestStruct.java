package ch.nksa.pu.robotics.libs;

public class RequestStruct {
	public int id = -1;
	public RequestMode mode = RequestMode.STATELESS;
	public String sender = "";
	public String nick = "";
	public String subject = "";
	public byte[][] data = {{}};
	public Request reference = null;
	public RequestOwner owner; 
	
	public RequestStruct(RequestOwner owner){
		this.owner = owner;
	}
	
	public RequestStruct(byte[][] raw_request, RequestOwner owner){
		id = Util.bytesToInt(raw_request[0]);
		
		String _mode = Util.bytesToString(raw_request[1]);
		mode = RequestMode.getFromString(_mode);
		
		if(RequestMode.FOLLOW_UP.toString().equals(mode)){
			reference = owner.getIncomingRequest(Util.bytesToInt(raw_request[2]));
		}
		else if(RequestMode.RESPONSE.toString().equals(mode)){
			reference = owner.getOutgoingRequest(Util.bytesToInt(raw_request[2]));
		}
		
		sender = Util.bytesToString(raw_request[3]);
		nick =  Util.bytesToString(raw_request[4]);
		subject =  Util.bytesToString(raw_request[5]);
		
		//copy data
		data = new byte[raw_request.length - 6][];
		for(int i = 6; i < raw_request.length; i++){
			data[i - 6] = new byte[raw_request[i].length];
			for(int k = 0; k < raw_request[i].length; k++){
				data[i - 6][k] = raw_request[i][k];
			}
		}
	}
}
