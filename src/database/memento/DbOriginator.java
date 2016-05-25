package database.memento;

import java.util.Map;

public interface DbOriginator {
	public void setState(Map<String, Object> state);
	public Map<String, Object> getState();
	public DbMemento saveStateToMemento();
	public void getStateFromMemento(DbMemento memento);
}
