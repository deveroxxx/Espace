package espace.exceptions;

import org.apache.log4j.Logger;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {
    private static final Logger log = Logger.getLogger(CustomExceptionHandler.class.getCanonicalName());
    private ExceptionHandler wrapped;

    CustomExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {

        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            // get the exception from context
            Throwable t = context.getException();

            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

            //handling the exception
            try {
                log.error("Unhandled exception!", t);
            } finally {
                //remove it from queue
                i.remove();
            }
            //FIXME: ez az égetett /faces/ nem épp szerencsés
            try {
                ec.redirect(ec.getRequestContextPath() + "/faces/Shared/globalError.xhtml");
                break;
            } catch (IOException e) {
                log.error("IOException on redirect!", t);
            }

        }
        //parent hanle
        getWrapped().handle();
    }
}