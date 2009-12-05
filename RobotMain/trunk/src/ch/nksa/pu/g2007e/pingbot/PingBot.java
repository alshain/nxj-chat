package ch.nksa.pu.g2007e.pingbot;

import ch.nksa.pu.g2007e.pingbot.SensorMount;
import ch.nksa.pu.robotics.libs.BaseUnit;
import ch.nksa.pu.robotics.libs.BaseUnit.Orientation;
import lejos.nxt.*;

public class PingBot {

	/**
	 * @param args
	 */
	public static BaseUnit base = new BaseUnit();
	//public static SensorMount sMount = new SensorMount();
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		base.setOrientation(Orientation.BACKWARD);

	}

}
