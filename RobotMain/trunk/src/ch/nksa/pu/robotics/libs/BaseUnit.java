package ch.nksa.pu.robotics.libs;

import lejos.nxt.*;

public class BaseUnit {
	public static Motor mLeft;
	public static Motor mRight;
	protected static BaseUnit instance;
	public enum Orientation{
		FORWARD,  //i.e. the NXT brick pointing to the forward direction 
		BACKWARD; //i.e. the NXT brick pointing to the backward direction
	}
	protected static Orientation currentOrientation = Orientation.FORWARD;
	
	protected BaseUnit(){
		setOrientation(Orientation.FORWARD);
	}
	
	public static BaseUnit getInstance(){
		if(instance == null){
			instance = new BaseUnit();
		}
		return instance;
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
	
	public void stopLeft(){
		mLeft.stop();
	}
	
	public void stopRight(){
		mRight.stop();
	}
	
	public void toggleSmoothAccl(){
		mLeft.smoothAcceleration(!mLeft._rampUp );
		mRight.smoothAcceleration(!mRight._rampUp );
	}
	
	public void setSmoothAcceleration(boolean enable){
		mLeft.smoothAcceleration(enable);
		mRight.smoothAcceleration(enable);
	}
	
	public void setSpeed(int speed){
		mLeft.setSpeed(speed);
		mRight.setSpeed(speed);
	}
	
	public void setSpeedPercentage(int speed){
		int max_speed = 900;
		float result = max_speed * speed / 100;
		Util.log(result);
		setSpeed((int) result);
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
		if(currentOrientation == Orientation.FORWARD)
			m.forward();
		else
			m.backward();
	}
	
	protected void mBackward(Motor m){
		if(currentOrientation == Orientation.FORWARD)
			m.backward();
		else
			m.forward();
	}

	public void reverse() {
		mLeft.reverseDirection();
		mRight.reverseDirection();
	}
}
