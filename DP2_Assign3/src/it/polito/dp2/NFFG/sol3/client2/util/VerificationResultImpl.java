/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client2.util;

import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.VerificationResultReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author a.zanotti
 */
public class VerificationResultImpl implements VerificationResultReader {

    private PolicyReader policy;
    private boolean result;
    private String message;
    private GregorianCalendar time;

    @Override
    public PolicyReader getPolicy() {
        return policy;
    }

    @Override
    public Boolean getVerificationResult() {
        return result;
    }

    @Override
    public String getVerificationResultMsg() {
        return message;
    }

    @Override
    public Calendar getVerificationTime() {
        return time;
    }

    void setPolicy(PolicyReader policy) {
        this.policy = policy;
    }

    void setResult(boolean result) {
        this.result = result;
    }

    void setMessage(String message) {
        this.message = message;
    }

    void setTime(GregorianCalendar time) {
        this.time = time;
    }
    
    

}
