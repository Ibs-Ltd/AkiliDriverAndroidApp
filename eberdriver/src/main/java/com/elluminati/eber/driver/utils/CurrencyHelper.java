package com.elluminati.eber.driver.utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;

public class CurrencyHelper {
    private static CurrencyHelper currencyHelper;
    private Currency currency;

    private CurrencyHelper() {
    }

    public static CurrencyHelper getInstance(Context context) {
        if (currencyHelper == null) {
            currencyHelper = new CurrencyHelper();
            return currencyHelper;
        } else {
            return currencyHelper;
        }
    }

    public NumberFormat getCurrencyFormat(String currencyCode) {
        if (currency != null && currency.getCurrencyCode().equals(currencyCode)) {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
            numberFormat.setMinimumFractionDigits(0);
            numberFormat.setCurrency(currency);
            return numberFormat;
        }

        if (TextUtils.isEmpty(currencyCode)) {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
            numberFormat.setMinimumFractionDigits(0);
            return numberFormat;
        } else {
            Set<Currency> currencies = Currency.getAvailableCurrencies();
            for (Currency currency : currencies) {
                if (TextUtils.equals(currencyCode, currency.getCurrencyCode())) {
                    this.currency = currency;
                    break;
                }
            }
            if (currency != null) {
                NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
                numberFormat.setMinimumFractionDigits(0);
                numberFormat.setCurrency(currency);
                return numberFormat;
            } else {
                NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
                numberFormat.setMinimumFractionDigits(0);
                return numberFormat;
            }
        }
    }
}