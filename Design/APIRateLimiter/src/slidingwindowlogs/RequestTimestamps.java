package slidingwindowlogs;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class RequestTimestamps {

	private Queue<Long> timestamps;
	private int requests;
	private int windowTimeInSec;

	public RequestTimestamps(int requests, int windowTimeInSec) {
		super();
		this.timestamps = new ConcurrentLinkedDeque<Long>();
		this.requests = requests;
		this.windowTimeInSec = windowTimeInSec;
	}

	void evictOlderTimestamps(long currentTimestamp) {
		while (timestamps.size() != 0 && (currentTimestamp - timestamps.peek()) > windowTimeInSec) {
			timestamps.remove();

		}
	}

	public Queue<Long> getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(Queue<Long> timestamps) {
		this.timestamps = timestamps;
	}

	public int getRequests() {
		return requests;
	}

	public void setRequests(int requests) {
		this.requests = requests;
	}

	public int getWindowTimeInSec() {
		return windowTimeInSec;
	}

	public void setWindowTimeInSec(int windowTimeInSec) {
		this.windowTimeInSec = windowTimeInSec;
	}
	
}
