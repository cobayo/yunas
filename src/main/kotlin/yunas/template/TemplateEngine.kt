package yunas.template

import yunas.ModelAndView

/**
 * TemplateEngine.
 *
 */
interface TemplateEngine {

    fun render(modelAndView: ModelAndView): String
}
