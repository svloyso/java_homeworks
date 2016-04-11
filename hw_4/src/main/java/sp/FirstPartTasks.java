package sp;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;

public final class FirstPartTasks {

    private FirstPartTasks() {}

    // Список названий альбомов
    public static List<String> allNames(Stream<Album> albums) {
        return albums.map(a -> a.getName()).collect(Collectors.toList());
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    public static List<String> allNamesSorted(Stream<Album> albums) {
        return albums.map(a -> a.getName()).sorted().collect(Collectors.toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    public static List<String> allTracksSorted(Stream<Album> albums) {
        return albums.flatMap(a -> a.getTracks().stream().map(t -> t.getName())).sorted().collect(Collectors.toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    public static List<Album> sortedFavorites(Stream<Album> s) {
        return s.filter(a -> a.getTracks().stream().anyMatch(t -> t.getRating() > 95)).sorted(Comparator.comparing(Album::getName)).collect(Collectors.toList());
    }

    // Сгруппировать альбомы по артистам
    public static Map<Artist, List<Album>> groupByArtist(Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist));
    }

    // Сгруппировать альбомы по артистам (в качестве значения вместо объекта 'Artist' использовать его имя)
    public static Map<Artist, List<String>> groupByArtistMapName(Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist, Collectors.mapping(Album::getName, Collectors.toList())));
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(Stream<Album> albums) {
        class Counter {
            long state;
            public Counter() { state = 0; }
            public void increment() { state += 1; }
            public long getState() { return state; }
        };
        Counter c = new Counter();
        long distinct = albums.peek(e -> c.increment()).distinct().count();
        return c.getState() - distinct;
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
    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    public static Optional<Album> minMaxRating(Stream<Album> albums) {
        return albums.map(a -> new Pair<Album, Integer>(a, a.getTracks().stream()
                                                                        .mapToInt(Track::getRating)
                                                                        .max()
                                                                        .orElse(0)))
                     .min(Comparator.comparingInt(Pair::getRight))
                     .map(Pair::getLeft);
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    public static List<Album> sortByAverageRating(Stream<Album> albums) {
        return albums.map(a -> new Pair<Album, Double>(a, a.getTracks().stream()
                                                                       .mapToInt(Track::getRating)
                                                                       .average()
                                                                       .orElse(0.0)))
                     .sorted(Collections.reverseOrder(Comparator.comparing(Pair::getRight)))
                     .map(Pair::getLeft)
                     .collect(Collectors.toList());
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(IntStream stream, int modulo) {
        return stream.reduce(1, (x, y) -> x * y % modulo);
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    public static String joinTo(String... strings) {
        return Arrays.stream(strings).collect(Collectors.joining(", ", "<", ">"));
    }

    // Вернуть поток из объектов класса 'clazz'
    public static <R> Stream<R> filterIsInstance(Stream<?> s, Class<R> clazz) {
        return s.filter(c -> clazz.isInstance(c)).map(c -> clazz.cast(c));
    }
}
