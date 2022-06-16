package utils;

import database.Engine;
import jakarta.servlet.ServletContext;

public class EngineServlet {
    private static final String ENGINE_ATTRIBUTE_NAME = "Engine";
    private static final Object EngineLock = new Object();

    public static Engine getEngine(ServletContext servletContext) {
        synchronized (EngineLock) {
            if (servletContext.getAttribute(ENGINE_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(ENGINE_ATTRIBUTE_NAME, new Engine());
            }
        }
        return (Engine) servletContext.getAttribute(ENGINE_ATTRIBUTE_NAME);
    }
}
