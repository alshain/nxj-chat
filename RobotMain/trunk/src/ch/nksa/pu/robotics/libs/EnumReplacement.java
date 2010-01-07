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
	
	public boolean equals(EnumReplacement e){
		return this == e;
	}
	
	public boolean equals(String str){
		return this.toString().equals(str);
	}
	
	public static Boolean valueExists(String name){
		for(EnumReplacement e: list){
			if(e.toString().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public static EnumReplacement fromString(String name){
		for(EnumReplacement e: list){
			if(e.toString().equals(name)){
				return e;
			}
		}
		return null;
	}

	public static EnumReplacement fromDataArray(byte[] data){
		if(data.length > 0){
			return EnumReplacement.fromString(Util.bytesToString(data));
		}
		return null;
	}
	
	public static EnumReplacement fromDataArray(byte[][] data){
		if(data.length == 1){
			return EnumReplacement.fromDataArray(data[0]);
		}
		return null;
	}
}
