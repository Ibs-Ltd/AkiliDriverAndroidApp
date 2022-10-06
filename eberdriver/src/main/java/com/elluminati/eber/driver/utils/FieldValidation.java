package com.elluminati.eber.driver.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.models.validations.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidation {


    public static Validator isEmailValid(Context context, String email) {
        if (TextUtils.isEmpty(email)) {
            return new Validator(context.getString(R.string.msg_enter_email), false);
        } else if (!eMailValidation(email)) {
            return new Validator(context.getString(R.string.msg_enter_valid_email), false);
        } else if (email.length() < 12) {
            return new Validator(context.getString(R.string.msg_enter_valid_email_min_max_length), false);
        } else if (email.length() > 64) {
            return new Validator(context.getString(R.string.msg_enter_valid_email_min_max_length), false);
        } else {
            return new Validator("", true);
        }
    }

    public static Validator isPhoneValid(Context context, String phone, int phoneNumberLength, int phoneNumberMinLength) {
        int phoneLength = phone.length();
        if (TextUtils.isEmpty(phone)) {
            return new Validator(context.getString(R.string.msg_enter_number), false);
        } else if (phone.startsWith("0")) {
            return new Validator(context.getString(R.string.msg_enter_valid_number), false);
        } else if (phoneLength > phoneNumberLength || phoneLength < phoneNumberMinLength) {
            return new Validator(validPhoneNumberMessage(phoneNumberMinLength, phoneNumberLength, context), false);
        } else {
            return new Validator("", true);
        }
    }


    public static Validator isPasswordValid(Context context, String password) {
        if (TextUtils.isEmpty(password)) {
            return new Validator(context.getString(R.string.msg_enter_password), false);
        } else if (password.length() < 6) {
            return new Validator(context.getString(R.string.msg_enter_valid_password), false);
        } else if (password.length() > 20) {
            return new Validator(context.getString(R.string.msg_enter_valid_password), false);
        } else {
            return new Validator("", true);
        }
    }


    public static String validPhoneNumberMessage(int minDigit, int maxDigit, Context context) {
        if (maxDigit == minDigit) {
            return context.getResources().getString(R.string.msg_please_enter_valid_mobile_number, maxDigit);
        } else {
            return context.getResources().getString(R.string.msg_please_enter_valid_mobile_number_between, minDigit, maxDigit);
        }
    }

    public static boolean eMailValidation(String emailstring) {
        if (null == emailstring || emailstring.length() == 0) {
            return false;
        }

        Pattern emailPattern = Pattern.compile("^(?:(?:(?:(?: )*(?:(?:(?:\\t| )*\\r\\n)?(?:\\t| )+))+(?: )*)|(?: )+)?(?:(?:(?:[-A-Za-z0-9!#$%&’*+/=?^_'{|}~]+(?:\\.[-A-Za-z0-9!#$%&’*+/=?^_'{|}~]+)*)|" +
                "(?:\"(?:(?:(?:(?: )*(?:(?:[!#-Z^-~]|\\[|\\])|(?:\\\\(?:\\t|[ -~]))))+(?: )*)|(?: )+)\"))(?:@)(?:(?:(?:[A-Za-z0-9](?:[-A-Za-z0-9]{0,61}[A-Za-z0-9])?)(?:\\.[A-Za-z0-9](?:[-A-Za-z0-9]" +
                "{0,61}[A-Za-z0-9])?)*)))(\\.[A-Za-z]{2,})$");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }

}
