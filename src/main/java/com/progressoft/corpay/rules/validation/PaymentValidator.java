package com.progressoft.corpay.rules.validation;

import java.util.Calendar;
import java.util.Date;

public class PaymentValidator {

    public static final String NO_RULE = "NO_RULE";
    public static final String SAME_DAY = "SAME_DAY";
    public static final String SAME_MONTH = "SAME_MONTH";
    public static final String SAME_YEAR = "SAME_YEAR";
    private Calendar calendar;
    private Calendar submissionDate;

    public boolean validate(Payment payment) {
        calendar = Calendar.getInstance();
        submissionDate = Calendar.getInstance();
        if (payment == null)
            throw new PaymentNullException();
        if (payment.getFromAccount() == null)
            throw new NullAccountException("Account is null");
        if (payment.getFromAccount().getRule() == null)
            throw new NullRuleException("Rule is null");

        submissionDate.setTime(payment.getSubmissionDate());

        if(isOldDate()||isNextYear())
            return false;
        return checkDateBaseOnRules(payment);
    }

    private boolean isNextYear() {
        return submissionDate.get(Calendar.YEAR)>  calendar.get(Calendar.YEAR) ;
    }

    private boolean checkDateBaseOnRules(Payment payment) {
            String range = payment.getFromAccount().getRule().getRange();
            if (range.equals(NO_RULE) || range.equals(SAME_DAY))
                return isSameDay();
            if (range.equals(SAME_MONTH))
                return isSameMonth();
            if (range.equals(SAME_YEAR))
                return isSameYear();
        return false;
    }

    private boolean isOldDate() {
        return isPrevYear() || isPrevMonth() || isPrevDay();
    }

    private boolean isPrevYear() {
        return calendar.get(Calendar.YEAR) > submissionDate.get(Calendar.YEAR);
    }

    private boolean isPrevMonth() {
        return calendar.get(Calendar.MONTH) > submissionDate.get(Calendar.MONTH);
    }


    private boolean isValidDate() {
        if (!isSameYear())
            return false;
        if (!isSameMonth())
            return false;
        if (isPrevDay())
            return false;
        return true;
    }

    private boolean isPrevDay() {
        return calendar.get(Calendar.DAY_OF_MONTH) > submissionDate.get(Calendar.DAY_OF_MONTH);
    }

    private boolean isSameMonth() {
        return calendar.get(Calendar.MONTH) == submissionDate.get(Calendar.MONTH);
    }
    private boolean isSameDay() {
        return calendar.get(Calendar.DAY_OF_MONTH) == submissionDate.get(Calendar.DAY_OF_MONTH);
    }

    private boolean isSameYear() {
        return calendar.get(Calendar.YEAR) == submissionDate.get(Calendar.YEAR);
    }
}
