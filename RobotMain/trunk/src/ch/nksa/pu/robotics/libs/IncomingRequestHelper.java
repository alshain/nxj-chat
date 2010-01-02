package ch.nksa.pu.robotics.libs;


public class IncomingRequestHelper{
	protected Thread foreignThread;
	protected volatile boolean last = false;
	protected volatile boolean tryToParse = false;
	protected Thread knownThread;
	public IncomingRequestHelper(){
		super();
	}
	
	//TODO: check whether we need this.
	protected void assignThread(Thread t){
		foreignThread = t;
	}
	
	protected Thread makeActive(){
		knownThread = new Thread(){
			public void run(){
				tryToParse = true;
				while(true){
					try {
						//foreignThread will interrupt this thread when handling is done.
						Thread.sleep(10);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		};
		knownThread.start();
		
		return knownThread;
	}
	
}