package database;

import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.Test;

import database.memento.DbMemento;

public class DatabaseTest {
	private Database database = new Database();
	
	@Test
	public void testGetAndPut() {
		Cursor cursor = Cursor.getInstance();
		cursor.set("toto", 10);
		database.put(cursor);
		
		Cursor newCursor = database.get(cursor);
		assertEquals("Should be equal", cursor.key(), newCursor.key());
		assertEquals("Should be equal", cursor.value(), newCursor.value());
	}
	
	@Test
	public void testSaveAndGetMemento() {
		Cursor cursor = Cursor.getInstance();
		cursor.set("toto", 10);
		database.put(cursor);
		cursor.set("tata", 20);
		database.put(cursor);
		
		DbMemento memento = database.saveStateToMemento();
		
		// Invalid the database
		database.setState(new HashMap<String, Object>());
		cursor.setKey("toto");
		cursor = database.get(cursor);
		assertEquals("Should be equal", null, cursor.value());
		cursor.setKey("tata");
		cursor = database.get(cursor);
		assertEquals("Should be equal", null, cursor.value());
		
		// Restore from memento
		database.getStateFromMemento(memento);
		cursor.setKey("toto");
		cursor = database.get(cursor);
		assertEquals("Should be equal", 10, cursor.value());
		cursor.setKey("tata");
		cursor = database.get(cursor);
		assertEquals("Should be equal", 20, cursor.value());
		
	}
}
