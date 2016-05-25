package database.memento;

import java.util.ArrayList;
import java.util.List;

public class DbCareTaker {
	private List<DbMemento> mementoList = new ArrayList<DbMemento>();

	public void add(DbMemento state) {
		mementoList.add(state);
	}

	public DbMemento get(int index) {
		return mementoList.get(index);
	}

	public DbMemento getLatestState() {
		return mementoList.get(mementoList.size() - 1);
	}
}
