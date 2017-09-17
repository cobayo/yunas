package org.yunas.template

import org.yunas.ModelAndView

/**
 * TemplateEngine.
 *
 */
interface TemplateEngine {

    fun render(modelAndView: ModelAndView): String
}
