package com.bolsadeideas.springboot.form.app.controller;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FormController {

    @GetMapping("/form")
    public String form(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("titulo","Formulario usuarios");
        model.addAttribute(usuario);
        return "form";
    }

    @PostMapping("/form")
    public String procesar(@Valid Usuario usuario, BindingResult result, Model model
                           /*@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email*/){

        /*Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setEmail(email);*/

        if(result.hasErrors()){
            /*Map<String,String> errores = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> {
                errores.put(fieldError.getField(),
                        "El campo ".concat(fieldError.getField()).concat(" ").concat(fieldError.getDefaultMessage()));
            });
            model.addAttribute("error",errores);*/
            return "form";

        }

        model.addAttribute("titulo","Resultado form");
        model.addAttribute("usuario",usuario);
        return "resultado";
    }

}
