package edu.ccrm.service;

public class MaxCreditLimitExceededException extends RuntimeException {
    public MaxCreditLimitExceededException(String msg){ super(msg); }
}
