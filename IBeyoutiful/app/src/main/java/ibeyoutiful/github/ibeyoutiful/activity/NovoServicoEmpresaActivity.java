package ibeyoutiful.github.ibeyoutiful.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ibeyoutiful.github.ibeyoutiful.R;
import ibeyoutiful.github.ibeyoutiful.helper.UsuarioFirebase;
import ibeyoutiful.github.ibeyoutiful.model.Empresa;
import ibeyoutiful.github.ibeyoutiful.model.Produto;

public class NovoServicoEmpresaActivity extends AppCompatActivity {

    private EditText editProdutoNome, editProdutoDescricao,
            editProdutoPreco;
    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_servico_empresa2);

        incializarComponentes();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();
        //Configuração Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Serviço");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void validarDadosProduto(View view){
        //Validar campos preenchidos
        String nome = editProdutoNome.getText().toString();
        String descricao = editProdutoDescricao.getText().toString();
        String preco = editProdutoPreco.getText().toString();

        if( !nome.isEmpty() ){
            if( !descricao.isEmpty() ){
                if( !preco.isEmpty() ){

                    Produto produto = new Produto();
                    produto.setIdUsuario( idUsuarioLogado );
                    produto.setNome( nome );
                    produto.setDescricao( descricao );
                    produto.setPreco( Double.parseDouble(preco) );
                    produto.salvar();

                    finish();
                    exibirMensagem("Serviço Salvo com Sucesso");

                }else{
                    exibirMensagem("Digite um nome para o Serviço");
                }

            }else{
                exibirMensagem("Digite a descrição do serviço");
            }

        }else{
            exibirMensagem("Digite valor para o serviço");
        }

    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void incializarComponentes(){
        editProdutoDescricao = findViewById(R.id.editProdutoDescricao);
        editProdutoNome = findViewById(R.id.editProdutoNome);
        editProdutoPreco = findViewById(R.id.editProdutoPreco);
    }
}
