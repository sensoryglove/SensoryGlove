package code;

import code.gpio.GPIOPin;
import code.gpio.GPIOPin.GPIOMode;
import code.gpio.exceptions.GPIOCreationFailedException;
import code.gpio.exceptions.GPIOWriteException;
import code.sensors.FlexSensor;
import code.sensors.interfaces.ISensor;
import code.sensors.interfaces.SensorUpdateListener;

public class Main {
	/*public static void function() throws GPIOCreationFailedException {
		FlexSensor fs = new FlexSensor(7);
		GPIOPin gpio = new GPIOPin(6,GPIOMode.OUTPUT);
		System.out.println("Starting looper!");
		SensorEventLoop loop = new SensorEventLoop(30);
		System.out.println("Registering sensor...");
		loop.registerSensor(fs);
		System.out.println("Adding sensor update listener...");
		loop.addSensorUpdateListener(new SensorUpdateListener() {
			
			@Override
			public void sensorUpdated(ISensor s) {
				// TODO Auto-generated method stub
				System.out.println("Sensor " + s.getPinNumber() + " has changed stated to " + s.getValue() + "!");
				
				if(s.getValue() > 0){
					try {
						gpio.setHigh();
					} catch (GPIOWriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						gpio.setLow();
					} catch (GPIOWriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
}
