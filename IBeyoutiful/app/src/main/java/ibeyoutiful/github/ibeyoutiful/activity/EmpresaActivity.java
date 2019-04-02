package ibeyoutiful.github.ibeyoutiful.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import ibeyoutiful.github.ibeyoutiful.R;
import ibeyoutiful.github.ibeyoutiful.helper.ConfiguracaoFirebase;

public class EmpresaActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empresa);

        autenticacao= ConfiguracaoFirebase.getFirebaseAutenticacao();
        //Configuração Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("IBe you tiful - Profissional");
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_empresa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuSair:
                deslogarUsuario();
                break;
            case R.id.menuConfiguracoes:
                abrirConfiguracoes();
                break;
            //case R.id.menuNovoServico:
                //abrirNovoServico();
                //break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario(){
        try{
            autenticacao.signOut();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void abrirConfiguracoes() {
        startActivity(new Intent(EmpresaActivity.this, ConfiguracoesEmpresaActivity.class));

    }

   // private void abrirNovoServico(){
     //   startActivity(new Intent( NovoServicoEmpresaActivity.this, NovoServicoEmpresaActivity.class));
    //}
}
