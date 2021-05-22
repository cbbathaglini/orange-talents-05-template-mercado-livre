package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.Usuario;

import javax.persistence.EntityManager;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class UsuarioDTORequest {

    @NotBlank
    @Email
    private String login;

    @NotBlank @Size(min = 6)
    private String senha;


    public UsuarioDTORequest() {
    }

    public UsuarioDTORequest(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario converter(){
       return new Usuario(this.login, this.senha);
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
