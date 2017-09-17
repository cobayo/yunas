package org.yunas

import java.util.Locale

/**
 * When return HTML, Set model for view, viewName("home/index" etc...), Locale.
 *
 * @author yosuke kobayashi
 */
class ModelAndView {

    var model: Map<*, *>? = null
        private set

    var viewName: String? = null
        private set

    var locale: Locale? = null
        private set

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
