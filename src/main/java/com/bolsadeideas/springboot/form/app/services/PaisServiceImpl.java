package com.bolsadeideas.springboot.form.app.services;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class PaisServiceImpl implements PaisService{

    private List<Pais> lista;

    public PaisServiceImpl() {
        this.lista = Arrays.asList(
                new Pais(1,"ES","España"),
                new Pais(2,"MX","Mexico"),
                new Pais(3,"CL","Chile"),
                new Pais(4,"AR","Argentina"),
                new Pais(5,"PE","Peru"),
                new Pais(6,"CO","Colombia"),
                new Pais(7,"VE","Venezuela"));
    }

    @Override
    public List<Pais> listar() {
        return lista;
    }

    @Override
    public Pais obtenerPorId(Integer id) {
        Pais resultado = null;
        for (Pais pais: lista) {
            if(Objects.equals(id, pais.getId())){
                resultado = pais;
                break;
            }
        }
        return resultado;
    }
}
