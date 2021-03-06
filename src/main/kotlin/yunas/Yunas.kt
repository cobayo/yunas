package yunas


import yunas.configuration.Configuration
import yunas.http.DefaultContentType
import yunas.http.HttpMethod
import yunas.router.Router
import yunas.template.TemplateEngine


/**
 * Entry Point of Yunas Framework.
 *
 * Set routing path, error handling and request interceptor.
 */
object Yunas {

    val configuration: Configuration?
        get() = SingletonContainer.instance.configuration

    operator fun get(path: String, controller: Controller) {
        SingletonContainer.instance.add(HttpMethod.GET, path, controller, DefaultContentType.HTML)
    }

    fun post(path: String, controller: Controller) {
        SingletonContainer.instance.add(HttpMethod.POST, path, controller, DefaultContentType.HTML)
    }

    fun head(path: String, controller: Controller) {
        SingletonContainer.instance.add(HttpMethod.HEAD, path, controller, DefaultContentType.HTML)
    }


    /**
     * Provide routing path for RestFul Api.
     *
     * Default content type is application/json.
     *
     */
    object Rest {

        operator fun get(path: String, controller: Controller) {
            SingletonContainer.instance.add(HttpMethod.GET, path, controller, DefaultContentType.JSON)
        }

        fun post(path: String, controller: Controller) {
            SingletonContainer.instance.add(HttpMethod.POST, path, controller, DefaultContentType.JSON)
        }

        fun put(path: String, controller: Controller) {
            SingletonContainer.instance.add(HttpMethod.PUT, path, controller, DefaultContentType.JSON)
        }

        fun delete(path: String, controller: Controller) {
            SingletonContainer.instance.add(HttpMethod.DELETE, path, controller, DefaultContentType.JSON)
        }

        fun head(path: String, controller: Controller) {
            SingletonContainer.instance.add(HttpMethod.HEAD, path, controller, DefaultContentType.JSON)
        }

        fun options(path: String, controller: Controller) {
            SingletonContainer.instance.add(HttpMethod.OPTIONS, path, controller, DefaultContentType.JSON)
        }

        operator fun get(path: String, controller: Controller, defaultContentType: DefaultContentType) {
            SingletonContainer.instance.add(HttpMethod.GET, path, controller, defaultContentType)
        }

        fun post(path: String, controller: Controller, defaultContentType: DefaultContentType) {
            SingletonContainer.instance.add(HttpMethod.POST, path, controller, defaultContentType)
        }

        fun put(path: String, controller: Controller, defaultContentType: DefaultContentType) {
            SingletonContainer.instance.add(HttpMethod.PUT, path, controller, defaultContentType)
        }

        fun delete(path: String, controller: Controller, defaultContentType: DefaultContentType) {
            SingletonContainer.instance.add(HttpMethod.DELETE, path, controller, defaultContentType)
        }

        fun head(path: String, controller: Controller, defaultContentType: DefaultContentType) {
            SingletonContainer.instance.add(HttpMethod.HEAD, path, controller, defaultContentType)
        }

        fun options(path: String, controller: Controller, defaultContentType: DefaultContentType) {
            SingletonContainer.instance.add(HttpMethod.OPTIONS, path, controller, defaultContentType)
        }

    }

    var templateEngine: TemplateEngine
        get() = SingletonContainer.instance.templateEngine
        set(templateEngine) {
            SingletonContainer.instance.templateEngine = templateEngine
        }

    val after: Interceptor?
        get() = SingletonContainer.instance.after

    val before: Interceptor?
        get() = SingletonContainer.instance.before

    val router: Router
        get() = SingletonContainer.instance.router

    val errorController: ErrorController?
        get() = SingletonContainer.instance.errorController

    fun setErrorHandler(errorHandler: ErrorController) {
        SingletonContainer.instance.errorController = errorHandler
    }

    fun kill() {
        SingletonContainer.instance.kill()
    }

    // Singleton.
    private object SingletonContainer {
        val instance = App.init()

    }

}
