package homeworks;

public interface Function1<A, R> {
    
    R invoke(A arg);

    default <RR> Function1<A,RR> compose(Function1<? super R, RR> g) {
        return arg -> g.invoke(invoke(arg));
    }
}
