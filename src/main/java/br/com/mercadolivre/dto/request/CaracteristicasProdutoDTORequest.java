package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.CaracteristicasProduto;
import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CaracteristicasProdutoDTORequest {

    private String nome;
    private String descricao;
    private Long idProduto;

    public CaracteristicasProdutoDTORequest() {

    }

    public CaracteristicasProdutoDTORequest(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public static CaracteristicasProduto converterLst(CaracteristicasProdutoDTORequest c, Produto produto) {
        return new CaracteristicasProduto(c.getNome(), c.getDescricao(), produto);
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public String toString() {
        return "CaracteristicasProdutoDTORequest{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", idProduto=" + idProduto +
                '}';
    }
}
