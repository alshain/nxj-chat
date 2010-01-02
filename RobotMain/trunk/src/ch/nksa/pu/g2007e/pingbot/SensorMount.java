package ch.nksa.pu.g2007e.pingbot;

import lejos.nxt.*;

public class SensorMount {
	public Motor positionLight = Motor.B;
	public LightSensor light = new LightSensor(SensorPort.S1);
	public UltrasonicSensor uSonic = new UltrasonicSensor(SensorPort.S2);
	
	protected long lastPing = 0;
	protected static int sonicDelay = 500; 
	
	
	public SensorMount(){
		light.setFloodlight(false);
	}
	
	public int getDistance() throws Exception{
		if(System.currentTimeMillis() < (lastPing + sonicDelay)){
			Thread.sleep(sonicDelay);
		}
		lastPing = System.currentTimeMillis();
		return uSonic.getDistance();
	}
	
	public void enablePositionLight(){
		enablePositionLight(true);
	}
	
	public void enablePositionLight(boolean enable){
		if(enable){
			positionLight.setPower(100);
			positionLight.forward();
		}
		else{
			positionLight.stop();
		}
		
	}
	
	public void followTheLight() throws Exception{
		boolean found = false;
		int newIntensity;
		int epsilon = 2;
	}
}
