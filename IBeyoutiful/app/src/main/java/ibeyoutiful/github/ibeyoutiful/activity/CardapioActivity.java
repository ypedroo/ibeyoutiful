package ibeyoutiful.github.ibeyoutiful.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import ibeyoutiful.github.ibeyoutiful.R;
import ibeyoutiful.github.ibeyoutiful.adapter.AdapterProduto;
import ibeyoutiful.github.ibeyoutiful.helper.ConfiguracaoFirebase;
import ibeyoutiful.github.ibeyoutiful.helper.UsuarioFirebase;
import ibeyoutiful.github.ibeyoutiful.listener.RecyclerItemClickListener;
import ibeyoutiful.github.ibeyoutiful.model.Empresa;
import ibeyoutiful.github.ibeyoutiful.model.ItemPedido;
import ibeyoutiful.github.ibeyoutiful.model.Pedido;
import ibeyoutiful.github.ibeyoutiful.model.Produto;
import ibeyoutiful.github.ibeyoutiful.model.Usuario;

public class CardapioActivity extends AppCompatActivity {

    private RecyclerView recyclerProdutosCardapio;
    private ImageView imageEmpresaCardapio;
    private TextView textNomeEmpresaCardapio;
    private Empresa empresaSelecionada;
    private TextView textCarrinhoQtd, textCarrinhoTotal;

    private AdapterProduto adapterProduto;
    private List<Produto> produtos = new ArrayList<>();
    private List<ItemPedido> itensCarrinho = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private String idEmpresa;
    private AlertDialog dialog;
    private String idUsuarioLogado;
    private Usuario usuario;
    private Pedido pedidoRecuperado;
    private int qtdItensCarrinho;
    private Double totalCarrinho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        //Configuções Iniciais
        inicializarComponentes();
        firebaseRef = ConfiguracaoFirebase.getFirebase();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

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

        //Configurar evento de click
        recyclerProdutosCardapio.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerProdutosCardapio,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                confirmarQuantidade(position);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        })
        );

        //Recuperar Produtos para empresa
        recuperarProdutos();
        recuperarDadosUsuario();
    }

    private void confirmarQuantidade(final int posicao) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quantidade");
        builder.setMessage("Digite a Quantidade");

        final EditText editQuantidade = new EditText(this);
        editQuantidade.setText("1");

        builder.setView( editQuantidade );

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String quantidade = editQuantidade.getText().toString();

                Produto produtoSelecionado = produtos.get(posicao);
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setIdProduto( produtoSelecionado.getIdProduto() );
                itemPedido.setNomePorduto( produtoSelecionado.getNome() );
                itemPedido.setPreco( produtoSelecionado.getPreco() );
                itemPedido.setQuantidade( Integer.parseInt(quantidade) );

                itensCarrinho.add( itemPedido );

                if( pedidoRecuperado == null){
                    pedidoRecuperado = new Pedido( idUsuarioLogado, idEmpresa);
                }

                pedidoRecuperado.setNome( usuario.getNomeUsuario() );
                pedidoRecuperado.setEndereco( usuario.getEnderecoUsuario() );
                pedidoRecuperado.setItens( itensCarrinho );
                pedidoRecuperado.salvar();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void recuperarDadosUsuario() {

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Dados")
                .setCancelable( false )
                .build();
        dialog.show();

        DatabaseReference usuariosRef = firebaseRef
                .child("usuarios")
                .child( idUsuarioLogado );

        usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if( dataSnapshot.getValue() != null ){
                    usuario = dataSnapshot.getValue(Usuario.class);
                }
                recuperarPedido();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
}

    private void recuperarPedido() {

        DatabaseReference pedidoRef = firebaseRef
                .child("pedidos_usuario")
                .child( idEmpresa )
                .child( idUsuarioLogado );

        pedidoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    qtdItensCarrinho = 0;
                    totalCarrinho = 0.0;
                    itensCarrinho = new ArrayList<>();

                if(dataSnapshot.getValue() != null){

                    pedidoRecuperado = dataSnapshot.getValue(Pedido.class);
                    itensCarrinho = pedidoRecuperado.getItens();

                    for (ItemPedido itemPedido: itensCarrinho){

                        int qtde = itemPedido.getQuantidade();
                        Double preco = itemPedido.getPreco();

                        totalCarrinho += (qtde * preco);
                        qtdItensCarrinho += qtde;

                    }

                }

                DecimalFormat df = new DecimalFormat( "0.00");

                textCarrinhoQtd.setText( "qtd: " + String.valueOf(qtdItensCarrinho) );
                textCarrinhoTotal.setText("R$: " + df.format( totalCarrinho ));

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cardapio, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuPedido:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void inicializarComponentes() {
        recyclerProdutosCardapio = findViewById(R.id.recyclerProdutoCardapio);
        imageEmpresaCardapio = findViewById(R.id.imageEmpresaCardapio);
        textNomeEmpresaCardapio = findViewById(R.id.textNomeEmpresaCardapio);

        textCarrinhoQtd = findViewById( R.id.textCarrinhoQtd);
        textCarrinhoTotal = findViewById(R.id.textCarrinhoTotal);

    }
}
