package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.Gson;

import database.memento.DbCareTaker;

public class DbAdministrator {
	private static final String DB_LOCATION = "database/database.json";
	private static DbAdministrator instance;
	private Database database;
	private Cursor cursor;
	private DbCareTaker dbCareTaker;

	private DbAdministrator(Database database, Cursor cursor, DbCareTaker dbCareTaker) {
		this.database = database;
		this.cursor = cursor;
		this.dbCareTaker = dbCareTaker;
	}

	public static DbAdministrator getInstance() {
		if (instance == null) {
			Database database = new Database();
			Cursor cursor = Cursor.getInstance();
			DbCareTaker dbCareTaker = new DbCareTaker();
			instance = new DbAdministrator(database, cursor, dbCareTaker);
		}
		return instance;
	}

	public Cursor put(String key, Object value) {
		cursor.set(key, value);
		cursor = database.put(cursor);
		dbCareTaker.add(database.saveStateToMemento());
		return cursor;
	}

	public Cursor get(String key) {
		cursor.setKey(key);
		cursor = database.get(cursor);
		return cursor;
	}

	public Cursor delete(String key) {
		cursor.setKey(key);
		cursor = database.delete(cursor);
		dbCareTaker.add(database.saveStateToMemento());
		return cursor;
	}

	public Cursor getCursor() {
		return cursor;
	}

	public void saveDatabase() {
		saveDatabase(DB_LOCATION);
	}

	public void saveDatabase(String location) {
		Gson gson = new Gson();
		String json = gson.toJson(dbCareTaker);
		System.out.println(json);

		File file = null;
		FileWriter writer = null;
		try {
			file = new File(location);
			if (!file.exists()) {
				file.createNewFile();
			}
			file = new File(location);
			writer = new FileWriter(file);
			writer.write(json);
		} catch (IOException e) {
			throw new IllegalStateException("Cannot save database to " + location);
		} finally {
			closeWriter(writer);
		}
	}

	public void restoreDatabase() {
		restoreDatabase(DB_LOCATION);
	}

	public void restoreDatabase(String location) {
		// Create the source
		File file = new File(location);
		// Create a "connection" stream, which connect directly to source
		FileReader fileReader = null;
		BufferedReader reader = null;
		try {
			fileReader = new FileReader(file);
			// Create a "chain" (or "filter") stream
			reader = new BufferedReader(fileReader);
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			Gson gson = new Gson();
			dbCareTaker = gson.fromJson(sb.toString(), DbCareTaker.class);
			database.getStateFromMemento(dbCareTaker.getLatestState());
			cursor = Cursor.getInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot restore database from " + location);
		} finally {
			closeReader(reader);
			closeReader(fileReader);
		}

		printDatabase();
	}

	public void startSession() {
		database.startSession();
	}

	public void rollback() {
		database.rollback();
	}

	public void printDatabase() {
		System.out.println(database);
	}

	private void closeReader(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}

	private void closeWriter(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
	}
}
