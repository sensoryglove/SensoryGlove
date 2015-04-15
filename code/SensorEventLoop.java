package code;

import java.util.LinkedList;
import java.util.Timer;

import code.sensors.interfaces.ISensor;
import code.sensors.interfaces.SensorUpdateListener;

public class SensorEventLoop {
	
	private static final int DEFAULT_FREQUENCY = 30;
	
	private Thread mLooperThread;
	
	private LinkedList<ISensor> _sensors;
	private LinkedList<SensorUpdateListener> _updateListeners;
	
	public SensorEventLoop() {
		// TODO Auto-generated constructor stub
		this(DEFAULT_FREQUENCY);
	}
	
	public SensorEventLoop(int frequency){		
		_sensors = new LinkedList<>();
		_updateListeners = new LinkedList<>();
		mLooperThread = new Thread(new SensorLooper(frequency));
		mLooperThread.start();
	}
	
	public void addSensorUpdateListener(SensorUpdateListener sul){
		synchronized (sul) {
			_updateListeners.add(sul);
		}
	}
	
	public void removeSensorUpdateListener(SensorUpdateListener sul){
		synchronized (sul) {
			_updateListeners.remove(sul);
		}
	}
	
	public void registerSensor(ISensor sensor){
		synchronized (_sensors) {
			_sensors.add(sensor);
		}
	}
	
	public void removeSensor(ISensor sensor){
		synchronized (_sensors) {
			_sensors.add(sensor);
		}
	}

	private class SensorLooper implements Runnable {

		private int frequency;
		private volatile boolean isRunning;
		
		public SensorLooper(int frequency) {
			// TODO Auto-generated constructor stub
			this.frequency = frequency;
			isRunning = true;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("Running looper!");
			while(isRunning){
				System.out.println("Synchronizing...");
				synchronized (_sensors) {
					for(ISensor s : _sensors){
						s.tick();
						
						if(s.hasChangedState()){
							synchronized (_updateListeners) {
								for(SensorUpdateListener sul : _updateListeners){
									sul.sensorUpdated(s);
								}
							}
						}
					}
				}
				
				try {
					Thread.sleep(1000/frequency);
				} catch (InterruptedException e) {
					System.err.println("SensorLooper thread has been interrupted! Killing...");
					e.printStackTrace();
					isRunning = false;
				}
			}
		}
		
	}
}
