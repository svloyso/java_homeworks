package homeworks;

public interface Predicate<A> extends Function1<A, Boolean> {
    
    default Predicate<A> or(Predicate<? super A> p) {
        return new Predicate<A>() {
            @Override
            public Boolean invoke(A arg) {
                return Predicate.this.invoke(arg) || p.invoke(arg);
            }
        };
    }

    default Predicate<A> and(Predicate<? super A> p) {
        return new Predicate<A>() {
            @Override
            public Boolean invoke(A arg) {
                return Predicate.this.invoke(arg) && p.invoke(arg);
            }
        };
    }

    default Predicate<A> not() {
        return new Predicate<A>() {
            @Override
            public Boolean invoke(A arg) {
                return !Predicate.this.invoke(arg);
            }
        };
    }

    Predicate<Object> ALWAYS_TRUE = x -> true;

    Predicate<Object> ALWAYS_FALSE = x -> false;
}
