package main;

import java.io.IOException;

import org.json.JSONException;

import database.Cursor;
import database.CustomObject;
import database.DbAdministrator;
import database.customArray;
import database.observer.CursorObserver;
import database.observer.CursorObserverImpl;

public class Main {

	public static void main(String[] args) throws JSONException, IOException {
		DbAdministrator database = DbAdministrator.getInstance();
		CursorObserver cursorObserver = new CursorObserverImpl();
		database.restoreDatabase(); // recover snapshot of database form file
		
		Cursor data = database.getCursor();
		data.addObserver(cursorObserver);
		
		System.out.println("\nWithout commit session");
		database.startSession();
		database.put("bar1", 300);
		database.put("bar1", 400); 
		database.put("foo1", 500); 
		database.rollback();
		database.printDatabase();
		
		customArray dbArray=new customArray();
		dbArray=customArray.fromString("[1,5,2.4,\"lekkala\",[5,6,7],{\"name\" : \"siva\"}]");
		String convertToObject="{\"name\":\"MyNode\", \"width\":200, \"height\":100.10, \"arr\":[3,4,5],\"obj\":{\"name\":\"dep\", \"width\":25, \"height\":11.0}}";
		CustomObject customObject=CustomObject.fromString(convertToObject);
		// Session
		System.out.println("\nUsing commit session");
		database.startSession();
		database.put("bar3", customObject);
		database.put("array", dbArray);
		database.put("bar1", 300);
		database.put("bar1", 400); 
		database.put("foo1", 500); 
		database.printDatabase();
		database.saveDatabase();   //snapShot of database save into file
	}

}
