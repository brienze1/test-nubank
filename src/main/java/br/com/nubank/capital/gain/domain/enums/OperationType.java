package br.com.nubank.capital.gain.domain.enums;

public enum OperationType {

    BUY("buy"),
    SELL("sell");

    private String value;

    OperationType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
