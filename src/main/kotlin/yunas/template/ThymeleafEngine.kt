package yunas.template

import yunas.ModelAndView
import org.thymeleaf.context.Context
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.ITemplateResolver

/**
 * ThymeleafEngine.
 *
 * Default TemplateEngine in Yunas.
 */
class ThymeleafEngine : TemplateEngine {

    // TemplateEngine objects are designed to be used in highly-concurrent environments
    private var templateEngine: org.thymeleaf.TemplateEngine? = null


    constructor() {
        create(PREFIX, SUFFIX, DEFAULT_CACHE_TTL_MILL)
    }

    constructor(prefix: String, suffix: String) {
        create(prefix, suffix, DEFAULT_CACHE_TTL_MILL)
    }


    constructor(prefix: String, suffix: String, ttl: Long) {
        create(prefix, suffix, ttl)
    }

    @SuppressWarnings("unchecked")
    override fun render(modelAndView: ModelAndView): String {

        val context = Context(modelAndView.locale!!)
        context.setVariables(modelAndView.model as Map<String, Any>?)

        return templateEngine!!.process(modelAndView.viewName!!, context)
    }


    private fun create(prefix: String, suffix: String, ttl: Long) {

        val resolver = createResolver(prefix, suffix, ttl)
        templateEngine = org.thymeleaf.TemplateEngine()
        templateEngine!!.setTemplateResolver(resolver)
        templateEngine!!.addDialect(Java8TimeDialect())

    }

    private fun createResolver(prefix: String, suffix: String, ttl: Long): ITemplateResolver {
        val resolver = ClassLoaderTemplateResolver()
        resolver.templateMode = TemplateMode.HTML

        resolver.prefix = prefix
        resolver.suffix = suffix
        resolver.cacheTTLMs = ttl
        return resolver
    }

    companion object {

        private val PREFIX = "templates/"
        private val SUFFIX = ".html"
        private val DEFAULT_CACHE_TTL_MILL = 3000000L
    }

}
