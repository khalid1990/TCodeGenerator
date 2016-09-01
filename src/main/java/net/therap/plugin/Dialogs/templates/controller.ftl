package ${PACKAGE_NAME};

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

${IMPORTS}

@Controller
@RequestMapping("${REQUEST_PATH}")
public class ${CLASS_NAME}Controller {

    private static final Logger log = LoggerFactory.getLogger(${CLASS_NAME}Controller.class);

    <#--${FIELDS}-->

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    <#--${METHODS}-->
}
