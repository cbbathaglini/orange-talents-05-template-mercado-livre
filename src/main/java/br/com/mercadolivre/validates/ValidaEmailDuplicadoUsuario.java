package br.com.mercadolivre.validates;

import br.com.mercadolivre.dto.request.UsuarioDTORequest;
import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
public class ValidaEmailDuplicadoUsuario implements Validator {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public boolean supports(Class<?> clazz) {
        return UsuarioDTORequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()){ return; }
        UsuarioDTORequest usuarioDto = (UsuarioDTORequest) target;
        Optional<Usuario> usuarioOp = usuarioRepository.findByLogin(usuarioDto.getLogin());
        if(usuarioOp.isPresent()){
            errors.rejectValue("login", null, "Já existe este email cadastrado na base de dados");
            System.out.println("Já existe este email na base de dados");
        }
    }
}
