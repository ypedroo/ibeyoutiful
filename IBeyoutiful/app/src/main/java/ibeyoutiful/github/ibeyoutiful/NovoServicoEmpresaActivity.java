package ibeyoutiful.github.ibeyoutiful;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class NovoServicoEmpresaActivity extends AppCompatActivity {

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
}
