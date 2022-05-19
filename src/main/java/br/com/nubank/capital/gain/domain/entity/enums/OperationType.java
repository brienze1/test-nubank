package br.com.nubank.capital.gain.domain.entity.enums;

public enum OperationType {

    BUY("buy"),
    SELL("sell");

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
