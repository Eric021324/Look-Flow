package com.lookflow.domain.model.valueobject;

import com.lookflow.domain.exception.DomainException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    private final String value;

    public Email(String value) {
        if (value == null || value.isBlank()) {
            throw new DomainException("Email cannot be null or empty");
        }
        String trimmed = value.trim();
        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            throw new DomainException("Invalid email format: " + trimmed);
        }
        this.value = trimmed;
    }

    public String getValue() {
        return value;
    }

    // Comparación por valor
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email = (Email) o;
        return value.equalsIgnoreCase(email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toLowerCase());
    }

    @Override
    public String toString() {
        return value;
    }
}
