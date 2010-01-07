package ch.nksa.pu.robotics.libs;

import java.util.ArrayList;

public class EnumReplacement {
	private final String name;
	private static ArrayList<ArrayList<EnumReplacement>> list = new ArrayList<ArrayList<EnumReplacement>>();
	private static int nextIndex = 0;
	//private static int nextOrdinal = 0;
	//private final int ordinal = nextOrdinal++;
	
	protected static int registerEnum(){
		list.add(new ArrayList<EnumReplacement>());
		return nextIndex++;
	}
	
	protected EnumReplacement(String name, int index){
		this.name = name;
		list.get(index).add(this);
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
	
	public static Boolean valueExists(String name, int index){
		for(EnumReplacement e: list.get(index)){
			Util.log(e.toString());
			if(e.equals(name)){
				return true;
			}
		}
		return false;
	}
	
	protected static EnumReplacement fromString(String name, int index){
		for(EnumReplacement e: list.get(index)){
			if(e.toString().equals(name)){
				return e;
			}
		}
		return null;
	}

	protected static EnumReplacement fromDataArray(byte[] data, int index){
		if(data.length > 0){
			return EnumReplacement.fromString(Util.bytesToString(data), index);
		}
		return null;
	}
	
	protected static EnumReplacement fromDataArray(byte[][] data, int index){
		if(data.length == 1){
			return EnumReplacement.fromDataArray(data[0], index);
		}
		return null;
	}
}
