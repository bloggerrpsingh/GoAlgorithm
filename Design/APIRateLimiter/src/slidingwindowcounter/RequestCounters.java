package slidingwindowcounter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RequestCounters {
	private Map<Long, Integer> counts;
	private AtomicInteger totalCounts;
	private int requests;
	private int windowTimeInSec;
	private int bucketSize;

	public RequestCounters(int requests, int windowTimeInSec, int bucketSize) {
		super();
		this.counts = new ConcurrentHashMap<Long, Integer>();
		this.totalCounts = new AtomicInteger(0);
		this.requests = requests;
		this.windowTimeInSec = windowTimeInSec;
		this.bucketSize = bucketSize;
	}

	public long getBucket(long timestamp) {

		int factor = this.windowTimeInSec / this.bucketSize;
		return (long) ((timestamp / factor) * factor);
	}

	public long getOldestvalidBucket(long currentTimestamp) {
		return this.getBucket(currentTimestamp - this.windowTimeInSec);
	}

	// Remove all the older buckets that are not relevant anymore
	void evictOlderBuckets(long currentTimestamp) {
		long oldestValidBucket = this.getOldestvalidBucket(currentTimestamp);
		List<Long> bucketsToBeDeleted = counts.keySet().stream().filter(bucket -> bucket < oldestValidBucket)
				.collect(Collectors.toList());

		for (long bucket : bucketsToBeDeleted) {
			int bucketCount = this.counts.get(bucket);
			int tc = totalCounts.get();
			tc -= bucketCount;
			totalCounts.set(tc);
			counts.remove(bucket);
		}
	}

	public Map<Long, Integer> getCounts() {
		return counts;
	}

	public AtomicInteger getTotalCounts() {
		return totalCounts;
	}

	public void setTotalCounts(AtomicInteger totalCounts) {
		this.totalCounts = totalCounts;
	}

	public int getRequests() {
		return this.requests;
	}

}
