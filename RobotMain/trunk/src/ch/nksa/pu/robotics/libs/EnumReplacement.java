package ch.nksa.pu.robotics.libs;

import java.util.ArrayList;

public class EnumReplacement {
	private final String name;
	private static ArrayList<EnumReplacement> list = new ArrayList<EnumReplacement>();
	//private static int nextOrdinal = 0;
	//private final int ordinal = nextOrdinal++;
	
	protected EnumReplacement(String name){
		this.name = name;
		list.add(this);
	}
	
	public String toString(){
		return name;
	}
	
	public static EnumReplacement getFromString(String name){
		for(EnumReplacement e: list){
			if(e.toString() == name){
				return e;
			}
		}
		return null;
	}
	
	public static Boolean valueExists(String name){
		for(EnumReplacement e: list){
			if(e.toString() == name){
				return true;
			}
		}
		return false;
	}
}
