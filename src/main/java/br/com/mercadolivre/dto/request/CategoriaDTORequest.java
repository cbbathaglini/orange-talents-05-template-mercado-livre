package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.Categoria;
import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.repository.CategoriaRepository;
import br.com.mercadolivre.validates.UniqueValue;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class CategoriaDTORequest {

    @NotBlank
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome", message = "O nome da categoria que foi informado já existe")
    private String nome;

    @ManyToOne //A categoria pode ter muitas categorias mães: Tecnologia -> Celulares -> Smartphones -> Android,Ios
    private Long idCategoriaMae;

    public CategoriaDTORequest() {
    }

    public CategoriaDTORequest(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdCategoriaMae() {
        return idCategoriaMae;
    }

    public void setIdCategoriaMae(Long idCategoria) {
        this.idCategoriaMae = idCategoria;
    }

    public Categoria converter(CategoriaRepository categoriaRepository){

        Categoria categoria = new Categoria(this.nome);
        if(this.idCategoriaMae != null){
            Optional<Categoria> categoriaMaeOp = categoriaRepository.findById(this.idCategoriaMae);
            if(categoriaMaeOp.isPresent()) {
                categoria.setCategoriaMae(categoriaMaeOp.get());
            }
        }
        return categoria;
    }
}
