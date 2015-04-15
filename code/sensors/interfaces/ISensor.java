package code.sensors.interfaces;

public interface ISensor {
	/**
	 * @author ginonott
	 * 
	 * This method returns true if the state has changed from the last tick.
	 * 
	 * @return whether the state has changed.
	 */
	public boolean hasChangedState();
	
	/**
	 * Retrieves the value since the last time tick was called. 
	 * 
	 * @return 1 if the sensor has a high voltage or 0 if the sensor has a low voltage.
	 */
	public int getValue();
	
	/**
	 * Retrieves the state of the sensor. This is on a sensor by sensor basis and is not required.
	 * 
	 * @return some integer representing the sensors current state.
	 */
	public int getState();
	
	/**
	 * Tells the sensor to update and take a reading or adjust it's output.
	 */
	public void tick();
	
	/**
	 * 
	 * @return the pin number the sensor occupies.
	 */
	public int getPinNumber();
}
