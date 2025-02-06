package by.daniyal.weather.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSQLException(SQLException ex) {
        ModelAndView modelAndView = new ModelAndView("search-results"); // Укажите имя вашего шаблона
        modelAndView.addObject("errorMessage", "Не удалось добавить карточку. Данная карточка уже существует");
        return modelAndView;
    }
}