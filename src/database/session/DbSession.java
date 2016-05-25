package database.session;

import java.util.List;

import database.command.DbCommand;

public class DbSession {
	private List<DbCommand> commands;
	
	public void commit() {
		for (DbCommand command: commands) {
			//command.execute();
		}
	}
	
	public void addToSession(DbCommand command) {
		commands.add(command);
	}
}
