package ibeyoutiful.github.ibeyoutiful.activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import ibeyoutiful.github.ibeyoutiful.R;
import ibeyoutiful.github.ibeyoutiful.helper.ConfiguracaoFirebase;
import ibeyoutiful.github.ibeyoutiful.helper.UsuarioFirebase;
import ibeyoutiful.github.ibeyoutiful.model.Empresa;
import ibeyoutiful.github.ibeyoutiful.model.Usuario;

public class ConfiguracoesUsuarioActivity extends AppCompatActivity {

    private EditText editUsuarioNome, editUsuarioEndereco;
    private String idUsuario;
    private DatabaseReference firebaseRef;
    private ImageView imagePerfilUsuario;
    private static final int SELECAO_GALERIA = 200;
    private String urlImagemSelecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_usuario);

        //Configurações Iniciais
        inicializarComponentes();
        idUsuario = UsuarioFirebase.getIdUsuario();
        firebaseRef = ConfiguracaoFirebase.getFirebase();

        //Configuração Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações Usuário");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagePerfilUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                if( i.resolveActivity(getPackageManager()) != null ){
                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });
    }

    public void validarDadosUsuario(View view){
        String nome = editUsuarioNome.getText().toString();
        String endereco = editUsuarioEndereco.getText().toString();

        if( !nome.isEmpty() ){
            if(!endereco.isEmpty()){
                Usuario usuario = new Usuario() ;
                usuario.setIdUsuario( idUsuario );
                usuario.setNomeUsuario( nome );
                usuario.setEnderecoUsuario( endereco );
                usuario.setUrlImagem( urlImagemSelecionada );
                usuario.salvar();
                finish();

            }else{
                exibirMensagem("Digite um endereço válido");
            }

        }else{
            exibirMensagem("Digite um nome válido ");
        }

    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes(){
        editUsuarioNome = findViewById(R.id.editNomeUsuario);
        editUsuarioEndereco = findViewById(R.id.editUsuarioEndereco);
        imagePerfilUsuario = findViewById(R.id.imagePerfilUsuario);
    }
}
