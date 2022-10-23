package com.supremecorp.serviceregistrationserver.microservices.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

@Getter
@Setter
public class SearchCriteria {
    private String accountNumber;
    private String searchText;

    public boolean isValid() {
        if (StringUtils.hasText(accountNumber))
            return !(StringUtils.hasText(searchText));
        else
            return (StringUtils.hasText(searchText));
    }

    public boolean validate(Errors errors) {
        if (StringUtils.hasText(accountNumber)) {
            if (accountNumber.length() != 9)
                errors.rejectValue("accountNumber", "badFormat",
                        "Account number should be 9 digits");
            else {
                try {
                    Integer.parseInt(accountNumber);
                } catch (NumberFormatException e) {
                    errors.rejectValue("accountNumber", "badFormat",
                            "Account number should be 9 digits");
                }
            }

            if (StringUtils.hasText(searchText)) {
                errors.rejectValue("searchText", "nonEmpty",
                        "Cannot specify account number and search text");
            }
        } else if (StringUtils.hasText(searchText)) {
            ; // Nothing to do
        } else {
            errors.rejectValue("accountNumber", "nonEmpty",
                    "Must specify either an account number or search text");

        }

        return errors.hasErrors();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return (StringUtils.hasText(accountNumber) ? "number: " + accountNumber
                : "")
                + (StringUtils.hasText(searchText) ? " text: " + searchText
                : "");
    }
}
