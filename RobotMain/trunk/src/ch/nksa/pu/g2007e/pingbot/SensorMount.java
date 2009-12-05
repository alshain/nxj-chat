package ch.nksa.pu.g2007e.pingbot;

import lejos.nxt.*;

public class SensorMount {
	public Motor motor = Motor.A;
	public TouchSensor touch = new TouchSensor(SensorPort.S3);
	public LightSensor light = new LightSensor(SensorPort.S1);
	public UltrasonicSensor uSonic = new UltrasonicSensor(SensorPort.S2);
	
	protected long lastPing = 0;
	protected static int sonicDelay = 500; 
	
	
	public SensorMount(){
		motor.setPower(800);
		resetPosition();
	}
	
	public int getDistance() throws Exception{
		if(System.currentTimeMillis() < (lastPing + sonicDelay)){
			Thread.sleep(sonicDelay);
		}
		lastPing = System.currentTimeMillis();
		return uSonic.getDistance();
	}
	
	public void hiphop(){
		while(true){
			motor.backward();
			while(true && !Button.ESCAPE.isPressed()){
		    	if(touch.isPressed()){
		    		break;
		    	}
		    }
			motor.reverseDirection();
		}
	}
	
	public void resetPosition(){
		if(touch.isPressed()) return;
		
		motor.backward();
		while(true){
	    	if(touch.isPressed()){
	    		motor.stop();
	    		break;
	    	}
	    }
	}
	
	public void followTheLight() throws Exception{
		resetPosition();
		boolean found = false;
		int newIntensity;
		int epsilon = 2;
		motor.forward();
		while(!found && motor.isMoving()){
			Thread.sleep(10);
			newIntensity = light.getLightValue();
		}
	}
}
