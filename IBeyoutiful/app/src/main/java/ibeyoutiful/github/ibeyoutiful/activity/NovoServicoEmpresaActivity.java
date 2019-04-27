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
import ibeyoutiful.github.ibeyoutiful.model.Empresa;

public class NovoServicoEmpresaActivity extends AppCompatActivity {

    private EditText editProdutoNome, editProdutoDescricao,
            editProdutoPreco;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_servico_empresa2);

        //Configuração Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Serviço");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void validarDadosProduto(View view){
        //Validar campos preenchidos
        String nome = editProdutoNome.getText().toString();
        String descicao = editProdutoDescricao.getText().toString();
        String preco = editProdutoPreco.getText().toString();

        if( !nome.isEmpty() ){
            if( !descicao.isEmpty() ){
                if( !preco.isEmpty() ){


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
