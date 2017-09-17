package org.yunas


import org.yunas.configuration.Configuration
import org.yunas.http.DefaultContentType
import org.yunas.http.HttpMethod
import org.yunas.router.Router
import org.yunas.template.TemplateEngine


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

    fun isJar() :Boolean {
        return SingletonContainer.instance.isJar
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

    var before: Interceptor?
        get() = SingletonContainer.instance.before
        set(interceptor) {
            SingletonContainer.instance.before = interceptor
        }

    var after: Interceptor?
        get() = SingletonContainer.instance.after
        set(interceptor) {
            SingletonContainer.instance.after = interceptor
        }

    val router: Router
        get() = SingletonContainer.instance.router

    var errorHandler: ErrorController?
        get() = SingletonContainer.instance.errorController
        set(errorController) {
            SingletonContainer.instance.errorController = errorController
        }

    /*** Private HttpMethod  */
    // Singleton.
    private object SingletonContainer {

        val instance = App.init()

    }

}// Hide Constructor.
