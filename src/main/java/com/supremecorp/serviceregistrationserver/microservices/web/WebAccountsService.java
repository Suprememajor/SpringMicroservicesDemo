package com.supremecorp.serviceregistrationserver.microservices.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class WebAccountsService {

    protected final RestTemplate restTemplate;
    protected String serviceUrl;

    protected Logger logger = LoggerFactory.getLogger(WebAccountsService.class);

    public WebAccountsService(String serviceUrl, @LoadBalanced RestTemplate restTemplate) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
        this.restTemplate = restTemplate;
    }

    public void demoOnly() {
        logger.warn("The RestTemplate request factory is " + restTemplate.getRequestFactory().getClass());
    }

    public Account findByNumber(String accountNumber) {

        logger.info("findByNumber() invoked: for " + accountNumber);
        try {
            logger.info("In try");
            logger.info("In try");
            logger.info("In try");
            logger.info("In try");
            logger.info("In try");
            logger.info(serviceUrl + "/accounts/" + accountNumber);
            logger.info(serviceUrl + "/accounts/" + accountNumber);
            logger.info(serviceUrl + "/accounts/" + accountNumber);
            return restTemplate.getForObject(serviceUrl + "/accounts/" + accountNumber, Account.class, accountNumber);
        } catch (Exception e) {
            logger.info("In catch");
            logger.info("In catch");
            logger.info("In catch");
            logger.info("In catch");
            logger.info("In catch");
            logger.warn(e.getClass() + ": " + e.getLocalizedMessage());
            return null;
        }

    }

    public List<Account> byOwnerContains(String name) {
        logger.info("byOwnerContains() invoked:  for " + name);
        Account[] accounts = null;

        try {
            accounts = restTemplate.getForObject(serviceUrl + "/accounts/owner/{name}", Account[].class, name);
        } catch (HttpClientErrorException e) { // 404
            // Nothing found
        }

        if (accounts == null || accounts.length == 0) return null;
        else return Arrays.asList(accounts);
    }

    public Account getByNumber(String accountNumber) {
        Account account = restTemplate.getForObject(serviceUrl + "/accounts/{number}", Account.class, accountNumber);

        if (account == null) throw new AccountNotFoundException(accountNumber);
        else return account;
    }
}
