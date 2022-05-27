package com.tp.backend.enums;

public enum TokenType {
    AccountActivation("accountActivation"),
    PhoneVerificationOTP("phoneVerificationOTP"),
    EmailUpdateVerification("emailUpdateVerification"),
    ResetPasswordVerification("resetPasswordVerification");

    private String type;

    TokenType(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
