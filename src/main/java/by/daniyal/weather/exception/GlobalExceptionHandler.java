package by.daniyal.weather.exception;

import by.daniyal.weather.services.weather.Weather;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
//
//    @ExceptionHandler(SQLException.class)
//    public ModelAndView handleSQLException(SQLException ex) {
//        ModelAndView modelAndView = new ModelAndView("search-results"); // Укажите имя вашего шаблона
//        modelAndView.addObject("errorMessage", "Не удалось добавить карточку. Данная карточка уже существует");
//        return modelAndView;
//    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSQLException(SQLException ex, HttpServletRequest request) {
        // Получите список погодных условий из запроса (предполагая, что он хранится в области видимости запроса)
        List<Weather> weathers = (List<Weather>) request.getAttribute("weathers");

        ModelAndView modelAndView = new ModelAndView("search-results");
        modelAndView.addObject("errorMessage", "Не удалось добавить карточку. Данная карточка уже существует");
        modelAndView.addObject("weathers", weathers);

        return modelAndView;
    }
}