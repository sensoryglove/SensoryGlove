package test.gpiotest;


import code.gpio.GPIOPin;
import code.gpio.GPIOPin.GPIOMode;
import code.gpio.exceptions.GPIOCreationFailedException;
import code.gpio.exceptions.GPIOReadException;
import code.gpio.exceptions.GPIOWriteException;

public class GPIOTest {
	public static void main(String[] args) throws GPIOWriteException,
			InterruptedException, GPIOCreationFailedException, GPIOReadException {
		
		GPIOPin gpio = new GPIOPin(4,GPIOMode.INPUT);
		GPIOPin led = new GPIOPin(7, GPIOMode.OUTPUT);
		
		boolean whatever = false;
		
		while(true){
			if(gpio.readPin() == 1){
				System.out.println("Hi there!");
				led.setHigh();
				whatever = true;
			}else if(whatever){
				led.setLow();
				whatever = false;
			}
			
			Thread.sleep(1000/30);
		}
		
	}
}
