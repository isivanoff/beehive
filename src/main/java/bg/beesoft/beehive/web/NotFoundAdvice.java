package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class NotFoundAdvice {
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView modelAndView(NotFoundException nfe){
        ModelAndView modelAndView = new ModelAndView("not-found-exception");
        modelAndView.addObject("message",nfe.getMessage());
        return modelAndView;
    }

}
