package telran.util.words;
import java.util.function.Predicate;
import java.util.*;

public class AutoCompletionMapImpl implements AutoCompletion {
	HashMap<Character, TreeSet<String>> words = new HashMap<>(); // key - first character of a word;
// value - collection (TreeSet) of words beginning with the given first character case insensitive

	@Override
	/**
	 * adds word with applying the method computeIfAbsent
	 */
	public boolean addWord(String word) {
		// V.R. In case "word is empty" it fails
		return words.computeIfAbsent(getkey(word), 
				t -> new TreeSet<>(String.CASE_INSENSITIVE_ORDER)).add(word);
	}

	private Character getkey(String word) {
		return word.toLowerCase().charAt(0);
	}

	@Override
	public boolean removeWord(String word) {
		// V.R. In case "word is empty" it fails
		return words.getOrDefault(getkey(word), new TreeSet<>(String.CASE_INSENSITIVE_ORDER)).remove(word);
	}

	@Override
	public Iterable<String> getCompletionOptions(String prefix) {
		// V.R. In case "prefix is empty" it fails
		char keyForSearch = getkey(prefix);
		// V.R. The case "key isn't mapped" isn't supported
		// V.R. Very bad solution (even forbidden). One interface's implementation
		// know nothing about other implementations. It is the base of OOD.
		return words.get(keyForSearch).subSet(prefix, AutoCompletionImpl.getPrefixLimit(prefix));
	}

	/**
	 * removes words matching a given predicate
	 * 
	 * @param predicate
	 * @return count of the removed words
	 */
	public int removeIf(Predicate<String> predicate) {
		int countOfRemovedWords = 0;
		// V.R. It is better to define 'treeSet' INSIDE the loop
		// words.keySet() has to be called outside of loop
		TreeSet<String> treeSet;
		for (char c : words.keySet()) {
			treeSet = words.get(c);
			int startSize = treeSet.size();
			treeSet.removeIf(predicate);
			countOfRemovedWords += startSize - treeSet.size();
		}
		return countOfRemovedWords;
		// V.R. The algorithm isn't effective. Think about 'view' mechanism
	}

}