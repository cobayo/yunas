package yunas.router

import yunas.Controller
import yunas.http.DefaultContentType
import yunas.http.HttpMethod

/**
 * Route.
 *
 */
class Route(val method: HttpMethod, val path: String, private val controller: Controller, val defaultContentType: DefaultContentType) {


    @Throws(IllegalAccessException::class)
    fun getController(): Controller {

        return try {
            controller.javaClass.newInstance()
        } catch (e: InstantiationException) {
            // When it is lambda in Kotlin.
            controller
        } catch (e: IllegalAccessException) {
            controller
        }

    }

}
