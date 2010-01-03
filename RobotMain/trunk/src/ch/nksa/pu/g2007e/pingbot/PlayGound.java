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
	public static SensorMount sMount = SensorMount.getInstance();
	public static BaseUnit base = BaseUnit.getInstance();
	
	public static void main( String[] args) throws Exception{
		base.setOrientation(Orientation.BACKWARD);
		printLight();
		//printBluetoothAddress();
	}
	
	public static void printDistance() throws Exception{
		while(!Button.ESCAPE.isPressed()){
			LCD.drawString(String.valueOf(sMount.getDistance()), 0, 0);
			LCD.refresh();
		}
	}
	
	public static void printLight() throws Exception{
		while(!Button.ESCAPE.isPressed()){
			LCD.drawString(String.valueOf(sMount.light.readNormalizedValue()) + "   ", 0, 0);
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
