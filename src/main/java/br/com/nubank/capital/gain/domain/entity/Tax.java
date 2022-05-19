package br.com.nubank.capital.gain.domain.entity;

import br.com.nubank.capital.gain.domain.entity.validators.Validators;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Tax {
    private final BigDecimal tax;

    public Tax(BigDecimal tax) {
        Validators.validatePositive(tax, "Tax value needs to be positive");
        this.tax = tax.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTax() {
        return tax.setScale(2, RoundingMode.HALF_UP);
    }
}
