package ch.nksa.pu.g2007e.pingbot;

import ch.nksa.pu.g2007e.pingbot.SensorMount;
import ch.nksa.pu.robotics.libs.BaseUnit;
import lejos.nxt.*;

public class PingBot {

	/**
	 * @param args
	 */
	public static BaseUnit base = new BaseUnit();
	public static SensorMount sMount = new SensorMount();
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		base.setSpeed(8000);
		base.spinLeft();
		Thread.sleep(8000);
	}

}
