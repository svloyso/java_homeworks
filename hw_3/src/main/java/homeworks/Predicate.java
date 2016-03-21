package homeworks;

interface Predicate<A> extends Function1<A, Boolean> {
    
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

    Predicate<Object> ALWAYS_TRUE = 
        new Predicate<Object>() {
            @Override
            public Boolean invoke(Object arg) {
                return true;
            }
        };

    Predicate<Object> ALWAYS_FALSE = 
        new Predicate<Object>() {
            @Override
            public Boolean invoke(Object arg) {
                return false;
            }
        };
}
