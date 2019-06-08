package slidingwindowlogs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import service.RateLimiter;

public class SlidingWindowLogsRateLimiter implements RateLimiter {

	private Map<String, RequestTimestamps> ratelimiterMap;

	public SlidingWindowLogsRateLimiter() {

		ratelimiterMap = new ConcurrentHashMap<String, RequestTimestamps>();
	}

	@Override
	public void addUser(String userId, int requests, int windowTimeInSec) {

		if (!ratelimiterMap.containsKey(userId)) {
			ratelimiterMap.put(userId, new RequestTimestamps(requests, windowTimeInSec));
		}
	}

	@Override
	public void removeUser(String userId) {

		if (ratelimiterMap.containsKey(userId)) {
			ratelimiterMap.remove(userId);
		}

	}

	@Override
	public boolean shouldAllowServiceCall(String userId) {

		if (!ratelimiterMap.containsKey(userId)) {
			System.out.println("User Not Allowed");
			return false;
		}
		RequestTimestamps userTimestamps = ratelimiterMap.get(userId);
		synchronized (userTimestamps) {

			long currentTimestamp = System.currentTimeMillis() / 1000;
			userTimestamps.evictOlderTimestamps(currentTimestamp);

			userTimestamps.getTimestamps().add(currentTimestamp);
			if ((userTimestamps.getTimestamps().size()) > userTimestamps.getRequests()) {
				return false;
			}
		}
		return true;
	}

}
