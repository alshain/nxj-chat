package ch.nksa.pu.robotics.libs;

public class RequestStruct {
	public int id = -1;
	public RequestMode mode = RequestMode.STATELESS;
	public String sender = "";
	public String nick = "";
	public String subject = "";
	public byte[][] data = {{0}};
	public Request reference = null;
	public RequestOwner owner; 
	
	public RequestStruct(RequestOwner owner){
		this.owner = owner;
	}
	
	public RequestStruct(Request ref, RequestMode mode){
		this.mode = mode;
		owner = ref.owner;
		sender = ref.sender;
		nick = ref.nick;
		subject = ref.subject;
		reference = ref; 
	}
	
	public RequestStruct(RequestOwner owner, RequestMode mode,
			Request req, String sender,
			String nick, String subject, byte[][] data) {
		this.owner = owner;
		this.mode = mode;
		this.reference = req;
		this.sender = sender;
		this.nick = nick;
		this.subject = subject;
		this.data = data;
	}

	public RequestStruct(RequestOwner owner, byte[][] raw_request){
		this.owner = owner;
		id = Util.bytesToInt(raw_request[0]);
		
		String _mode = Util.bytesToString(raw_request[1]);
		mode = RequestMode.getFromString(_mode);
		
		if(RequestMode.FOLLOW_UP.equals(mode)){
			reference = owner.getIncomingRequest(Util.bytesToInt(raw_request[2]));
		}
		else if(RequestMode.RESPONSE.equals(mode)){
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

	public RequestStruct(RequestOwner owner, String sender, String subject,
			byte[][] data) {
		this(owner, RequestMode.STATELESS, null, sender, "default", subject, data);
	}

	public static RequestStruct asReply(Request ref){
		return new RequestStruct(ref, RequestMode.RESPONSE);
	}
	
	public static RequestStruct asFollowUp(Request ref){
		return new RequestStruct(ref, RequestMode.FOLLOW_UP);
	}
}
