package service;

public interface RateLimiter {
	void addUser(String userId, int requests, int windowTimeInSec) ;
	void removeUser(String userId);
	boolean shouldAllowServiceCall(String userId);
	

}
