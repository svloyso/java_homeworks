package homeworks;

public interface Function1<A, R> {
    
    R invoke(A arg);

    default <RR> Function1<A,RR> compose(Function1<? super R, RR> g) {
        //return arg -> g.invoke(invoke(arg));

        return new Function1<A, RR>() {
            @Override
            public RR invoke(A arg) {
                return g.invoke(Function1.this.invoke(arg));
            }
        };
    }
}
