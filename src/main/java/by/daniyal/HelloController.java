package by.daniyal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author daha
 */

@Controller
@RequestMapping("/hello ")
public class HelloController {

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("name", "World");
        return "index";
    }
}
