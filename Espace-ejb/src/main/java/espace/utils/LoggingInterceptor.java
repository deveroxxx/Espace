package espace.utils;


import espace.annotations.ExcludeFromLog;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Calendar;


@Interceptor
@Log
@Priority(100)
public class LoggingInterceptor {

    @AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {

        //Exclude esetén nem logolunk
        if (ctx.getMethod().getAnnotation(ExcludeFromLog.class) != null ||
                ctx.getMethod().getDeclaringClass().getAnnotation(ExcludeFromLog.class) != null) {
            return ctx.proceed();
        }

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
        if (ctx.getParameters() != null) {
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
        } else {
            return "";
        }
    }
}
