package java8;

import lombok.Getter;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-09-09
 **/
public class SpliteratorTest {
    /**
     * 方法一：函数版本
     * @param s
     * @return
     */
    public int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) {
                    counter++;
                    lastSpace = false;
                }
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        int count = new SpliteratorTest().countWordsIteratively("abc sdf 123");
        System.out.println(count
        );

        final String SENTENCE =
            " Nel   mezzo del cammin  di nostra  vita " +
                "mi  ritrovai in una  selva oscura" +
                " ché la  dritta via era   smarrita ";
        Stream<Character> characterStream = IntStream.range(0, SENTENCE.length())
            .mapToObj(SENTENCE::charAt);

//        int i = new Counter(0,false).countWords(characterStream);
//        System.out.println(i);

        System.out.println("Found " + new Counter(0, false).countWords(characterStream) + " words");

        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> stream = StreamSupport.stream(spliterator, true);

        System.out.println("Found " + new Counter(0, false).countWords(stream) + " words");
    }
}

/**
 * 方法二：流式，lastIsSpace 表示前面是不是空格
 */
@Getter
class Counter {
    private final int counter;
    private final boolean lastIsSpace;

    public Counter(int counter, boolean lastIsSpace) {
        this.counter = counter;
        this.lastIsSpace = lastIsSpace;
    }

    public Counter acculate(Character c) {
        if (Character.isWhitespace(c)) {
            return this.lastIsSpace ? this : new Counter(counter, true);
        } else {
            return this.lastIsSpace ? new Counter(counter + 1, false) : this;
        }
    }

    public Counter combie(Counter param) {
        return new Counter(this.counter + param.getCounter(), param.isLastIsSpace());
    }

    public int countWords(Stream<Character> stream) {
        Counter reduce = stream.reduce(new Counter(0, true), (p, p2) -> p.acculate(p2), Counter::combie);
//        Counter reduce = stream.reduce(new Counter(0,true),(p,p2) -> p.acculate(p2));
        return reduce.getCounter();
    }
}

/**
 * 方法三：分割器
 */
class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;

    /**
     * 这个变量在trySplit中没什么卵用
     * 在 tryAdvance 中需要判断是不是还有要遍历的Character
     */
    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        System.out.println(string);
        this.string = string;
    }

    /**
     * 这个currentChar变量的值，是上一次切分出去的WordCounterSpliterator的末位值
     * 和这次的string无关，还是可以判断的
     * @param action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++));
        return currentChar < string.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        if (currentSize < 10) {
            return null;
        }
        for (int splitPos = currentSize / 2 + currentChar;
             splitPos < string.length(); splitPos++) {
            if (Character.isWhitespace(string.charAt(splitPos))) {
                Spliterator<Character> spliterator =
                    new WordCounterSpliterator(string.substring(currentChar, splitPos));
                currentChar = splitPos;
                return spliterator;

            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}

