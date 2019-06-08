package client_test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import service.RateLimiter;
import slidingwindowcounter.SlidingWindowCounterRateLimiter;
import slidingwindowlogs.SlidingWindowLogsRateLimiter;

public class SlindingWindowLogClient {
	private RateLimiter rateLimiter;

	public SlindingWindowLogClient(RateLimiter rateLimiter) {
		this.rateLimiter = rateLimiter;
	}

	void registerUser(String userId) {
		int windowTimeInSec = 60;
		int requests = 100;
		rateLimiter.addUser(userId, requests, windowTimeInSec);
	}

	boolean checkIfAllowed(String userId) {
		boolean isAllowed = rateLimiter.shouldAllowServiceCall(userId);
		return isAllowed;
	}

	public static void main(String[] args) {
	//	RateLimiter limiter= new SlidingWindowLogsRateLimiter();
		RateLimiter limiter= new SlidingWindowCounterRateLimiter();

		SlindingWindowLogClient client = new SlindingWindowLogClient(limiter);

		List<String> users = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			String usr = "user " + i;
			client.registerUser(usr);
			users.add(usr);

		}

		ExecutorService executors = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 1000000; i++) {

			int rand = new Random().nextInt(9);
			Task task = new Task(users.get(rand), client);
			executors.submit(task);

		}
	}
}

class Task implements Callable<Boolean> {
	private String user;
	private SlindingWindowLogClient client;

	public Task(String user, SlindingWindowLogClient client) {
		super();
		this.user = user;
		this.client = client;
	}

	@Override
	public Boolean call() throws Exception {
		Thread.sleep(300);

		boolean result = client.checkIfAllowed(user);

		System.out.println(user + " : " + result + " : " + System.currentTimeMillis() / 1000);
		return result;

	}

}
