package database;

import java.util.ArrayList;
import java.util.List;

import database.observer.CursorObserver;

public class Cursor {
	private static Cursor instance;
	private String key;
	private Object value;
	private List<CursorObserver> observers;
	
	private Cursor() {
		observers = new ArrayList<CursorObserver>();
	}
	
	public static Cursor getInstance() {
		if (instance == null) {
			instance = new Cursor();
		}
		return instance;
	}
	
	public Cursor(String key) {
		this.key = key;
	}
	
	public Cursor(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public void addObserver(CursorObserver cursorObserver) {
		observers.add(cursorObserver);
	}
	
	public void removeObserver(CursorObserver cursorObserver) {
		observers.remove(cursorObserver);
	}
	
	public void notifyKeyChange() {
		for (CursorObserver observer : observers) {
			observer.onChange(key, value);
		}
	}
	
	public void notifyValueChange() {
		for (CursorObserver observer : observers) {
			observer.onChange(key, value);
		}
	}
	
	public void notifyChange() {
		for (CursorObserver observer : observers) {
			observer.onChange(key, value);
		}
	}
	
	public void set(String key, Object value) {
		this.key = key;
		this.value = value;
		notifyChange();
	}
	
	public String key() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public Object value() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
}
