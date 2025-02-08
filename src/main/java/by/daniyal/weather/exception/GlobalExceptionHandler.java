package by.daniyal.weather.exception;

import by.daniyal.weather.models.weather.Weather;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSQLException(SQLException ex, HttpServletRequest request) {
        List<Weather> weathers = (List<Weather>) request.getAttribute("weathers");

        ModelAndView modelAndView = new ModelAndView("search-results");
        modelAndView.addObject("errorMessage", "Не удалось добавить карточку. Данная карточка уже существует");
        modelAndView.addObject("weathers", weathers);

        return modelAndView;
    }
}