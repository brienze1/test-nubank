package br.com.nubank.capital.gain.delivery.controller;


import br.com.nubank.capital.gain.delivery.dto.OperationDto;
import br.com.nubank.capital.gain.delivery.parse.OperationParse;
import br.com.nubank.capital.gain.domain.entity.Operation;
import br.com.nubank.capital.gain.domain.entity.OperationSet;
import br.com.nubank.capital.gain.domain.exception.OperationException;
import br.com.nubank.capital.gain.domain.usecase.CalculateTaxUseCase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;

public class OperationController {

    private static OperationController instance;

    private OperationController() {
    }

    public static OperationController getInstance() {
        if (instance == null) {
            instance = new OperationController();
        }
        return instance;
    }

    private ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
            .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
    private OperationParse operationParse = OperationParse.getInstance();
    private CalculateTaxUseCase calculateTaxUseCase = CalculateTaxUseCase.getInstance();

    public void execute(String operationString) {
        try {
            ArrayList<OperationDto> operationDtoList = mapper.readValue(operationString, new TypeReference<>() {});

            ArrayList<Operation> operationList = operationParse.toOperationList(operationDtoList);

            calculateTaxUseCase.calculateTax(new OperationSet(operationList));
        } catch (Exception ex) {
            throw new OperationException(ex);
        }
    }

}
