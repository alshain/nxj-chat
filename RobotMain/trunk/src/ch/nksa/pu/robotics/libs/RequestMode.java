package ch.nksa.pu.robotics.libs;

/**
 * Classic enum Replacement
 * Needed because enums can be sent but not restored over Bluetooth.
 * @author Christian
 *
 */
public class RequestMode extends EnumReplacement{
	private RequestMode(String name) {
		super(name);
	}
	
	public static RequestMode getFromString(String name){
		return (RequestMode) EnumReplacement.getFromString(name);
	}
	
	public static final RequestMode STATELESS = new RequestMode("stateless");
	public static final RequestMode RESPONSE = new RequestMode("response");
	public static final RequestMode FOLLOW_UP = new RequestMode("follow_up");
	public static final RequestMode BROADCAST = new RequestMode("broadcast");
}
