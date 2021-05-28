package br.com.mercadolivre.model;

import br.com.mercadolivre.dto.request.GatewayDTORequest;
import br.com.mercadolivre.repository.CompraRepository;
import br.com.mercadolivre.repository.ProdutoRepository;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @ManyToOne
    private Produto produto; // um produto pode ter várias compras

    private BigDecimal valorProduto;

    @ManyToOne
    private Usuario comprador;//um comprador pode ter várias compras

    @Enumerated(EnumType.STRING)
    private Status status;

    private GatewayPagamento gatewayPagamento;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Pagamento> pagamentosList = new HashSet<>();

    public Compra() {
    }

    public Compra(Integer quantidade, Produto produto, Usuario comprador, BigDecimal valorProduto, GatewayPagamento gatewayPagamento) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.comprador = comprador;
        this.valorProduto = valorProduto;
        this.status = Status.INICIADA;
        this.gatewayPagamento = gatewayPagamento;
    }

    public static String URLRetorno(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
        return compra.getGatewayPagamento().retornoURL(compra, uriComponentsBuilder);
    }

    public Long getId() {
        return id;
    }

    public GatewayPagamento getGatewayPagamento() {
        return gatewayPagamento;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public static Compra existeCompra(Long idCompra, CompraRepository compraRepository) {
        Optional<Compra> compraOptional = compraRepository.findById(idCompra);
        if(compraOptional.isPresent()){
            return compraOptional.get();
        }
        return null;
    }

    public Pagamento adicionarPagamento(CompraRepository compraRepository, Pagamento pagamento){

        this.status = Status.PAGA;
        this.pagamentosList.add(pagamento);
        compraRepository.save(this);

        return pagamento;
    }


    public boolean pagamentoInvalido(CompraRepository compraRepository){
        //Uma compra não pode ter mais de duas transações concluídas com sucesso associada a ela.
        int sucesso = 0;
        for (Pagamento p:this.pagamentosList) {
            if(p.getStatusPagamento() == StatusPagamento.Sucesso) {sucesso++;}
            if (sucesso >= 2) return true;
        }
        return false;

    }

    public StatusPagamento getStatusPagamento(String statusPassado) {
        return this.getGatewayPagamento().retornaStatus(statusPassado);
    }

    /*public void setStatus(Status status) {
        this.status = status;
    }*/

    /*public void setPagamentosList(Set<Pagamento> pagamentosList) {
        this.pagamentosList = pagamentosList;
    }*/

    public Set<Pagamento> getPagamentosList() {
        return pagamentosList;
    }
}
