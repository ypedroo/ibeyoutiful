package ibeyoutiful.github.ibeyoutiful.model;

public class Usuario {

    private String idUsuario;
    private String urlImagem;
    private String nomeUsuario;
    private String enderecoUsuario;

    public Usuario(String idUsuario, String urlImagem, String nomeUsuario, String enderecoUsuario) {
        this.idUsuario = idUsuario;
        this.urlImagem = urlImagem;
        this.nomeUsuario = nomeUsuario;
        this.enderecoUsuario = enderecoUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEnderecoUsuario() {
        return enderecoUsuario;
    }

    public void setEnderecoUsuario(String enderecoUsuario) {
        this.enderecoUsuario = enderecoUsuario;
    }
}
