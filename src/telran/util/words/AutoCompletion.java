package telran.util.words;

public interface AutoCompletion {
	boolean addWord(String word); //adds word, returns true if added,

	boolean removeWord(String word); //removes word returns true if removed
	Iterable<String> getCompletionOptions(String prefix); //returns any Iterable of words beginning from a given prefix
}