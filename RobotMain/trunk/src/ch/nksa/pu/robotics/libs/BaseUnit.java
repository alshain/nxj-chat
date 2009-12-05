package ch.nksa.pu.robotics.libs;

import lejos.nxt.*;

public class BaseUnit {
	public static Motor mLeft;
	public static Motor mRight;
	public enum Orientation{
		FORWARD,  //i.e. the NXT brick pointing to the forward direction 
		BACKWARD; //i.e. the NXT brick pointing to the backward direction
	}
	protected static Orientation currentOrientation = Orientation.FORWARD;
	
	public BaseUnit(){
		setOrientation(Orientation.FORWARD);
	}
	
	public void setOrientation(Orientation orientation){
		currentOrientation = orientation;
		if(orientation == Orientation.FORWARD){
			mLeft = Motor.C;
			mRight = Motor.A;
		}
		else{
			mLeft = Motor.A;
			mRight = Motor.C;
		}
	}
		
	public void stopAll(){
		mLeft.stop();
		mRight.stop();
	}
	
	public void setSpeed(int speed){
		mLeft.setSpeed(speed);
		mRight.setSpeed(speed);
	
	}
	
	public void forward(){
		mForward(mLeft);
		mForward(mRight);
	}
	
	public void backward(){
		mBackward(mLeft);
		mBackward(mRight);
	}
	
	public void spinLeft(){
		mLeft.backward();
		mRight.forward();
	}
	
	public void spinRight(){
		mLeft.forward();
		mRight.backward();
	}
	
	protected void mForward(Motor m){
		if(currentOrientation == Orientation.BACKWARD)
			m.backward();
		else
			m.forward();
	}
	
	protected void mBackward(Motor m){
		if(currentOrientation == Orientation.FORWARD)
			m.backward();
		else
			m.forward();
	}
	
	
}
