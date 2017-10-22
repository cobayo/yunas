package yunas.template;

import org.thymeleaf.context.Context;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import yunas.ModelAndView;

import java.util.Map;

/**
 * ThymeleafEngine.
 *
 * Default TemplateEngine in Yunas.
 */
public class ThymeleafEngine implements TemplateEngine {


    private static final String PREFIX = "templates/";
    private static final String  SUFFIX = ".html";
    private static final long DEFAULT_CACHE_TTL_MILL = 3000000L;

    // TemplateEngine objects are designed to be used in highly-concurrent environments
    private org.thymeleaf.TemplateEngine templateEngine = null;


    public ThymeleafEngine() {
        create(PREFIX, SUFFIX, DEFAULT_CACHE_TTL_MILL);
    }

    public ThymeleafEngine (String prefix, String suffix) {
        create(prefix, suffix, DEFAULT_CACHE_TTL_MILL);
    }


    public ThymeleafEngine (String prefix, String suffix, Long ttl) {
        create(prefix, suffix, ttl);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String render(ModelAndView modelAndView) {

        Context context = new Context(modelAndView.getLocale());
        context.setVariables((Map<String,Object>)modelAndView.getModel() );

        return templateEngine.process(modelAndView.getViewName(), context);
    }


    private void create(String prefix, String suffix, Long ttl) {

        ITemplateResolver resolver = createResolver(prefix, suffix, ttl);
        templateEngine = new org.thymeleaf.TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        templateEngine.addDialect(new Java8TimeDialect());

    }

    private ITemplateResolver createResolver(String prefix, String suffix, Long ttl) {
        final ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);

        resolver.setPrefix(prefix);
        resolver.setSuffix(suffix);
        resolver.setCacheTTLMs(ttl);
        return resolver;
    }


}
