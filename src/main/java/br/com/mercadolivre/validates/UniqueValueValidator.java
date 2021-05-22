package br.com.mercadolivre.validates;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String domainAttribute;
    private Class<?> klass;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void initialize(UniqueValue parametros) {
        domainAttribute = parametros.fieldName();
        klass = parametros.domainClass();
    }

    @Override
    @Transactional
    public boolean isValid(Object valor, ConstraintValidatorContext contexto) {
        Query q = em.createQuery("SELECT 1 FROM " + klass.getName() + " WHERE " + domainAttribute + " = :valueparam" );
        q.setParameter("valueparam", valor);
        List<?> listaResultados = q.getResultList();
        //System.out.println("listaresultados: " + listaResultados.size());
        Assert.state(listaResultados.size() <= 1, "Foi encontrado(a) mais de um(a) " + klass.getName() + " com o atributo "+ domainAttribute);

        return listaResultados.isEmpty();
    }
}