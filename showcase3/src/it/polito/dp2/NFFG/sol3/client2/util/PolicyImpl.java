/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client2.util;

import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.VerificationResultReader;

/**
 *
 * @author a.zanotti
 */
public class PolicyImpl extends NamedEntityImpl implements PolicyReader {

    private NffgReader nffg;
    private VerificationResultReader result;
    private boolean positive;

    @Override
    public NffgReader getNffg() {
        return nffg;
    }

    @Override
    public VerificationResultReader getResult() {
        return result;
    }

    @Override
    public Boolean isPositive() {
        return positive;
    }

    void setNffg(NffgReader nffg) {
        this.nffg = nffg;
    }

    void setVerificationResult(VerificationResultReader result) {
        this.result = result;
    }

    void setPositive(boolean positive) {
        this.positive = positive;
    }

}
