package database.command;

import database.Cursor;

public interface DbCommand {
	public Cursor execute(Cursor cursor);
}
