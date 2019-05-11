package ibeyoutiful.github.ibeyoutiful.model;

import android.content.Intent;

import com.google.firebase.database.DatabaseReference;

import ibeyoutiful.github.ibeyoutiful.helper.ConfiguracaoFirebase;

public class Usuario {

    private String idUsuario;
    private String urlImagem;
    private String nomeUsuario;
    private String enderecoUsuario;
    private Integer telefoneUsuario;

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference usuarioRef = firebaseRef.child("usuarios")
                .child( getIdUsuario() );
        usuarioRef.setValue(this);
    }

    public Usuario() {

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

    public Integer getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public void setTelefoneUsuario(Integer telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }
}
