package com.sample.trade.model;

public record IBANAccount(
                String accountNumber,
                String bankCode,
                String countryCode,
                String iban) {
}
