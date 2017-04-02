package espace.utils;


import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoggingInterceptor {

    private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class.getName());

    @AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {
        try {
            LOGGER.log(Level.INFO, "Entering method: " + ctx.getMethod().getName());
            return ctx.proceed();
        } finally {
            LOGGER.log(Level.INFO, "Leaving method: " + ctx.getMethod().getName());
        }

    }
}
