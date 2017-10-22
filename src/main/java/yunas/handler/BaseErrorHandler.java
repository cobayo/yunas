package yunas.handler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ErrorHandler;
import yunas.ErrorController;
import yunas.ModelAndView;
import yunas.Yunas;
import yunas.http.DefaultContentType;
import yunas.template.TemplateEngine;
import yunas.util.BaseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handling Errors.
 * if Not Set ErrorController in Yunas, Call createDefaultResponse method.
 *
 * @author yosuke kobayashi
 */
public class BaseErrorHandler extends ErrorHandler {


    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        baseRequest.setHandled(true);
        ErrorController handler = Yunas.getErrorController();
        if (handler != null) {
            createCustomResponse(handler, request, response);
        } else {
            createDefaultResponse(baseRequest, request, response);
        }
    }



    private void createCustomResponse(ErrorController controller, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Object obj = controller.action(request, response.getStatus());

        if (obj instanceof ModelAndView) {
            response.setContentType(DefaultContentType.HTML.getValue());
            TemplateEngine templateEngine = Yunas.getTemplateEngine();
            response.getWriter().print(templateEngine.render((ModelAndView)obj));
            return;
        }

        response.getWriter().print(obj.toString());

    }

    private void createDefaultResponse(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (baseRequest.getContentType() == null || BaseUtil.equals(baseRequest.getContentType(),DefaultContentType.HTML.getValue())) {
            response.setContentType(DefaultContentType.HTML.getValue());
            createErrorHtml(request, response);
        } else {
            response.getWriter().print(response.getStatus());
        }
    }

    private void createErrorHtml(HttpServletRequest request, HttpServletResponse response ) throws IOException {

        response.getWriter().append("<html><head><title>Error</title></head><body>")
                .append("Error(Status Code: ").append(String.valueOf(response.getStatus()))
                .append(")").append("</body><html>").close();
    }
}
