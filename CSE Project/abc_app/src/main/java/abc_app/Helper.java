package abc_app;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Helper {

    public static <T> T getBean(String beanName, Class<T> type) {

        // Get the FacesContext object.
        FacesContext context = FacesContext.getCurrentInstance();

        // Get the Application object for the current context.
        Application application = context.getApplication();

        // Evaluate an EL expression, to get the bean with the specified name.
        return application.evaluateExpressionGet(
        		context, 
        		"#{" + beanName + "}", 
        		type);
    }


    public static void expungeSession() {

        // Get the FacesContext object.
        FacesContext context = FacesContext.getCurrentInstance();

        // Get the HttpSession object for the current context.
        HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
        
        // Invalidate (i.e. expunge) session state.
        if (session != null) {
            session.invalidate();
        }
    }
}
