package com.ozymern.spring.user.manager.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ozymern.spring.user.manager.commands.UserForm;
import com.ozymern.spring.user.manager.services.UserService;



@Controller
@SessionAttributes("userForm")
public class UserController {

    @Autowired
    private UserService userService;

    

    // Mensaje para internalizacion
    @Autowired
    private MessageSource messageSource;


    protected final Log logger = LogFactory.getLog(this.getClass());

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/")

    public String list(Model model, Authentication authentication, HttpServletRequest request, Locale locale) {
        if (authentication != null) {

            logger.info("Hola usuario " + authentication.getName());
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {

            logger.info("Hola usuario " + auth.getName()
                    + " utilizando de forma estatica Authentication auth = SecurityContextHolder.getContext().getAuthentication(); ");
        }

        // otra forma para validar el role, mas facil y optima
        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request,
                "ROLE_");
        if (securityContext.isUserInRole("ADMIN")) {
            logger.info(
                    "Hola usuario " + auth.getName() + " utilizando de forma SecurityContextHolderAwareRequestWrapper");

        } else {
            logger.info("Hola usuario " + auth.getName()
                    + " usted no tiene acceso utilizando de forma SecurityContextHolderAwareRequestWrapper");

        }

        model.addAttribute("titulo", messageSource.getMessage("text.user.list", null, locale));
        model.addAttribute("userForm", userService.finAll());
        return "pages/home";

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/newUser")
    public String newUser(Model model, Locale locale) {
        // enviamos un cliente y en ese clientes vamos a llenar los datos, lo
        // recibiremos con th:object
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("titulo", "Formulario");
        model.addAttribute("submit", messageSource.getMessage("text.user.create", null, locale));
        return "pages/userform";
    }

    // formulario submit
    // @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")

    public String saveOrUpdate(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult,
                               RedirectAttributes flash, Model model, Locale locale) {

        String mensajeFlash = (userForm.getId() == null)
                ? messageSource.getMessage("text.user.createExit", null, locale)
                : messageSource.getMessage("text.user.editExit", null, locale);
        // Valid habilita la validacion en el objeto mapeado al userform
        // BindingResult: para manejar los errores
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Formulario");
            model.addAttribute("submit", "prueba");
            model.addAttribute("submit", mensajeFlash);
            return "pages/userform";
        }
        UserForm saveUser;

        try {
            saveUser = userService.saveOrUpdate(userForm);
            flash.addFlashAttribute("success", mensajeFlash);
        } catch (Exception e) {
            flash.addFlashAttribute("error", messageSource.getMessage("text.user.email.repeat", null, locale));
            model.addAttribute("submit", messageSource.getMessage("text.user.create", null, locale));
            return "redirect:/userform";
        }

        return "redirect:/show/" + saveUser.getId();
    }

    @RequestMapping("/edit/{hashId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit(@PathVariable String hashId, Model model, Locale locale) {

        if (userService.findByHashId(hashId).getId() != null) {
            model.addAttribute("userForm", userService.findByHashId(hashId));
            model.addAttribute("submit", messageSource.getMessage("text.user.edit", null, locale));
            return "pages/userform";
        } else {

            return "redirect:/";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @RequestMapping("/show/{hashId}")
    public String getUser(@PathVariable String hashId, Model model, Locale locale) {

        if (userService.findByHashId(hashId).getId() != null) {
            model.addAttribute("userForm", userService.findByHashId(hashId));
            model.addAttribute("titulo", messageSource.getMessage("text.user.show", null, locale));
            return "pages/show";
        } else {

            return "redirect:/";
        }

    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@ModelAttribute UserForm userForm) {

        userService.delete(userForm.getHashId());
        return "redirect:/";

    }
}
