package com.bolsadeideas.springboot.form.app.controller;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Role;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.PaisService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("usuario")
public class FormController {

    @Autowired
    private UsuarioValidador validador;

    @Autowired
    private PaisService paisService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PaisPropertyEditor paisPropertyEditor;

    @Autowired
    private RolesEditor roleEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(validador);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat,true));
        binder.registerCustomEditor(String.class, "nombre",new NombreMayusculaEditor());
        binder.registerCustomEditor(String.class, "apellido",new NombreMayusculaEditor());
        binder.registerCustomEditor(Pais.class,"pais", paisPropertyEditor);
        binder.registerCustomEditor(Role.class,"roles", roleEditor);
    }


    @ModelAttribute("listaPaises")
    public List<Pais> listaPaises(){
        return paisService.listar();
    }

    @ModelAttribute("paises")
    public List<String> paises(){
        return Arrays.asList("España","Mexico","Chile","Argentina", "Peru","Colombia","Venezuela");
    }


    @ModelAttribute("paisesMap")
    public Map<String,String> paisesMap(){
        Map<String,String> paises = new HashMap<>();
        paises.put("ES","España");
        paises.put("MX", "Mexico");
        paises.put("CL", "Chile");
        paises.put("AR","Argentina");
        paises.put("PE", "Peru");
        paises.put("CO", "Colombia");
        paises.put("VE","Venezuela");
        return paises;
    }

    @ModelAttribute("listaRolesString")
    public List<String> listaRolesString(){
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        roles.add("ROLE_MODERATOR");
        return roles;
    }

    @ModelAttribute("listaRolesMap")
    public Map<String, String> listaRolesMap(){
        Map<String, String> roles = new HashMap<>();
        roles.put("ROLE_ADMIN", "Administrador");
        roles.put("ROLE_USER", "Usuario");
        roles.put("ROLE_MODERATOR", "Moderador");
        return roles;
    }

    @ModelAttribute("listaRoles")
    public List<Role> listaRoles(){
        return this.roleService.listar();
    }

    @ModelAttribute("genero")
    public List<String> genero(){
        return Arrays.asList("Hombre","Mujer");
    }

    @GetMapping("/form")
    public String form(Model model){
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setIdentificador("11.001.101-L");
        usuario.setHabilitar(true);
        usuario.setPais(new Pais(6,"CO","Colombia"));
        usuario.setRoles(Arrays.asList(new Role(2, "Usuario", "ROLE_USER")));
        usuario.setValorSecreto("algun valor secreto");
        model.addAttribute("titulo","Formulario usuarios");
        model.addAttribute(usuario);
        return "form";
    }

    @PostMapping("/form")
    public String procesar(@Valid Usuario usuario, BindingResult result, Model model
                           /*@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email*/){
        //validador.validate(usuario,result);

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
            model.addAttribute("titulo","Resultado form");
            return "form";

        }

        return "redirect:/ver";
    }

    @GetMapping("/ver")
    public String ver(@SessionAttribute(name = "usuario",required = false) Usuario usuario, Model model, SessionStatus status){
        if(usuario == null){
            return "redirect:/form";
        }

        model.addAttribute("titulo","Resultado form");

        status.setComplete();
        return "resultado";
    }

}
