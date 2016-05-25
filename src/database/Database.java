package database;

import java.util.HashMap;
import java.util.Map;

import database.command.DbCommand;
import database.command.DeleteCommand;
import database.command.GetCommand;
import database.command.PutCommand;
import database.memento.DbMemento;
import database.memento.DbOriginator;

public class Database implements DbOriginator {
	private Map<String, Object> database;
	private Map<String, Object> savedDatabase;
	private DbCommand putCommand;
	private DbCommand getCommand;
	private DbCommand deleteCommand;

	public Database() {
		database = new HashMap<String, Object>();
		putCommand = new PutCommand(database);
		getCommand = new GetCommand(database);
		deleteCommand = new DeleteCommand(database);
	}

	public Cursor get(Cursor cursor) {
		return getCommand.execute(cursor);
	}

	public Cursor put(Cursor cursor) {
		return putCommand.execute(cursor);
	}

	public Cursor delete(Cursor cursor) {
		return deleteCommand.execute(cursor);
	}

	public void startSession() {
		savedDatabase = new HashMap<String, Object>(database);
	}

	public void rollback() {
		if (savedDatabase == null) {
			throw new IllegalStateException("Session must be started first");
		}
		setState(savedDatabase);
		savedDatabase = null;
	}

	@Override
	public void setState(Map<String, Object> state) {
		database = state;
		putCommand = new PutCommand(database);
		getCommand = new GetCommand(database);
		deleteCommand = new DeleteCommand(database);

	}

	@Override
	public Map<String, Object> getState() {
		return database;
	}

	@Override
	public DbMemento saveStateToMemento() {
		return new DbMemento(new HashMap<>(database));
	}

	@Override
	public void getStateFromMemento(DbMemento memento) {
		database = memento.getState();
		putCommand = new PutCommand(database);
		getCommand = new GetCommand(database);
		deleteCommand = new DeleteCommand(database);
	}

	@Override
	public String toString() {
		String str = "[Database: " + database.toString() + "]";
		return str;
	}
}
