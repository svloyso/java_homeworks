package homeworks;

public interface Function2<A1, A2, R> {
    R invoke(A1 arg1, A2 arg2);

    default <RR> Function2<A1, A2, RR> compose(Function1<? super R, RR> g) {
        return new Function2<A1, A2, RR>() {
            @Override
            public RR invoke(A1 arg1, A2 arg2) {
                return g.invoke(Function2.this.invoke(arg1, arg2));
            }
        };
    }

    default Function1<A2, R> bind1(A1 arg1) {
        return new Function1<A2, R>() {
            @Override
            public R invoke(A2 arg2) {
                return Function2.this.invoke(arg1, arg2);
            }
        };
    }
    default Function1<A1, R> bind2(A2 arg2) {
        return new Function1<A1, R>() {
            @Override
            public R invoke(A1 arg1) {
                return Function2.this.invoke(arg1, arg2);
            }
        };
    }
    default Function1<A1, Function1<A2, R> > curry() {
        return new Function1<A1, Function1<A2, R> >() {
            @Override
            public Function1<A2, R> invoke(A1 arg1) {
                return new Function1<A2, R>() {
                    @Override
                    public R invoke(A2 arg2) {
                        return Function2.this.invoke(arg1, arg2);
                    }
                };
            }
        };
    }
}
