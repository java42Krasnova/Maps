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
		
		return word.isEmpty()? false:  words.computeIfAbsent(getkey(word), 
				t -> new TreeSet<>(String.CASE_INSENSITIVE_ORDER)).add(word);
	}

	private Character getkey(String word) {
		return word.toLowerCase().charAt(0);
	}

	@Override
	public boolean removeWord(String word) {
		// V.R. TreeSet is empty, so String.CASE_INSENSITIVE_ORDER is redundant
		return   word.isEmpty()?false: words.getOrDefault(getkey(word), new TreeSet<>(String.CASE_INSENSITIVE_ORDER)).remove(word);
	}

	@Override
	public Iterable<String> getCompletionOptions(String prefix) {
		/* V.R.
		 * Why different container types are used for emply Iterable<String>?
		 * ArrayList and TreeSet
		 */
		if(prefix.isEmpty()) {
			return new ArrayList<>();
		}
		char keyForSearch = getkey(prefix);		
		// V.R. TreeSet is empty, so String.CASE_INSENSITIVE_ORDER is redundant
		return words.getOrDefault(keyForSearch, new TreeSet<String>(String.CASE_INSENSITIVE_ORDER)).subSet(prefix, getPrefixLimit(prefix));
	}
	/**
	 * removes words matching a given predicate
	 * 
	 * @param predicate
	 * @return count of the removed words
	 */
	public int removeIf(Predicate<String> predicate) {
		int countOfRemovedWords = 0;
		Collection<TreeSet<String>> collection = words.values();
		for(TreeSet<String> set: collection ) {
			int startSize = set.size();
			set.removeIf(predicate);
			countOfRemovedWords+= startSize-set.size();
		}
		return countOfRemovedWords;
		}
}