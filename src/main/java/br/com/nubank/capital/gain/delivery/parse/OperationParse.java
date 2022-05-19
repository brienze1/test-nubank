package br.com.nubank.capital.gain.delivery.parse;

import br.com.nubank.capital.gain.delivery.dto.OperationDto;
import br.com.nubank.capital.gain.domain.entity.Operation;
import br.com.nubank.capital.gain.domain.entity.validators.Validators;

import java.util.ArrayList;

public class OperationParse {

    private static OperationParse instance;

    private OperationParse() {}

    public static OperationParse getInstance() {
        if(instance == null) {
            instance = new OperationParse();
        }
        return instance;
    }

    public ArrayList<Operation> toOperationList(ArrayList<OperationDto> operationDtoList) {
        ArrayList<Operation> operations = new ArrayList<>();

        if (operationDtoList != null) {
            for (OperationDto operationDto : operationDtoList) {
                operations.add(toOperation(operationDto));
            }
        }

        return operations;
    }

    private Operation toOperation(OperationDto operationDto) {
        Validators.validateNotNull(operationDto, "Operation dto cannot be null");

        return new Operation(operationDto.getOperationType(), operationDto.getUnitCost(), operationDto.getQuantity());
    }

}
