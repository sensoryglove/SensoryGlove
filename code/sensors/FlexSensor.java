package code.sensors;

import code.gpio.GPIOPin;
import code.gpio.GPIOPin.GPIOMode;
import code.gpio.exceptions.GPIOCreationFailedException;
import code.gpio.exceptions.GPIOReadException;
import code.sensors.interfaces.ISensor;

public class FlexSensor implements ISensor {
	
	private GPIOPin myPin;
	
	private int previous, current;
	private int myPinNumber;
	
	public FlexSensor(int pinNumber) throws GPIOCreationFailedException {
		myPin = new GPIOPin(pinNumber, GPIOMode.INPUT);
		previous = current = -1;
		myPinNumber = pinNumber;
	}
	
	@Override
	public boolean hasChangedState() {
		// TODO Auto-generated method stub
		return previous != current;
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return current;
	}

	@Override
	public int getState() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		System.out.println("Flexsens: tick called!");
		previous = current;
		
		try {
			current = myPin.readPin();
			System.out.println("["+myPinNumber+"]Read in value " + current);
		} catch (GPIOReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Couldn't read in flex sensor " + myPin + "!");
		}
	}

	@Override
	public int getPinNumber() {
		// TODO Auto-generated method stub
		return myPinNumber;
	}

}
