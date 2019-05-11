package ibeyoutiful.github.ibeyoutiful.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ibeyoutiful.github.ibeyoutiful.R;
import ibeyoutiful.github.ibeyoutiful.adapter.AdapterProduto;
import ibeyoutiful.github.ibeyoutiful.helper.ConfiguracaoFirebase;
import ibeyoutiful.github.ibeyoutiful.model.Empresa;
import ibeyoutiful.github.ibeyoutiful.model.Produto;

public class CardapioActivity extends AppCompatActivity {

    private RecyclerView recyclerProdutosCardapio;
    private ImageView imageEmpresaCardapio;
    private TextView textNomeEmpresaCardapio;
    private Empresa empresaSelecionada;
    private AdapterProduto adapterProduto;
    private List<Produto> produtos = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private String idEmpresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        //Configuções Iniciais
        inicializarComponentes();
        firebaseRef = ConfiguracaoFirebase.getFirebase();



        //Recuperar Emprsa
        Bundle bundle = getIntent().getExtras();
        if( bundle != null){
            empresaSelecionada = (Empresa) bundle.getSerializable("empresa");

            textNomeEmpresaCardapio.setText( empresaSelecionada.getNome() );
            idEmpresa = empresaSelecionada.getIdUsuario();

            String url = empresaSelecionada.getUrlImagem();
            Picasso.get().load(url).into( imageEmpresaCardapio );
        }

        //Configuração Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Catálogo de Serviços");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configuraçao recyclerView
        recyclerProdutosCardapio.setLayoutManager( new LinearLayoutManager(this));
        recyclerProdutosCardapio.setHasFixedSize(true);
        adapterProduto = new AdapterProduto( produtos, this);
        recyclerProdutosCardapio.setAdapter( adapterProduto );

        //Recuperar Produtos para empresa
        recuperarProdutos();
        recuperarDadosUsuario();
    }

    private void recuperarDadosUsuario() {


    }

    private void recuperarProdutos() {

        final DatabaseReference produtosRef = firebaseRef
                .child("produtos")
                .child( idEmpresa );
        produtosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos.clear();

                for ( DataSnapshot ds: dataSnapshot.getChildren()) {
                    produtos.add( ds.getValue(Produto.class));
                }

                adapterProduto.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void inicializarComponentes() {
        recyclerProdutosCardapio = findViewById(R.id.recyclerProdutoCardapio);
        imageEmpresaCardapio = findViewById(R.id.imageEmpresaCardapio);
        textNomeEmpresaCardapio = findViewById(R.id.textNomeEmpresaCardapio);

    }
}
