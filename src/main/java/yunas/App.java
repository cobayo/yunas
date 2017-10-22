package yunas;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yunas.configuration.Configuration;
import yunas.configuration.ConfigurationFactory;
import yunas.configuration.DefaultConfigurationFactory;
import yunas.db.Databases;
import yunas.db.DefaultDataSourceStrategy;
import yunas.exception.YunasExceptionProvider;
import yunas.http.DefaultContentType;
import yunas.http.HttpMethod;
import yunas.router.Router;
import yunas.server.EmbeddedServerFactory;
import yunas.server.JettyFactory;
import yunas.template.TemplateEngine;
import yunas.template.ThymeleafEngine;

/**
 * App Instance.
 *
 * Singleton Instance.
 *
 * @author  Yosuke Kobayashi
 */
class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private Configuration configuration;

    private Server server;

    private Router router = new Router();

    private Interceptor before;

    private Interceptor after;

    private ErrorController errorController;

    // Default Thymeleaf
    private TemplateEngine templateEngine = new ThymeleafEngine();

    private boolean isInit = false;

    Configuration getConfiguration() {
        return configuration;
    }

    Router getRouter() {
        return router;
    }

    Interceptor getBefore() {
        return before;
    }

    Interceptor getAfter() {
        return after;
    }

    ErrorController getErrorController() {
        return errorController;
    }

    TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    void setRouter(Router router) {
        this.router = router;
    }

    void setBefore(Interceptor before) {
        this.before = before;
    }

   void setAfter(Interceptor after) {
        this.after = after;
    }

   void setErrorController(ErrorController errorController) {
        this.errorController = errorController;
    }

    void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    // Hide Constructor
    private App(boolean isWeb) {

        perform(new DefaultConfigurationFactory(), new JettyFactory(),isWeb);
    }

    synchronized static App init() {

        try {
            return new App(true);
        } catch (Throwable th) {
            // Fail to init
            LOG.error(th.getMessage());
            th.printStackTrace();
            throw new YunasExceptionProvider().failToInit(th.getCause());
        }

    }


    synchronized void  kill() {

        try {
            this.server.stop();
        } catch (Throwable th) {
            // Fail to Stop
            LOG.error(th.getMessage());
            th.printStackTrace();
        }

    }


    private void perform(ConfigurationFactory confFactory,  EmbeddedServerFactory serverFactory, boolean isWeb) {

        if (isInit) {
            return;
        }

        // Build Configuration, Server
        Configuration conf  = confFactory.create();
        this.configuration = conf;
        this.server = serverFactory.create(conf);

        // Create Databases ConnectionPool
        Databases.init(conf, new DefaultDataSourceStrategy());

        if (!isWeb) {
            isInit = true;
            return;
        }

        new Thread(() -> {

            try {

                App.this.server.start();
                App.this.server.join();

            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("Halt server: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }).start();

        isInit = true;

    }

    public void add(HttpMethod method, String path, Controller controller, DefaultContentType defaultContentType) {
        router.add(method, path, controller, defaultContentType);
    }

    synchronized static App initBatch() {

            try {
                return new App(false);
            } catch (Throwable th) {
                // Fail to init
                LOG.error(th.getMessage());
                th.printStackTrace();
                throw new YunasExceptionProvider().failToInit(th.getCause());
            }

    }
}


