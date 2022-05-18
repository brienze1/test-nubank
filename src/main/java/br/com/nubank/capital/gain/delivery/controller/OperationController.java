package br.com.nubank.capital.gain.delivery.controller;


import br.com.nubank.capital.gain.delivery.dto.OperationDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;

public class OperationController {

    private static OperationController instance;

    private OperationController() {}

    public static OperationController getInstance() {
        if(instance == null) {
            instance = new OperationController();
        }
        return instance;
    }

    private ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
            .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

    public void execute(String operations) {
        try{
            ArrayList<OperationDto> operationsDto = mapper.readValue(operations, new TypeReference<>(){});



            String value = mapper.writeValueAsString(operationsDto);

            System.out.println("[{\"tax\": 0.00},{\"tax\": 10000.00},{\"tax\": 0.00}]");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
