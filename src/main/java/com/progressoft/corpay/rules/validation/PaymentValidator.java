package com.progressoft.corpay.rules.validation;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by u550 on 12/21/2016.
 */
public class PaymentValidator {

    public boolean validate(Payment payment) {
        Calendar calendar = Calendar.getInstance();
        Calendar submissionDate = Calendar.getInstance();
        if (payment == null)
            throw new PaymentNullException();
        submissionDate.setTime(payment.getSubmissionDate());
        if(isValidDate(calendar,submissionDate))
            return false;
        if (!isSameYear(calendar, submissionDate))
            return false;
        if (!isSameMonth(calendar, submissionDate))
            return false;
        if (isPrevDay(calendar, submissionDate))
            return false;
        return true;
    }

    private boolean isValidDate(Calendar calendar, Calendar submissionDate) {
        if (!isSameYear(calendar, submissionDate))
            return false;
        if (!isSameMonth(calendar, submissionDate))
            return false;
        if (isPrevDay(calendar, submissionDate))
            return false;
        return true;
    }

    private boolean isPrevDay(Calendar calendar, Calendar submissionDate) {
        return calendar.get(Calendar.DAY_OF_MONTH) >  submissionDate.get(Calendar.DAY_OF_MONTH);
    }

    private boolean isSameMonth(Calendar calendar, Calendar submissionDate) {
        return calendar.get(Calendar.MONTH) == submissionDate.get(Calendar.MONTH);
    }

    private boolean isSameYear(Calendar calendar, Calendar submissionDate) {
        return calendar.get(Calendar.YEAR) == submissionDate.get(Calendar.YEAR);
    }
}
