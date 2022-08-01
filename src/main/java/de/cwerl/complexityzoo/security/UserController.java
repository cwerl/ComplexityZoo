package de.cwerl.complexityzoo.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private InvitedUserRepository invitedUserRepository;

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(Model model) {
        if(isAlreadyLoggedIn()) {
            return "redirect:/";
        }
        model.addAttribute("title", "Login");
        return "users/login";
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        if (isAlreadyLoggedIn()) {
            return "redirect:/";
        }
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        model.addAttribute("title", "Register");
        return "users/register";
    }

    @PostMapping("/signup")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("users/register", "user", userDto);
            return mav;
        }
        try {
            userService.registerNewUserAccount(userDto);
        } catch (final UserAlreadyExistsException uae) {
            ModelAndView mav = new ModelAndView("users/register", "user", userDto);
            result.rejectValue("username", "error.user", uae.getMessage());
            result.rejectValue("email", "error.user", uae.getMessage());
            return mav;
        } catch (final UserNotInvitedException uni) {
            ModelAndView mav = new ModelAndView("users/register", "user", userDto);
            result.rejectValue("email", "error.user", uni.getMessage());
            return mav;
        }
        return new ModelAndView("redirect:/login");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/invite")
    public String showInviteForm(Model model) {
        InvitedUser user = new InvitedUser();
        model.addAttribute("user", user);
        model.addAttribute("invitations", invitedUserRepository.findAll());
        model.addAttribute("title", "Invite author");
        return "users/invite";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/invite")
    public ModelAndView inviteUser(@ModelAttribute("user") InvitedUser user, BindingResult result, Model model) {
        model.addAttribute("title", "Invite author");
        model.addAttribute("invitations", invitedUserRepository.findAll());
        try {
            userService.inviteUser(user.getEmail());
        } catch (final UserAlreadyExistsException uae) {
            ModelAndView mav = new ModelAndView("users/invite", "user", user);
            result.rejectValue("email", "error.user", uae.getMessage());
            return mav;
        }
        return new ModelAndView("redirect:/invite?success");
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/invite/{email}/cancel", method = RequestMethod.DELETE)
    public String cancelInvite(@PathVariable String email) {
        invitedUserRepository.deleteById(email);
        return "redirect:/invite";
    }

    private boolean isAlreadyLoggedIn() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }
}
