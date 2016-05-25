package database.command;

import java.util.Map;

import database.Cursor;

public class GetCommand implements DbCommand {
	private Map<String, Object> database;
	
	public GetCommand(Map<String, Object> database) {
		this.database = database;
	}
	
	@Override
	public Cursor execute(Cursor cursor) {
		Object value = database.get(cursor.key());
		cursor.setValue(value);
		return cursor;
	}

}
