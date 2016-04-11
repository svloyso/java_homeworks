package sp;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Random;
import java.io.IOException;
import java.lang.RuntimeException;

public final class SecondPartTasks {

    private SecondPartTasks() {}

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {
        return paths.stream()
                    .flatMap(p -> { 
                        Stream<String> lines; 
                        try { 
                            lines = Files.lines(Paths.get(p)); 
                        } catch (IOException e) { 
                            throw new RuntimeException(e); 
                        } return lines;
                    })
                    .filter(s -> s.contains(sequence))
                    .collect(Collectors.toList());
    }

    static class DoublePair {
        double left;
        double right;
        public DoublePair(double leftValue, double rightValue) { setLeft(leftValue); setRight(rightValue); }
        public double getLeft() { return left; }
        public double getRight() { return right; }
        public void setLeft(double newLeft) { left = newLeft; }
        public void setRight(double newRight) { right = newRight; }
    };

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        long N = 1000000;
        double D = Integer.MAX_VALUE;
        Random rand = new Random();
        return Stream.generate(() -> new DoublePair(rand.nextInt()/D, rand.nextInt()/D)).limit(N).filter(p -> p.getLeft() * p.getLeft() + p.getRight() * p.getRight() < 1).count() / (double)N;
    }

    static class Pair<L, R> {
        L left;
        R right;
        public Pair(L leftValue, R rightValue) { setLeft(leftValue); setRight(rightValue); }
        public L getLeft() { return left; }
        public R getRight() { return right; }
        public void setLeft(L newLeft) { left = newLeft; }
        public void setRight(R newRight) { right = newRight; }
    };

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        return compositions.entrySet().stream().map(e -> new Pair<String, Long>(e.getKey(), e.getValue().stream()
                                                                                                          .mapToLong(s -> s.length())
                                                                                                          .sum()))
                                               .max(Comparator.comparing(Pair::getRight))
                                               .map(Pair::getLeft)
                                               .orElse("");
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders.stream().reduce((m1, m2) -> { 
            m2.entrySet().stream().forEach(e -> {
                String key = e.getKey();
                int val = e.getValue();
                m1.put(key, val + (m1.containsKey(key) ? m1.get(key) : 0));
            }); return m1; }).orElse(new HashMap<String, Integer>());
    }
}
