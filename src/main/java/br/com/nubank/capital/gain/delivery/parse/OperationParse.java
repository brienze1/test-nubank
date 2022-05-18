package br.com.nubank.capital.gain.delivery.parse;

import br.com.nubank.capital.gain.delivery.dto.OperationDto;
import br.com.nubank.capital.gain.domain.entity.Operation;
import br.com.nubank.capital.gain.domain.validators.Validators;

import java.util.ArrayList;

public class OperationParse {

    private OperationParse() {
    }

    public static ArrayList<Operation> toOperationList(ArrayList<OperationDto> operationDtos) {
        ArrayList<Operation> operations = new ArrayList<>();

        if (operationDtos != null) {
            for (OperationDto operationDto : operationDtos) {
                operations.add(toOperation(operationDto));
            }
        }

        return operations;
    }

    private static Operation toOperation(OperationDto operationDto) {
        Validators.validateNotNull(operationDto, "Operation dto cannot be null");

        return new Operation(operationDto.getOperationType(), operationDto.getUnitCost(), operationDto.getQuantity());
    }

}
