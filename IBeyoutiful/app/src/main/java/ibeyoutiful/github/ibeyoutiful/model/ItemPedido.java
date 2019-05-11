package ibeyoutiful.github.ibeyoutiful.model;

public class ItemPedido {

    private String idProduto;
    private String nomePorduto;
    private int quantidade;
    private Double preco;

    public ItemPedido() {

    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomePorduto() {
        return nomePorduto;
    }

    public void setNomePorduto(String nomePorduto) {
        this.nomePorduto = nomePorduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
