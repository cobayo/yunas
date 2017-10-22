package yunas;

import java.util.Locale;
import java.util.Map;

/**
 * When return HTML, Set model for view, viewName("home/index" etc...), Locale.
 *
 * @author yosuke kobayashi
 */
public class ModelAndView {

    private Map<?,?> model;

    private String viewName;

    private Locale locale;


    /**
     * Constructor.
     *
     * @param model Map
     * @param viewName String
     */
    ModelAndView(Map<?, ?> model, String viewName) {
        this.model = model;
        this.viewName = viewName;
        this.locale = Locale.getDefault();
    }

    ModelAndView(Map<?, ?> model, String viewName, Locale locale) {
        this.model = model;
        this.viewName = viewName;
        this.locale = locale;
    }

    public Map<?, ?> getModel() {
        return model;
    }

    public String getViewName() {
        return viewName;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setModel(Map<?, ?> model) {
        this.model = model;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
