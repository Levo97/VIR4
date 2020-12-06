/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.levi.VIR4.controllers;

import com.levi.VIR4.domain.Role;
import com.levi.VIR4.domain.User;
import com.levi.VIR4.repositories.UserRepository;
import com.levi.VIR4.repositories.RoleRepository;
import com.levi.VIR4.services.CustomUserDetailsService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;




@Controller  // a Controller azonosítása
public class AuthController {

    @Autowired // Automatikus komponens szkennelés
    private CustomUserDetailsService userService;

    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Már létezik ilyen felhasználó");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }

    @PostMapping(value = "/rights")
    @ResponseBody
    public String rightFinder(String email){

        User user = userService.findUserByEmail(email);
        Set<Role> dsa = user.getRoles();

        List<Role> targetList = new ArrayList<>(dsa);

        JSONArray roles = new JSONArray();
        for (Role a : targetList) {
            JSONObject tmp = new JSONObject();
            tmp.put("id", a.getId());
            tmp.put("name", a.getRole());
            roles.put(tmp);
        }

        return roles.toString();

    }
    @PostMapping(value = "/modifyRights")
    @ResponseBody
    public void  modifyRights(String email, Boolean JPG, Boolean PNG, Boolean GIF){

        userService.deleteRights(email);
        User user = userService.findUserByEmail(email);




        if (JPG == true){
            userService.setRight(user,"JPG");
        }if (PNG == true){
            userService.setRight(user,"PNG");
        }if (GIF == true){
            userService.setRight(user,"GIF");
        }


    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<User> users = userService.getUsers();
        users.remove(0);
        modelAndView.addObject("users", users);
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Üdv," + user.getFullname()+"!");
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @RequestMapping(value = "/gallery", method = RequestMethod.GET)
    public ModelAndView gallery() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Üdv," + user.getFullname()+"!");
        modelAndView.setViewName("gallery");

        ArrayList<String> png = new ArrayList<String>();
        ArrayList<String> jpg = new ArrayList<String>();
        ArrayList<String> gif = new ArrayList<String>();


        File directory = new File(System.getProperty("user.dir") +"\\src\\main\\resources\\static\\images" );
        File[] f = directory.listFiles();
        for (File file : f) {
            if (file != null ) {
                if(file.getName().toLowerCase().endsWith(".png")){
                    png.add(file.getName());
                }if(file.getName().toLowerCase().endsWith(".jpg")){
                    jpg.add(file.getName());
                }if(file.getName().toLowerCase().endsWith(".gif")){
                    gif.add(file.getName());
                }
            }

        }

        Set<Role> dsa = user.getRoles();

        List<Role> targetList = new ArrayList<>(dsa);

        for (Role a : targetList) {
            String role = a.getRole();
            if (role.equals("JPG")){
                modelAndView.addObject("JPG", jpg);

            }if (role.equals("PNG")){
                modelAndView.addObject("PNG", png);

            }if (role.equals("GIF")){
                modelAndView.addObject("GIF", gif);

            }
        }


        return modelAndView;


    }



    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
       if (user!=null) {
           modelAndView.addObject("currentUser", user);
           modelAndView.addObject("fullName", "Üdv," + user.getFullname()+"!" );


               for (GrantedAuthority a : auth.getAuthorities()) {
                   if ("ADMIN".equals(a.getAuthority())) {
                       user = userService.findUserByEmail(auth.getName());
                       List<User> users = userService.getUsers();
                       users.remove(0);
                       modelAndView.addObject("users", users);
                       modelAndView.setViewName("dashboard");
                       return modelAndView;

                   }}

           modelAndView.setViewName("gallery");

           return modelAndView;


       }
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
