package yunas;


import yunas.configuration.Configuration;
import yunas.http.DefaultContentType;
import yunas.http.HttpMethod;
import yunas.router.Router;
import yunas.template.TemplateEngine;


/**
 * Entry Point of Yunas Framework.
 *
 * Set routing path, error handling and request interceptor.
 */
public class Yunas {

    public static  Configuration getConfiguration() {
        return SingletonContainer.instance.getConfiguration();
    }

    public static void get(String path, Controller controller) {
        SingletonContainer.instance.add(HttpMethod.GET, path, controller, DefaultContentType.HTML);
    }

    public static void post(String path, Controller controller) {
        SingletonContainer.instance.add(HttpMethod.POST, path, controller, DefaultContentType.HTML);
    }

    public static void head(String path, Controller controller) {
        SingletonContainer.instance.add(HttpMethod.HEAD, path, controller, DefaultContentType.HTML);
    }


    /**
     * Provide routing path for RestFul Api.
     *
     * Default content type is application/json.
     *
     */
    public static class Rest {

        public static void get(String path, Controller controller) {
            SingletonContainer.instance.add(HttpMethod.GET, path, controller, DefaultContentType.JSON);
        }

        public static void post(String path, Controller controller) {
            SingletonContainer.instance.add(HttpMethod.POST, path, controller, DefaultContentType.JSON);
        }

        public static void put(String path, Controller controller) {
            SingletonContainer.instance.add(HttpMethod.PUT, path, controller, DefaultContentType.JSON);
        }

        public static void delete(String path, Controller controller) {
            SingletonContainer.instance.add(HttpMethod.DELETE, path, controller, DefaultContentType.JSON);
        }

        public static void head(String path, Controller controller) {
            SingletonContainer.instance.add(HttpMethod.HEAD, path, controller, DefaultContentType.JSON);
        }

        public static void options(String path, Controller controller) {
            SingletonContainer.instance.add(HttpMethod.OPTIONS, path, controller, DefaultContentType.JSON);
        }

        public static void get(String path, Controller controller, DefaultContentType defaultContentType) {
            SingletonContainer.instance.add(HttpMethod.GET, path, controller, defaultContentType);
        }

        public static void post(String path, Controller controller, DefaultContentType defaultContentType) {
            SingletonContainer.instance.add(HttpMethod.POST, path, controller, defaultContentType);
        }

        public static void put(String path, Controller controller, DefaultContentType defaultContentType) {
            SingletonContainer.instance.add(HttpMethod.PUT, path, controller, defaultContentType);
        }

        public static void delete(String path, Controller controller, DefaultContentType defaultContentType) {
            SingletonContainer.instance.add(HttpMethod.DELETE, path, controller, defaultContentType);
        }

        public static void head(String path, Controller controller, DefaultContentType defaultContentType) {
            SingletonContainer.instance.add(HttpMethod.HEAD, path, controller, defaultContentType);
        }

        public static void options(String path, Controller controller, DefaultContentType defaultContentType) {
            SingletonContainer.instance.add(HttpMethod.OPTIONS, path, controller, defaultContentType);
        }

    }

    public static TemplateEngine getTemplateEngine() {
        return SingletonContainer.instance.getTemplateEngine();
    }

    public static void setTemplateEngine(TemplateEngine templateEngine) {
        SingletonContainer.instance.setTemplateEngine(templateEngine);
    }

    public static Interceptor getAfter() {
        return SingletonContainer.instance.getAfter();
    }

    public static Interceptor getBefore() {
        return SingletonContainer.instance.getBefore();
    }

    public static Router getRouter() {
        return SingletonContainer.instance.getRouter();
    }

    public static ErrorController getErrorController() {
        return SingletonContainer.instance.getErrorController();
    }

    public static void setErrorHandler(ErrorController errorHandler) {
        SingletonContainer.instance.setErrorController(errorHandler);
    }


    public static void kill() {
        SingletonContainer.instance.kill();
    }


    // Singleton.
    private static class SingletonContainer {
        private static App instance = App.init();

    }

}
