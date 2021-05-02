package com.actionbazaar.util;

public enum ResourceBundleKeys {
	step3_skippedSteps("step3_skippedSteps"),
    step1_emailMustMatch("step1_emailMustMatch"),
    step2_invalidPhoneNumber("step2_invalidPhoneNumber"),
    step3_selectCardType("step3_selectCardType"),
    step3_confirmationEmail("step3_confirmationEmail");

    /**
     * Key
     */
    private String key;

    /**
     * Creates a new enumeration
     * @param key - resource key
     */
    private ResourceBundleKeys(String key) {
        this.key = key;
    }

    /**
     * Returns the key
     * @return key
     */
    public String getKey() {
        return key;
    }
}
