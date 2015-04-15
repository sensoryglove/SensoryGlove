package code.gpio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import code.gpio.exceptions.GPIOCreationFailedException;
import code.gpio.exceptions.GPIOReadException;
import code.gpio.exceptions.GPIOWriteException;

public class GPIOPin {
	
	public enum GPIOMode {
		INPUT,OUTPUT;
	}
	
	private static final String exportPath = "/sys/class/gpio/export";
	private static final String unexportPath = "/sys/class/gpio/unexport";
	private static final String gpioBasePath = "/sys/class/gpio/gpio";
	private static final String directionPath = "/direction";
	private static final String valuePath = "/value";
	
	private String gpioPath;
	
	private GPIOMode _currentMode;
	
	public GPIOPin(int pinNumber) throws GPIOCreationFailedException{
		this(pinNumber,GPIOMode.INPUT);
	}
	
	public GPIOPin(int pinNumber, GPIOMode mode) throws GPIOCreationFailedException{
		gpioPath = gpioBasePath + pinNumber;
		_currentMode = mode;
		
		try {
			export(pinNumber);
		} catch (FileNotFoundException e) {
			System.err.println("Failed to create GPIO file!");
			e.printStackTrace();
			throw new GPIOCreationFailedException();
		}
		
		try {
			setMode(mode);
		} catch (FileNotFoundException e) {
			System.err.println("Failed to set mode!");
			e.printStackTrace();
			
			try {
				unexport(pinNumber);
			} catch (FileNotFoundException e1) {
				System.err.println("Failed to unexport!");
				e1.printStackTrace();
				throw new GPIOCreationFailedException();
			}
			
			throw new GPIOCreationFailedException();
		}
		
	}
	
	private void setDirection(GPIOMode m) throws FileNotFoundException{
		File f = new File(gpioPath+directionPath);
		PrintWriter pw = new PrintWriter(f);
		pw.print(m == GPIOMode.INPUT ? "in" : "out");
		System.out.println("Printing " + (m == GPIOMode.INPUT ? "in" : "out") + " to " +  gpioPath+directionPath);
		pw.flush();
		pw.close();
	}
	
	private void export(int pin) throws FileNotFoundException{
		File f = new File(exportPath);
		PrintWriter pw = new PrintWriter(f);
		pw.print(pin);
		pw.flush();
		pw.close();
	}
	
	private void unexport(int pin) throws FileNotFoundException{
		File f = new File(unexportPath);
		PrintWriter pw = new PrintWriter(f); 
		pw.print(pin);
		pw.flush();
		pw.close();
	}
	
	private void writeToValue(int value) throws FileNotFoundException{
		File f = new File(gpioPath + valuePath);
		
		System.out.println("Writing to file " + f.getPath());
		
		PrintWriter pw = new PrintWriter(f);
		
		if(value == 0){
			pw.write('0');
		}else{
			pw.write('1');
		}
		pw.flush();
		pw.close();
	}
	
	private int readFromValue() throws FileNotFoundException{
		File f = new File(gpioPath + valuePath);
		Scanner s = new Scanner(f);
		int val = s.nextInt();
		s.close();
		return val;
	}
	
	public void setMode(GPIOMode mode) throws FileNotFoundException{
		switch(mode){
		case INPUT:
			setDirection(mode);
			break;
		case OUTPUT:
			setDirection(mode);
			break;
		default:
			break;
		}
		
		_currentMode = mode;
	}
	
	public void setLow() throws GPIOWriteException{
		if(_currentMode != GPIOMode.OUTPUT){
			throw new GPIOWriteException();
		}
		
		try {
			writeToValue(0);
		} catch (FileNotFoundException e) {
			System.err.println("Set low failed!");
			e.printStackTrace();
			throw new GPIOWriteException();
		}
	}
	
	public void setHigh() throws GPIOWriteException{
		if(_currentMode != GPIOMode.OUTPUT){
			throw new GPIOWriteException();
		}
		
		try {
			writeToValue(1);
		} catch (FileNotFoundException e) {
			System.err.println("Set high failed!");
			e.printStackTrace();
			throw new GPIOWriteException();
		}
	}
	
	public int readPin() throws GPIOReadException{
		if(_currentMode != GPIOMode.INPUT){
			throw new GPIOReadException();
		}
		
		try {
			return readFromValue();
		} catch (FileNotFoundException e) {
			System.err.println("Read pin failed!");
			e.printStackTrace();
			throw new GPIOReadException();
		}
	}
}
