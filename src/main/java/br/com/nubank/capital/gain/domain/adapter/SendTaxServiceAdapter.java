package br.com.nubank.capital.gain.domain.adapter;

import br.com.nubank.capital.gain.domain.entity.Tax;

import java.util.ArrayList;

public interface SendTaxServiceAdapter {

    void send(ArrayList<Tax> taxList);

}
