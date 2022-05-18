package br.com.nubank.capital.gain.domain.entity;

import java.math.BigDecimal;

public class Tax {
    private BigDecimal tax;

    public Tax() {
    }

    public Tax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTax() {
        return tax;
    }
}
