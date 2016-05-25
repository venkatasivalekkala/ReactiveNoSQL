package database.observer;

public class CursorObserverImpl implements CursorObserver {
	@Override
	public void onChange(String key, Object value) {
		System.out.println("CursorObserver: The data in " + key + " has been change to " + value);
	}

}
