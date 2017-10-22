package yunas.router;

import yunas.Controller;
import yunas.http.DefaultContentType;
import yunas.http.HttpMethod;

/**
 * Route.
 *
 */
public class Route {


    private HttpMethod method;
    private String path;
    private Controller controller;
    private DefaultContentType defaultContentType;

    public Route(HttpMethod method, String path, Controller controller, DefaultContentType defaultContentType) {
        this.method = method;
        this.path = path;
        this.controller = controller;
        this.defaultContentType = defaultContentType;
    }

    public Controller getController() throws IllegalAccessException {

        try {
            return controller.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // When it is lambda in Kotlin.
            return controller;
        }

    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public DefaultContentType getDefaultContentType() {
        return defaultContentType;
    }
}
