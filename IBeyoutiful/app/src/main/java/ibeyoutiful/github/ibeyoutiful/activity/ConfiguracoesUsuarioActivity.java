package ibeyoutiful.github.ibeyoutiful.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import ibeyoutiful.github.ibeyoutiful.R;
import ibeyoutiful.github.ibeyoutiful.helper.ConfiguracaoFirebase;
import ibeyoutiful.github.ibeyoutiful.helper.UsuarioFirebase;
import ibeyoutiful.github.ibeyoutiful.model.Usuario;

public class ConfiguracoesUsuarioActivity extends AppCompatActivity {

    private EditText editUsuarioNome, editUsuarioEndereco;
    private String idUsuario;
    private DatabaseReference firebaseRef;
    private ImageView imagePerfilUsuario;
    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private String urlImagemSelecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_usuario);

        //Configurações Iniciais
        inicializarComponentes();
        idUsuario = UsuarioFirebase.getIdUsuario();
        firebaseRef = ConfiguracaoFirebase.getFirebase();
        storageReference = ConfiguracaoFirebase.getReferenciaStorage();

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

        recuperarDadosUsuario();
    }

    private void recuperarDadosUsuario(){

        DatabaseReference usuarioRef = firebaseRef
                .child("usuarios")
                .child( idUsuario);
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if( dataSnapshot.getValue() != null ){

                    Usuario usuario = dataSnapshot.getValue( Usuario.class );
                    editUsuarioNome.setText( usuario.getNomeUsuario());
                    editUsuarioEndereco.setText( usuario.getEnderecoUsuario());

                    urlImagemSelecionada = usuario.getUrlImagem();

                    if( "" != "" ){
                        Picasso.get()
                                .load( urlImagemSelecionada )
                                .into( imagePerfilUsuario );
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);

            if( resultCode == RESULT_OK ) {
                Bitmap imagem = null;
                try{

                    switch (requestCode){
                        case SELECAO_GALERIA:
                            Uri localImage = data.getData();
                            imagem = MediaStore.Images
                                    .Media
                                    .getBitmap(
                                            getContentResolver(),
                                            localImage
                                    );
                            break;
                    }

                    if( imagem != null ){
                        imagePerfilUsuario.setImageBitmap( imagem );

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        imagem.compress(Bitmap.CompressFormat.JPEG,70, baos);
                        byte[] dadosImagem = baos.toByteArray();

                        StorageReference imagemRef = storageReference
                                .child("imagens")
                                .child("usuarios")
                                .child(idUsuario + "jpeg");

                        UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ConfiguracoesUsuarioActivity.this, "Erro ao fazer o upload da imagem"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                urlImagemSelecionada  = taskSnapshot.getDownloadUrl().toString();
                                Toast.makeText(ConfiguracoesUsuarioActivity.this, "Sucesso ao fazer o upload da imagem"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }


    private void inicializarComponentes(){
        editUsuarioNome = findViewById(R.id.editNomeUsuario);
        editUsuarioEndereco = findViewById(R.id.editUsuarioEndereco);
        imagePerfilUsuario = findViewById(R.id.imagePerfilUsuario);
    }
}
