package intelli.uno.control;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Controller
public class MyErrorController implements ErrorController  {

	 
	@Autowired
    private ErrorAttributes errorAttributes;

	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request,Model model) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
        	model.addAttribute("statusCode", statusCode);

	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	model.addAttribute("ErrorH1", "404 - Not Found");
	        	model.addAttribute("title", "Erprmwise 404-Not Found");
	        	model.addAttribute("ErrorP", "The page you are looking for could not be found.");
	            return "errors/error-404";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        	
	        	String errorMessage="";
	        	
	        	try {
	                WebRequest webRequest = new ServletWebRequest(request);
	                Throwable error = errorAttributes.getError(webRequest);
	                errorMessage = (error != null) ? error.getMessage() : "Internal Server Error";	        		
	        	}catch(Exception ex) {    ex.printStackTrace();  errorMessage="The page you are looking for contains some error.";  }

                model.addAttribute("ErrorP", errorMessage);
	        	model.addAttribute("ErrorH1", "500 - Internal Server Error");
	        	model.addAttribute("title", "Erprmwise 500-Internal Error");

	            return "errors/error-404";
	        }
	    }
	    return "errors/error-404";
	}
}