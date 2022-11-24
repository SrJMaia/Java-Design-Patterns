package org.example.solid.s.after.src.main.java.com.company.singleresp;

//A separate class for handling persistence 
public class UserPersistenceService {

	private Store store = new Store();
	
	public void saveUser(User user) {
		store.store(user);
	}
}
