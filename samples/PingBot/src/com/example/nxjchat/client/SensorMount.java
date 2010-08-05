package com.example.nxjchat.client;

import ch.nksa.pu.robotics.libs.Util;
import lejos.nxt.*;

public class SensorMount {
	public Motor positionLight = Motor.B;
	public LightSensor light = new LightSensor(SensorPort.S1);
	public UltrasonicSensor uSonic = new UltrasonicSensor(SensorPort.S2);
	private static SensorMount instance;
	
	/**
	 * For clarification, see
	 * http://tinyurl.com/yfhaskv
	 */
	protected long lastPing = 0;
	protected static int sonicDelay = 40; 
	
	public static SensorMount getInstance(){
		if(instance == null){
			instance = new SensorMount();
		}
		return instance;
	}
	
	private SensorMount(){
		light.setFloodlight(false);
		uSonic.ping();
	}
	
	public int getDistance(){
		if(System.currentTimeMillis() < (lastPing + sonicDelay)){
			try {
				Thread.sleep(sonicDelay);
			} catch (InterruptedException e) {}
		}
		uSonic.ping();
		Util.sleep(400);
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
