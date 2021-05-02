package com.actionbazaar.util;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;

@Interceptor
@PerformanceMonitor
public class PerformanceInterceptor {

	/**
     * Default constructor - logs a message so that we know the interceptor has been instantiated.
     */
    public PerformanceInterceptor() {
        Logger.getLogger("PerformanceInterceptor").log(Level.INFO, "Performance Interceptor Instantiated.");
    }
    
    @AroundInvoke
    public Object monitor(InvocationContext ctx) throws Exception {
        long start = new Date().getTime();
        try {
            return ctx.proceed();
        } finally {
            long elapsed = new Date().getTime() - start;
            Logger.getLogger("PerformanceInterceptor").log(Level.INFO, "Elapsed time: {0}", elapsed);
        }
    } 
}
