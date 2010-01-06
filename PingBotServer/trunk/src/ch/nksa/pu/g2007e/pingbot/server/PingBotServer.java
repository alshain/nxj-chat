package ch.nksa.pu.g2007e.pingbot.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTInfo;
import ch.nksa.pu.g2007e.pingbot.enums.PingBotStatus;
import ch.nksa.pu.robotics.libs.BasicIncomingPcRequest;
import ch.nksa.pu.robotics.libs.pc.BasicOutgoingPcRequest;
import ch.nksa.pu.robotics.libs.pc.MasterMind;

public class PingBotServer {
	public static String[][] btAddresses = {{"nxt12", "00:16:53:06:e7:ab"}, {"nxt4", "00:16:53:09:76:19"}};
	public static List<NXTComm> connections = new ArrayList<NXTComm>();
	public static List<NXTInfo> connectionInfos = new ArrayList<NXTInfo>();
	public static MasterMind mind;
	public static int connectionCount = 0; 
	
	public static void main(String[] args) throws IOException, NXTCommException, InterruptedException{
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
        String temp;
        Scanner in = new Scanner(System.in);
        BasicPingBotInRequest.registerListener(mind.getConnectedNxt(0));
        BasicOutgoingPcRequest req;
        while(true){
        	temp = in.nextLine();
        	if("off".equalsIgnoreCase(temp)){
        		req = BasicPingBotOutRequest.searchLight(mind.getConnectedNxt(0), false);
        	}
        	else if("dis".equalsIgnoreCase(temp)){
        		req = BasicPingBotOutRequest.getDistance(mind.getConnectedNxt(0));
        		System.out.println("Waiting for reply.");
        		req.waitForReply();
        		System.out.println("Reply received.");
        	}
        	else if("exit".equalsIgnoreCase(temp)){
        		break;
        	}
        	else{
        		req = BasicPingBotOutRequest.searchLight(mind.getConnectedNxt(0), true);
        	}
        }
        System.out.println("Closing...");
	}
}
