package bosek.joachim.crypto.application.domain.service;

class UnknownSymbolException extends RuntimeException {
    UnknownSymbolException(String symbol) {
        super("Unknown symbol: " + symbol);
    }
}
