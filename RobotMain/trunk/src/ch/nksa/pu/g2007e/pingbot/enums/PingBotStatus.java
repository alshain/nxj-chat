package ch.nksa.pu.g2007e.pingbot.enums;

import ch.nksa.pu.robotics.libs.EnumReplacement;

public class PingBotStatus extends EnumReplacement {
	private static int index = EnumReplacement.registerEnum();
	public PingBotStatus(String name) {
		super(name, index);
		// TODO Auto-generated constructor stub
	}
	
	public static final PingBotStatus ACTIVE = new PingBotStatus("active");
	public static final PingBotStatus PASSIVE = new PingBotStatus("passive");
	
	public static PingBotStatus getFromString(String name){
		return (PingBotStatus) EnumReplacement.fromString(name, index);
	}
}
