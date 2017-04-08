package espace.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Calendar;


@Interceptor
@Log
public class LoggingInterceptor {

    @AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {

        String cn = ctx.getMethod().getDeclaringClass().getCanonicalName();
        String mn = ctx.getMethod().getName();

        LogLevel l = LogLevel.INFO;
        if (ctx.getMethod().getAnnotation(Log.class) != null) {
            l = ctx.getMethod().getAnnotation(Log.class).level();
        } else if (ctx.getMethod().getDeclaringClass().getAnnotation(Log.class) != null) {
            l = ctx.getMethod().getDeclaringClass().getAnnotation(Log.class).level();
        }

        LogService.log(l, cn, ">> " + mn + " - [" + paramsFormatter(ctx) + "]");
        try {
            Object result = ctx.proceed();
            LogService.log(l, cn,"<< " + mn + " - [" + paramsFormatter(ctx) + "]");
            return result;
        } catch (Exception e) {
            LogService.log(LogLevel.WARN, cn , "--- Exception thrown by: " + mn + ", Exception: "
                    + e.getClass().getSimpleName() + ", Message: " + e.getMessage());
            throw e;
        }
    }


    private String paramsFormatter(InvocationContext ctx) {
        StringBuilder paramString = new StringBuilder();
        String delim = "";
        for (Object o : ctx.getParameters()) {
            paramString.append(delim);
            if (o instanceof Calendar) {
                paramString.append(((Calendar) o).getTime().toString());
            } else {
                paramString.append(o);
            }
            delim = ", ";
        }
        return paramString.toString();
    }
}
