package br.com.nubank.capital.gain.domain.entity;

import br.com.nubank.capital.gain.domain.entity.validators.Validators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class OperationSet {

    private ArrayList<Operation> operations;

    public OperationSet(ArrayList<Operation> operations) {
        Validators.validateNotEmpty(operations, "Operation array cannot be empty");
        this.operations = operations;
    }

    public List<BigDecimal> generateTax(BigDecimal operationValueCapWithoutTax, BigDecimal taxPercentage) {
        List<BigDecimal> taxList = new ArrayList<>();

        BigDecimal currentQuantity = BigDecimal.ZERO;
        BigDecimal currentAverage = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal currentLoss = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        for (Operation operation: operations) {
            BigDecimal operationTaxValue = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

            switch (operation.getOperationType()) {
                case BUY: {
                    currentAverage = calculateAverage(operation, currentQuantity, currentAverage);
                    currentQuantity = currentQuantity.add(operation.getQuantity());
                    break;
                }
                case SELL: {
                    BigDecimal profitGenerated = calculateProfit(operation, currentAverage);

                    if(operation.valueIsGreaterThan(operationValueCapWithoutTax) && valueIsGreaterThanZero(profitGenerated.add(currentLoss))) {
                        operationTaxValue = calculateTaxValue(profitGenerated, currentLoss, taxPercentage);
                    }
                    if(valueIsLessThanZero(profitGenerated) || valueIsLessThanZero(currentLoss)) {
                        currentLoss = calculateCurrentLoss(currentLoss, profitGenerated);
                    }

                    currentQuantity = currentQuantity.subtract(operation.getQuantity());
                }
            }

            taxList.add(operationTaxValue);
        }

        return taxList;
    }

    private BigDecimal calculateCurrentLoss(BigDecimal currentLoss, BigDecimal profitGenerated) {
        if(valueIsLessThanZero(currentLoss.add(profitGenerated))) {
            return currentLoss.add(profitGenerated);
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal calculateTaxValue(BigDecimal profitGenerated, BigDecimal currentLoss, BigDecimal taxPercentage) {
        return profitGenerated.add(currentLoss).multiply(taxPercentage);
    }

    private boolean valueIsGreaterThanZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean valueIsLessThanZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    private BigDecimal calculateProfit(Operation operation, BigDecimal currentAverage) {
        return operation.getOperationValue().subtract(currentAverage.multiply(operation.getQuantity()));
    }

    private BigDecimal calculateAverage(Operation operation, BigDecimal currentQuantity, BigDecimal currentAverage) {
        return currentQuantity
                .multiply(currentAverage)
                .add(operation.getOperationValue())
                .divide(currentQuantity.add(operation.getQuantity()), RoundingMode.HALF_UP);
    }
}
