package br.com.nubank.capital.gain.domain.validators;

import br.com.nubank.capital.gain.domain.exception.ValidationException;

import java.util.ArrayList;
import java.util.Optional;

public class Validators {

    public static <T> T validateNotNull(T object, String message) {
        return Optional.ofNullable(object).orElseThrow(() -> new ValidationException(message));
    }

    public static <T> void validateNotEmpty(ArrayList<T> array, String message) {
        Optional.ofNullable(array).filter(a -> !a.isEmpty()).orElseThrow(() -> new ValidationException(message));
    }

    public static <T extends Number> T validateGreaterThanZero(T value, String message) {
        return Optional.ofNullable(value).filter(v -> v.doubleValue() > 0).orElseThrow(() -> new ValidationException(message));
    }

    public static <T extends Number> T validatePositive(T value, String message) {
        return Optional.ofNullable(value).filter(v -> v.doubleValue() >= 0).orElseThrow(() -> new ValidationException(message));
    }

}
