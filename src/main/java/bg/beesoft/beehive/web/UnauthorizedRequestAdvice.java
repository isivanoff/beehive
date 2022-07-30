package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.exception.UnauthorizedRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class UnauthorizedRequestAdvice {

    @ExceptionHandler({UnauthorizedRequestException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ModelAndView onUnauthorizedRequest(UnauthorizedRequestException ure) {
        ModelAndView modelAndView = new ModelAndView("unauthorized-request-exception");
        modelAndView.addObject("message", ure.getMessage());

        return modelAndView;
    }

}
