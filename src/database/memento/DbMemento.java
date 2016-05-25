package database.memento;

import java.util.Map;

public class DbMemento {
	private Map<String, Object> state;

	public DbMemento(Map<String, Object> state) {
		this.state = state;
	}

	public Map<String, Object> getState() {
		return state;
	}
}
