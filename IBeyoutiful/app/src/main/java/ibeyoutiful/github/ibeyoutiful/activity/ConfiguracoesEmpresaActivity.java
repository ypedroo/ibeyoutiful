package ibeyoutiful.github.ibeyoutiful.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

import ibeyoutiful.github.ibeyoutiful.R;

public class ConfiguracoesEmpresaActivity extends AppCompatActivity {

    private EditText editNomeEmpresa, editTipoServico, editValorMedio;
    private ImageView perfilEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_empresa);
        incializarComponentes();

        //Configuração Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void incializarComponentes(){
        editNomeEmpresa = findViewById(R.id.editNomeEmpresa);
        editTipoServico = findViewById(R.id.editTipoServico);
        editValorMedio = findViewById(R.id.editValorMedio);
        perfilEmpresa = findViewById(R.id.perfilEmpresa);
    }
}
