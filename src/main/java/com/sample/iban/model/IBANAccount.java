package com.sample.iban.model;

public record IBANAccount(
                String accountNumber,
                String bankCode,
                String countryCode,
                String iban) {
}
