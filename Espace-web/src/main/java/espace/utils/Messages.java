package espace.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Messages {

    private static FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    private static FacesMessage createMessage(
            FacesMessage.Severity severity, String summary, String detail) {

        return new FacesMessage(severity, summary, detail);
    }

    private static void addMessage(FacesMessage message) {
        getContext().addMessage(null, message);
    }

    public static void info(String summary, String detail) {
        addMessage(createMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    public static void error(String summary, String detail) {
        addMessage(createMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
    }

    public static void warn(String summary, String detail) {
        addMessage(createMessage(FacesMessage.SEVERITY_WARN, summary, detail));
    }
}