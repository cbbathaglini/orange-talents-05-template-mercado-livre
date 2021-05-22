package br.com.mercadolivre.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;


@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String login;

    @NotBlank @Length(min = 6)
    private String senha;

    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy" )
    private LocalDateTime instanteCadastro = LocalDateTime.now();

    public Usuario() {
    }

    public Usuario(String login, String senha){
        this.login = login;
        this.senha = new MD5().criptografar(senha);
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", instanteCadastro=" + instanteCadastro +
                '}';
    }

}
