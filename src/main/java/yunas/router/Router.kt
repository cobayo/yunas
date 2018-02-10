package yunas.router

import yunas.Controller
import yunas.exception.YunasExceptionProvider
import yunas.http.DefaultContentType
import yunas.http.HttpMethod
import yunas.util.BaseUtil

import java.util.ArrayList
import java.util.Optional

/**
 * Router.
 *
 * @author Yosuke Kobayashi
 */
class Router {

    private val routes = ArrayList<Route>()

    fun add(method: HttpMethod, path: String, controller: Controller, defaultContentType: DefaultContentType) {
        this.routes.add(Route(method, path, controller, defaultContentType))
    }

    @Throws(IllegalAccessException::class)
    fun find(method: HttpMethod, path: String): Route {

        val route = this.routes.stream().filter { r -> r.method == method && BaseUtil.equals(r.path, path) }.findFirst()

        if (!route.isPresent) {
            throw YunasExceptionProvider().notFoundPath(path)
        }

        return route.get()
    }

}
