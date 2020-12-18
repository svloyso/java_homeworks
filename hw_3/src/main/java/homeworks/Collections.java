package homeworks;

import java.util.ArrayList;
import java.util.Iterator;

public class Collections {

    public static <A, R> ArrayList<R> map(Function1<? super A, R> f, Iterable<A> a) {
        ArrayList<R> result = new ArrayList<R>();
        for (A elem : a) {
            result.add(f.invoke(elem));
        }
        return result;
    }

    public static <A> ArrayList<A> filter(Predicate<? super A> p, Iterable<A> a) {
        ArrayList<A> result = new ArrayList<A>();
        for (A elem : a) {
            if (p.invoke(elem)) {
                result.add(elem);
            }
        }
        return result;
    }

    public static <A> ArrayList<A> takeWhile(Predicate<? super A> p, Iterable<A> a) {
        ArrayList<A> result = new ArrayList<A>();
        for (A elem : a) {
            if (!p.invoke(elem)) {
                break;
            }
            result.add(elem);
        }
        return result;
    }

    public static <A> ArrayList<A> takeUnless(Predicate<? super A> p, Iterable<A> a) {
        return takeWhile(p.not(), a);
    }

    public static <A, B> B foldl(Function2<? super B, ? super A, ? extends B> f, B init, Iterable<A> list) {
        for (A elem : list) {
            init = f.invoke(init, elem);
        }
        return init;
    }

    public static <A, B> B foldr(Function2<? super A, ? super B, ? extends B> f, B init, Iterable<A> list) {
        return foldr(f, init, list.iterator());
    }

    private static <A, B> B foldr(Function2<? super A, ? super B, ? extends B> f, B init, Iterator<A> it) {
        if (it.hasNext()) {
            A next = it.next();
            return f.invoke(next, foldr(f, init, it));
        }
        return init;
    }
};
