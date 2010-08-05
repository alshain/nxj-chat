package ch.nksa.pu.robotics.libs.pc;

import java.util.ArrayList;
import java.util.List;

public class MasterMind {
	public List<Slave> nxts = new ArrayList<Slave>();
	public List<Slave> connectedNxts = new ArrayList<Slave>();
	private static MasterMind instance = null;
	protected int connectionCount = 0;
	
	public static MasterMind getInstance(){
		if(instance == null){
			return instance = new MasterMind();
		}
		return instance;
	}
	
	public MasterMind(){}
	
	public int connectAll(){
		connectionCount = 0;
		for(Slave s: nxts){
			if(s.connect()){
				connectionCount++;
			}
		}
		updateConnectedList();
		return connectionCount;
	}
	
	public void updateConnectedList(){
		connectedNxts.clear();
		for(Slave slave: nxts){
			if(slave.isConnected()){
				connectedNxts.add(slave);
			}
		}
	}
	
	public boolean connectToNxt(int index){
		boolean result = nxts.get(index).connect();
		updateConnectedList();
		return result;
	}
	
	public boolean connectToNxt(String name_or_addr){
		boolean result = false;
		for(Slave s: nxts){
			if(s.getAddress() == name_or_addr)
				result = s.connect();
			if(s.getName() == name_or_addr)
				result = s.connect();
		}
		
		updateConnectedList();
		return result;
	}
	
	
	public Slave getConnectedNxt(int index){
		return connectedNxts.get(index);
	}
	
	public Slave getConnectedNxt(String name_or_addr){
		for(Slave s: connectedNxts){
			if(s.getAddress() == name_or_addr)
				return s;
			if(s.getName() == name_or_addr)
				return s;
		}
		return null;
	}
	
	public boolean isNxtConnected(String name_or_addr){
		for(Slave s: connectedNxts){
			if(s.getAddress() == name_or_addr)
				return true;
			if(s.getName() == name_or_addr)
				return true;
		}
		return false;
	}
	
	public void addNxt(Slave slave){
		nxts.add(slave);
	}
	
	public void addNewNxts(String[][] nameAddrArray){
		for(int i=0; i < nameAddrArray.length; i++){
        	addNewNxt(nameAddrArray[i][0], nameAddrArray[i][1]);
        }
	}
	
	public void addNewNxt(String name, String address){
		nxts.add(new Slave(name, address));
	}
	
	public int getConnectedNxtCount(){
		return connectionCount;
	}
	
	public int getTotalNxtCount(){
		return nxts.size();
	}
	
	public void sendSharedRequest(){
		//TODO: implement sharedRequest thingy
	}
	
}
