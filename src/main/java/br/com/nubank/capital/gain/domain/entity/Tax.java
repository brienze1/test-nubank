package br.com.nubank.capital.gain.domain.entity;

import br.com.nubank.capital.gain.domain.entity.validators.Validators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Tax {

    private BigDecimal tax;

    public Tax(BigDecimal tax) {
        Validators.validatePositive(tax, "Tax value needs to be positive");
        this.tax = tax.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTax() {
        return tax.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tax)) return false;
        Tax tax1 = (Tax) o;
        return Objects.equals(getTax(), tax1.getTax());
    }

}
