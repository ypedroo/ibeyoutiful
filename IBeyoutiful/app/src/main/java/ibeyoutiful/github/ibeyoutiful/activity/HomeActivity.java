package ibeyoutiful.github.ibeyoutiful.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import ibeyoutiful.github.ibeyoutiful.R;
import ibeyoutiful.github.ibeyoutiful.adapter.AdapterEmpresa;
import ibeyoutiful.github.ibeyoutiful.adapter.AdapterProduto;
import ibeyoutiful.github.ibeyoutiful.helper.ConfiguracaoFirebase;
import ibeyoutiful.github.ibeyoutiful.model.Empresa;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private MaterialSearchView searchView;
    private RecyclerView recyclerEmpresa;
    private List<Empresa> empresas = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private AdapterEmpresa adapterEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        inicializarComponentes();
        autenticacao= ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseRef = ConfiguracaoFirebase.getFirebase();

        //Configuração Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Beyoutiful");
        setSupportActionBar(toolbar);

        //Configuraçao recyclerView
        recyclerEmpresa.setLayoutManager( new LinearLayoutManager(this));
        recyclerEmpresa.setHasFixedSize(true);
        adapterEmpresa = new AdapterEmpresa( empresas );
        recyclerEmpresa.setAdapter( adapterEmpresa );

        //Recuperar Produtos para empresa
        recuperarEmpresas();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_usuario, menu);
        //Config search button
        MenuItem item = menu.findItem(R.id.menuPesquisa);
        searchView.setMenuItem(item);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuSair:
                deslogarUsuario();
                break;
            case R.id.menuConfiguracoes:
                abrirConfiguracoes();
                break;
            case R.id.menuPesquisa:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void inicializarComponentes(){
        searchView = findViewById(R.id.materialSearchView);
        recyclerEmpresa = findViewById(R.id.recyclerServicos);
    }


    private void deslogarUsuario(){
        try{
            autenticacao.signOut();
            finish();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void abrirConfiguracoes() {
        startActivity(new Intent(HomeActivity.this, ConfiguracoesUsuarioActivity.class));

    }
}
