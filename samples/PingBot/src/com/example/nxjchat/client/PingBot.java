package com.example.nxjchat.client;

import ch.nksa.pu.robotics.libs.BaseUnit;
import ch.nksa.pu.robotics.libs.Uplink;
import ch.nksa.pu.robotics.libs.BaseUnit.Orientation;
import lejos.nxt.*;
import lejos.util.Stopwatch;

public class PingBot {

	/**
	 * @param args
	 */
	public static BaseUnit base = BaseUnit.getInstance();
	public static Uplink uplink = Uplink.getInstance(true);
	public static SensorMount sMount = SensorMount.getInstance();
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		base.setOrientation(Orientation.BACKWARD);
		PingBotBasicInRequest.registerRequest();
		Thread.sleep(100000);
	}
	
	public static void searchTheLight() throws InterruptedException{
		int samples = 1500;
		//consider this a guideline, not an exact setting
		int time = 5000;
		//this parameter controls how much jitters are being smoothed out
		int smoothness = 4;
		int[] values = new int[samples];
		int epsilon = 9;
		int high;
		Stopwatch timeout = new Stopwatch();
		from_start:
		while(true){
			high = 0;
			base.setSpeedPercentage(30);
			base.spinRight();
			//quick scan
			for(int i = 0; i < samples; i++){
				values[i] = sMount.light.readNormalizedValue();
				//get new smoothed high value
				if(i >= smoothness){
					int temp = 0;
					for(int k = i; k >= i - smoothness; k--){
						temp += values[k];
					}
					temp = temp/(smoothness+1);
					if(temp > high){
						high = temp;
					}
				}
				Thread.sleep(time/samples);
			}
			int old_mean = 0;
			int new_mean = 0;
			int[] stack = new int[smoothness + 1];
			base.setSpeedPercentage(7);
			timeout.reset();
			//slow scan
			while(true){
				for(int i = 0; i < smoothness + 1; i++){
					stack[i] = sMount.light.readNormalizedValue();
					new_mean = calculateMean(stack);
					if(new_mean > old_mean){
						old_mean = new_mean;
					}
					else if(high - new_mean < epsilon){
						base.reverse();
						Thread.sleep(200);
						break from_start;
					}
					Thread.sleep(10);
				}
				if(timeout.elapsed() > time * 4){
					continue from_start;
				}
			}
		}
		
		base.stopAll();
		System.out.println(high);
		Thread.sleep(5000);
	}
	
	protected static int calculateMean(int[] values){
		int temp = 0;
		for(int i = 0; i < values.length; i++){
			temp += values[i];
		}
		
		return temp/values.length;
	}
}
