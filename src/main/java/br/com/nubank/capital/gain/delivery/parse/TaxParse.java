package br.com.nubank.capital.gain.delivery.parse;

import br.com.nubank.capital.gain.delivery.dto.TaxDto;
import br.com.nubank.capital.gain.domain.entity.Tax;
import br.com.nubank.capital.gain.domain.validators.Validators;

import java.util.ArrayList;

public class TaxParse {

    private static TaxParse instance;

    private TaxParse() {}

    public static TaxParse getInstance() {
        if(instance == null) {
            instance = new TaxParse();
        }
        return instance;
    }

    public ArrayList<TaxDto> toTaxDtoList(ArrayList<Tax> taxList) {
        ArrayList<TaxDto> taxDtoList = new ArrayList<>();

        if (taxList != null) {
            for (Tax tax : taxList) {
                taxDtoList.add(toTaxDto(tax));
            }
        }

        return taxDtoList;
    }

    private TaxDto toTaxDto(Tax tax) {
        Validators.validateNotNull(tax, "Tax dto cannot be null");

        return new TaxDto(tax.getTax());
    }
}
