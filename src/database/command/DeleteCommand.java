package database.command;

import java.util.Map;

import database.Cursor;

public class DeleteCommand implements DbCommand {
	private Map<String, Object> database;
	
	public DeleteCommand(Map<String, Object> database) {
		this.database = database;
	}

	@Override
	public Cursor execute(Cursor cursor) {
		Object deletedValue = database.remove(cursor.key());
		cursor.setValue(deletedValue);
		return cursor;
	}

}
