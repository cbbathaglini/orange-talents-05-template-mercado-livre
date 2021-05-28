package br.com.mercadolivre.validates;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Object> {

    private String domainAttribute;
    private Class<?> klass;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void initialize(ExistsId parametros) {
        domainAttribute = parametros.fieldName();
        klass = parametros.domainClass();
    }

    @Override
    public boolean isValid(Object valor, ConstraintValidatorContext contexto) {

        if(valor == null){ return true;}

        Query q = em.createQuery("select 1 from "+klass.getName()+" where "+domainAttribute+"=:value");
        //System.out.println("QEURY:"+ q);
        q.setParameter("value", valor);

        List<?> list = q.getResultList();
        Assert.isTrue(list.size() <=1, "aconteceu algo bizarro e você tem mais de um "+klass+" com o atributo "+domainAttribute+" com o valor = "+valor);

        return !list.isEmpty();
    }
}