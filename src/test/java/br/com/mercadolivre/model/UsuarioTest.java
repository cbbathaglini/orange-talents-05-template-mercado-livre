package br.com.mercadolivre.model;

import br.com.mercadolivre.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void init(){
        Usuario usuario = new Usuario("MonicaGeller@gmail.com", "123456");
        usuarioRepository.save(usuario);
        //System.out.println("Usuario: " + "\n \t login: " + usuario.getLogin() + "\n \t senha: "+ usuario.getSenha());
    }

    @Test
    public void validaUsuario(){
        Optional<Usuario> usuario = usuarioRepository.findById(1L);
        System.out.println(usuario.toString());
        if(usuario.isPresent()){
            //Assertions.assertEquals(32, usuario.get().getSenha().length());
            //Assertions.assertEquals("MonicaGeller@gmail.com", usuario.get().getLogin());
            //Assertions.assertTrue(usuario.get().getInstanteCadastro().compareTo(LocalDateTime.now()) < 0); //data 1 menor que a data atual

        }
    }

}