package bloomfilter;

import java.util.BitSet;

public class BloomFilter {

	public int bitSetSize;
	public float falsePostiveProbability;
	private BitSet bitSet;
	public int hashCount;

	public BloomFilter(int expectedElements, float falsePostiveProbability) {
		super();

		this.falsePostiveProbability = falsePostiveProbability;
		this.bitSetSize = getSize(falsePostiveProbability, expectedElements);
		this.bitSet = new BitSet(bitSetSize);
		this.hashCount = getHashCount(this.bitSetSize, expectedElements);
	}

	/**
	 Return the hash function(k) to be used using 
        following formula 
        k = (m/n) * lg(2) 
  
        m : int 
            size of bit array 
        n : int 
            number of items expected to be stored in filter 
	 */

	private int getHashCount(int m, int n) {
		int k = (int) ((m / n) * Math.log(2));
		return k;

	}

	/**
	 Return the size of bit array(m) to used using 
        following formula 
        m = -(n * log(p)) / (log(2)^2) 
        n : int 
            number of items expected to be stored in filter 
        p : float 
            False Positive probability in decimal 
	 */
	private int getSize(float p, int n) {

		int m = (int) (-(n * Math.log(p)) / Math.pow(Math.log(2), 2));

		return m;
	}

	public void add(String data) {
		for (int i = 0; i < this.hashCount; i++) {
			int hash = (int) (hash(data.getBytes(), (i * 2 + 1)) % this.bitSetSize);

			this.bitSet.set(Math.abs(hash));
		}
	}

	public boolean query(String data) {
		for (int i = 0; i < this.hashCount; i++) {
			int hash = (int) (hash(data.getBytes(), (i * 2 + 1)) % this.bitSetSize);

			if (bitSet.get(Math.abs(hash)) == false)
				return false;
		}
		return true;
	}

	private long hash(byte[] data, int seed) {
		long hash = seed;
		for (int i = 0; i < data.length; i++) {
			hash = hash * 31 + data[i];
		}

		return hash;
	}

}
