package br.com.mercadolivre.model;

import javax.persistence.*;

@Entity
public class Categoria{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nome;

    @ManyToOne //A categoria pode ter muitas categorias mães: Tecnologia -> Celulares -> Smartphones -> Android,Ios
    private Categoria categoriaMae;

    public Categoria() {
    }

    public Categoria(String nome) { //pode ter categorias mães então só vem o nome
        this.nome = nome;
    }

    public void setCategoriaMae(Categoria categoriaMae) { //caso tenha a categoria mãe então é só setar
        this.categoriaMae = categoriaMae;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoriaMae=" + categoriaMae +
                '}';
    }
}
