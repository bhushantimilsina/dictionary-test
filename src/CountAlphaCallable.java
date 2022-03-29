import java.util.List;
import java.util.concurrent.Callable;

public class CountAlphaCallable implements Callable<Long> {

    private List<String> wordList;
    private String prefix;

    @Override
    public Long call() throws Exception {
        return wordList.stream().filter(word -> prefix.equalsIgnoreCase(word.substring(0, 1))).count();
    }

    public List<String> getWordList() {
        return wordList;
    }

    public void setWordList(List<String> wordList) {
        this.wordList = wordList;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
