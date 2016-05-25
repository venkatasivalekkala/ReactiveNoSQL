package database.observer;

public interface CursorObserver {
	public void onChange(String key, Object value);
}
