package br.com.nubank.capital.gain.delivery.parse;

import br.com.nubank.capital.gain.delivery.dto.TaxDto;
import br.com.nubank.capital.gain.domain.entity.Tax;
import br.com.nubank.capital.gain.domain.validators.Validators;

import java.util.ArrayList;

public class TaxParse {

    private TaxParse() {}

    public static ArrayList<TaxDto> toTaxDtos(ArrayList<Tax> taxes) {
        Validators.validateNotNull(taxes, "Taxes cannot be null");


        return null;
    }
}
