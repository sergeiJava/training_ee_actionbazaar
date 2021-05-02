package com.actionbazaar.chat.bulletin;

public class SupportStatus {
	
	/**
     * Client backlog
     */
    private final int clientBacklog;
    
    /**
     * Total Custom Service Representatives
     */
    private final int totalCSRs;
	
    public SupportStatus(int clientBacklog, int totalCSRs) {
		this.clientBacklog = clientBacklog;
		this.totalCSRs = totalCSRs;
	}

    /**
     * Returns the client backlog
     * @return client backlog
     */
	public int getClientBacklog() {
		return clientBacklog;
	}

	/**
     * Returns total customer service representatives
     * @return customer service representatives
     */
	public int getTotalCSRs() {
		return totalCSRs;
	}
    
    
}
