package ch.nksa.pu.robotics.libs;


public class IncomingRequestHelper{
	protected Thread foreignThread;
	protected Thread knownThread;
	protected boolean tryToParse = false;
	public IncomingRequestHelper(){
		super();
	}
	
	protected void assignThread(Thread t){
		foreignThread = t;
		foreignThread.start();
	}
	
	protected Thread makeActive(){
		knownThread = new Thread(){
			public void run(){
				tryToParse = true;
				while(true){
					try {
						//foreignThread will interrupt this thread when handling is done.
						Thread.sleep(10);
					} catch (InterruptedException e) {}
				}
			}
		};
		knownThread.start();
		
		return knownThread;
	}
	
}