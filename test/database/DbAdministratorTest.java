package database;

import org.junit.Test;
import static org.junit.Assert.*;

public class DbAdministratorTest {
	private static final String DB_TEST_LOCATION = "database/testDatabase.json";
	private DbAdministrator dbAdmin = DbAdministrator.getInstance();
	
	@Test
	public void testGetAndPut() {
		dbAdmin.put("toto", 10);
		
		Cursor cursor = dbAdmin.get("toto");
		assertEquals("Should be equal", 10, cursor.value());
	}
	
	@Test
	public void testSaveAndGetDb() {
		dbAdmin.put("toto", 10);
		dbAdmin.put("tata", 20);
		
		dbAdmin.saveDatabase(DB_TEST_LOCATION);
		dbAdmin.put("toto", 0);
		dbAdmin.put("tata", 0);
		Cursor cursor = dbAdmin.get("toto");
		assertEquals("Should be equal", 0, cursor.value());
		cursor = dbAdmin.get("tata");
		assertEquals("Should be equal", 0, cursor.value());
		
		dbAdmin.restoreDatabase(DB_TEST_LOCATION);
		cursor = dbAdmin.get("toto");
		assertEquals("Should be equal", Double.valueOf(10), cursor.value());
		cursor = dbAdmin.get("tata");
		assertEquals("Should be equal", Double.valueOf(20), cursor.value());
	}
}
