package com.progressoft.corpay.rules.validation;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by u550 on 12/21/2016.
 */
public class Payment {

    private String reference;
    private BigDecimal amount;
    private String currency;
    private Date submissionDate;
    private String fromAccount;
    private String toAccount;

    public Payment(String reference, BigDecimal amount
            , String currency, Date submissionDate
            , String fromAccount, String toAccount) {
        this.reference = reference;
        this.amount = amount;
        this.currency = currency;
        this.submissionDate = submissionDate;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }
}
