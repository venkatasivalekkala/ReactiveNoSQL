package database.command;

import java.util.Map;

import database.Cursor;

public class PutCommand implements DbCommand {
	private Map<String, Object> database;
	
	public PutCommand(Map<String, Object> database) {
		this.database = database;
	}

	@Override
	public Cursor execute(Cursor cursor) {
		database.put(cursor.key(), cursor.value());
		return cursor;
	}

}
