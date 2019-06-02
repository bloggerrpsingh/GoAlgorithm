package bloomfilter;

import java.util.Arrays;
import java.util.HashSet;

public class BloomFilterTest {

	public static void main(String[] args) {

		int n = 20; // no of items to add
		float p = 0.05f; // false positive probability

		BloomFilter bloomf = new BloomFilter(n, p);
		System.out.println("Size of bit array: " + bloomf.bitSetSize);
		System.out.println("False positive Probability: " + bloomf.falsePostiveProbability);
		System.out.println("Number of hash functions: " + bloomf.hashCount);

		// words to be added
		String[] word_present = { "abound", "abounds", "abundance", "abundant", "accessable", "bloom", "blossom",
				"bolster", "bonny", "bonus", "bonuses", "coherent", "cohesive", "colorful", "comely", "comfort", "gems",
				"generosity", "generous", "generously", "genial" };

		// word not added
		String[] word_absent = { "sweet", "india", "hate", "war", "humanity", "racism", "hurt", "nuke", "gloomy",
				"facebook", "goalgorithm", "twitter" };
		HashSet<String> setAbsent = new HashSet<String>(Arrays.asList(word_absent));

		String[] testWord = { "abound", "abounds", "abundance", "abundant", "accessable", "bloom", "blossom", "bolster",
				"bonny", "bonus", "bonuses", "coherent", "cohesive", "colorful", "racism", "india", "nuke", "gloomy",
				"facebook", "goalgorithm", "twitter" };

		for (String data : word_present) {
			bloomf.add(data);
		}

		for (String word : testWord) {
			if (bloomf.query(word)) {
				if (setAbsent.contains(word)) {
					System.out.println(word + " is a false positive!");
				} else {
					System.out.println(word + " is probably present!");
				}
			} else {
				System.out.println(word + " is definitely not present!");
			}

		}
	}

}
