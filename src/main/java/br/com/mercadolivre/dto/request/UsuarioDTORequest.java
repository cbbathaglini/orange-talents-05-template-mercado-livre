package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.validates.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioDTORequest {

    @NotBlank
    @Email
    @UniqueValue(domainClass = Usuario.class, fieldName = "login", message = "O email informado já existe")
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
