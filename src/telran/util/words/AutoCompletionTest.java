package telran.util.words;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutoCompletionTest {

	String words[] = { "abcdef", "ab123", "aaa", "ab", "ablmn", "abbbb", "a", "ABd", "bbb", "B12" };
	String wordsStartB[] = { "B12", "bbb" };
	String wordsStartAB[] = { "ab", "ab123", "abbbb", "abcdef", "ABd", "ablmn" };
	String wordsStartABC[] = { "abcdef" };
	AutoCompletion autoCompletion;

	@BeforeEach
	void setUp() throws Exception {
		autoCompletion = new AutoCompletionMapImpl();
		for (String word : words) {
			autoCompletion.addWord(word);
		}

	}

	@Test
	void addRemoveTest() {
		//adding
		assertFalse(autoCompletion.addWord(""));
		assertTrue(autoCompletion.addWord("mirrow"));
		//adding existing
		assertFalse(autoCompletion.addWord("mirrow"));
		//removing
		assertTrue(autoCompletion.removeWord("mirrow"));
		//removing no existing 
		assertFalse(autoCompletion.removeWord("mirrow"));
		assertFalse(autoCompletion.removeWord(""));
	}

	@Test
	void testCompletionOptions() {
		assertIterableEquals(new ArrayList<>(), autoCompletion.getCompletionOptions(""));
		assertIterableEquals(Arrays.asList(wordsStartABC), autoCompletion.getCompletionOptions("abc"));
		assertIterableEquals(Arrays.asList(wordsStartB), autoCompletion.getCompletionOptions("B"));
		assertIterableEquals(Arrays.asList(wordsStartAB), autoCompletion.getCompletionOptions("ab"));

	}

	@Test
	void testRemoveIf() {
		assertEquals(4, ((AutoCompletionMapImpl) autoCompletion).removeIf(p -> p.length() == 3));
		assertEquals(5, ((AutoCompletionMapImpl) autoCompletion).removeIf(p -> p.startsWith("ab")));
		assertEquals(0, ((AutoCompletionMapImpl) autoCompletion).removeIf(p -> p.startsWith("hh")));

	}

}