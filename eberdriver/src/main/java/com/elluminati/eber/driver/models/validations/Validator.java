package com.elluminati.eber.driver.models.validations;

public class Validator {
    String errorMsg="";
    boolean isValid=false;

    public Validator(String errorMsg, boolean isValid) {
        this.errorMsg = errorMsg;
        this.isValid = isValid;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
