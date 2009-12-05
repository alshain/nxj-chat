/**
 * This unit is intended for testing purposes only.
 */
package ch.nksa.pu.g2007e.pingbot;

import ch.nksa.pu.robotics.libs.BaseUnit;
import ch.nksa.pu.robotics.libs.BaseUnit.Orientation;
import ch.nksa.pu.g2007e.pingbot.SensorMount;
import lejos.nxt.*;
import lejos.nxt.comm.*;

public class PlayGound {
	public static SensorMount sMount;// = new SensorMount();
	public static NxtCommunication comm = new NxtCommunication();
	public static BaseUnit base = new BaseUnit();
	
	public static void main( String[] args) throws Exception{
		base.setOrientation(Orientation.BACKWARD);
		base.forward();
		Thread.sleep(5000);
		//printBluetoothAddress();
	}
	
	public static void printDistance() throws Exception{
		while(!Button.ESCAPE.isPressed()){
			LCD.drawString(String.valueOf(sMount.getDistance()), 0, 0);
			LCD.refresh();
		}
	}
	
	public static void printBluetoothAddress() throws Exception{
		while(!Button.ESCAPE.isPressed()){
			LCD.drawString(Bluetooth.getLocalAddress(), 0, 0);
			LCD.refresh();
		}
	}
}
