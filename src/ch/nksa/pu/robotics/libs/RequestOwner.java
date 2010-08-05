package ch.nksa.pu.robotics.libs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class RequestOwner {
	protected InputStream dis;
	protected OutputStream dos;
	
	protected Thread receivingThread;
	protected Thread sendingThread;
	protected static Uplink instance;
	protected volatile ArrayList<IncomingRequest> listeners = new ArrayList<IncomingRequest>();
	protected volatile ArrayList<byte[][]> rawIncoming = new ArrayList<byte[][]>();
	protected volatile ArrayList<IncomingRequest> incomingRequests = new ArrayList<IncomingRequest>();
	protected volatile ArrayList<OutgoingRequest> outgoingRequests = new ArrayList<OutgoingRequest>();
	protected int requestsSent = 0;
	
	protected void initializeThreads(){
		sendingThread = new Thread(){
			public void run(){
				sendRequests();
			}
		};
		sendingThread.start();
		
		receivingThread = new Thread(){
			public void run(){
				getRequests();
			}
		};
		receivingThread.start();
	}
	
	public synchronized void registerRequest(OutgoingRequest req){
		OutgoingRequest req_ = (OutgoingRequest) req;
		req_.id = outgoingRequests.size();
		outgoingRequests.add(req_);
	}
	
	public void registerListener(IncomingRequest l){
		synchronized(listeners){
			if(!listenerIsRegistered(l)){
				Util.log("Adding listener to Uplink.");
				listeners.add(l);
				Util.log("Active listeners: " + listeners.size());
			}
		}
	}
	
	public synchronized boolean listenerIsRegistered(IncomingRequest dummy){
		return listeners.contains(dummy);
	} 
	
	public OutgoingRequest getOutgoingRequest(int id){
		if(outgoingRequests.size() > id){
			return outgoingRequests.get(id);
		}
		return null;
	}
	
	public IncomingRequest getIncomingRequest(int id){
		if(incomingRequests.size() > id){
			return incomingRequests.get(id);
		}
		return null;
	}
	
	protected void sendRequests(){
		while(true){
			if(outgoingRequests.size() > requestsSent){
				OutgoingRequest req = outgoingRequests.get(requestsSent);
				try {
					byte[][] header;
					header = req.getHeader();
					byte[][] data;
					data = req.getData();
					dos.write(Util.intToBytes(header.length + data.length));
					for(byte[] b: header){
						dos.write(Util.intToBytes(b.length));
						dos.write(b);
					}
					
					for(byte[] b: data){
						dos.write(Util.intToBytes(b.length));
						dos.write(b);
					}
					dos.flush();
					req.hasBeenSent = true;
					requestsSent ++;
				} catch (IOException e) {}
				
			}
			else{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	
	/**
	 * must NOT be invoked manually!
	 */
	protected void getRequests(){
		Util.log("Listening...");
		int length = 0;
		int lines = 0;
		byte[] int_b = new byte[4];
		RequestStruct requestStruct;
		receiving:
		while(true){
			try {
				String message = "Waiting for request ("+incomingRequests.size()+ ")...";
				Util.log(message);
				dis.read(int_b, 0, 4);
				lines = Util.bytesToInt(int_b);
				Util.log("Expecting " + lines + " lines.");
				byte[][] incoming = new byte[lines][];
				for(int i = 0; i < lines; i++){
					dis.read(int_b, 0, 4);
					length = Util.bytesToInt(int_b);
					incoming[i] = new byte[length];
					dis.read(incoming[i], 0, length);
				}
				
				if(!IncomingRequest.headerIsValid(incoming)){
					//Add bogus object in order to keep index and id in sync
					incomingRequests.add(null);
					Util.log(">Unexpected behaviour may follow!");
					continue;
				}
				Util.log("Synchronizing...");
				synchronized (listeners) {
					Util.log("Synchronized.");
					requestStruct = new RequestStruct(this, incoming);
					Util.log("Passing request " + requestStruct.sender + " to " + listeners.size() + " listeners.");
					for(IncomingRequest l: listeners){
						IncomingRequest req = l.validate(this, requestStruct);
						if(req != null){
							incomingRequests.add(req);
							req.notifyReferenceWaitingMonitor();
							continue receiving;
						}
					}
				}
				Util.log("No listener has been found. Passing to IncomingRequest.");
				IncomingRequest req =  new IncomingRequest(requestStruct);
				incomingRequests.add(req);
			} catch (IOException e){
				Util.log("Bluetooth has been terminated unexpectedly.");
				break;
			}
		}
	}
}
