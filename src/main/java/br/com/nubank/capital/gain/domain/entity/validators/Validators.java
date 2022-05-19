package br.com.nubank.capital.gain.domain.entity.validators;

import br.com.nubank.capital.gain.domain.exception.ValidationException;

import java.util.ArrayList;
import java.util.Optional;

public class Validators {

    private Validators() {}

    public static <T> void validateNotNull(T object, String message) {
        Optional.ofNullable(object).orElseThrow(() -> new ValidationException(message));
    }

    public static <T> void validateNotEmpty(ArrayList<T> array, String message) {
        Optional.ofNullable(array).filter(a -> !a.isEmpty()).orElseThrow(() -> new ValidationException(message));
    }

    public static <T extends Number> void validateGreaterThanZero(T value, String message) {
        Optional.ofNullable(value).filter(v -> v.doubleValue() > 0).orElseThrow(() -> new ValidationException(message));
    }

    public static <T extends Number> void validatePositive(T value, String message) {
        Optional.ofNullable(value).filter(v -> v.doubleValue() >= 0).orElseThrow(() -> new ValidationException(message));
    }

}
