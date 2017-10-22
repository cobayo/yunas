package yunas.router;

import yunas.Controller;
import yunas.exception.YunasExceptionProvider;
import yunas.http.DefaultContentType;
import yunas.http.HttpMethod;
import yunas.util.BaseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Router.
 *
 * @author Yosuke Kobayashi
 */
public class Router {

    private List<Route> routes = new ArrayList<>();

    public void add(HttpMethod method, String path,  Controller controller, DefaultContentType defaultContentType) {
        this.routes.add(new Route(method, path, controller, defaultContentType));
    }

    public Route find(HttpMethod method, String path) throws IllegalAccessException {

        Optional<Route> route = this.routes.stream().filter(r -> r.getMethod() == method && BaseUtil.equals(r.getPath(), path) ).findFirst();

        if (!route.isPresent()) {
            throw new YunasExceptionProvider().notFoundPath(path);
        }

        return route.get();
    }

}
