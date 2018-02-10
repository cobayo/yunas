package yunas

import java.util.Locale

/**
 * When return HTML, Set model for view, viewName("home/index" etc...), Locale.
 *
 * @author yosuke kobayashi
 */
class ModelAndView {

    var model: Map<*, *>? = null

    var viewName: String? = null

    var locale: Locale? = null


    /**
     * Constructor.
     *
     * @param model Map
     * @param viewName String
     */
    constructor(model: Map<*, *>, viewName: String) {
        this.model = model
        this.viewName = viewName
        this.locale = Locale.getDefault()
    }

    constructor(model: Map<*, *>, viewName: String, locale: Locale) {
        this.model = model
        this.viewName = viewName
        this.locale = locale
    }
}
