package ch.nksa.pu.g2007e.pingbot.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.nksa.pu.robotics.libs.pc.MasterMind;
import ch.nksa.pu.robotics.libs.pc.OutgoingRequest;
import ch.nksa.pu.robotics.libs.pc.RequestTest;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTInfo;

public class PingBotServer {
	public static String[][] btAddresses = {{"nxt12", "00:16:53:06:e7:ab"}, {"nxt4", "00:16:53:09:76:19"}};
	public static List<NXTComm> connections = new ArrayList<NXTComm>();
	public static List<NXTInfo> connectionInfos = new ArrayList<NXTInfo>();
	public static MasterMind mind;
	public static int connectionCount = 0; 
	
	public static void main(String[] args) throws IOException, NXTCommException{
        System.out.println("Connecting...");
        mind = new MasterMind();
        mind.addNewNxts(btAddresses);
        connectionCount = mind.connectAll();
        if(connectionCount == btAddresses.length){
        	System.out.println("Successfully connected to all " + btAddresses.length + " NXTs.");
        }
        else{
        	System.out.println("Warning: Only " + connectionCount + " out of " + btAddresses.length + " connections have been established.");
        }
        System.out.println("After line");
        System.out.println("Closing...");
        
	}
}
