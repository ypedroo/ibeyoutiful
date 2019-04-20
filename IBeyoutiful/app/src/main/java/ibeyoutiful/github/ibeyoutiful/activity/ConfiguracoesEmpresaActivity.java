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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import ibeyoutiful.github.ibeyoutiful.R;
import ibeyoutiful.github.ibeyoutiful.helper.ConfiguracaoFirebase;
import ibeyoutiful.github.ibeyoutiful.helper.UsuarioFirebase;
import ibeyoutiful.github.ibeyoutiful.model.Empresa;

public class ConfiguracoesEmpresaActivity extends AppCompatActivity {

    private EditText editNomeEmpresa, editTipoServico, editValorMedio;
    private ImageView imagePerfilEmpresa;

    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private String idUsuarioLogado;
    private String urlImagemSelecionada = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_empresa);
        //Configurações Iniciais
        incializarComponentes();
        storageReference = ConfiguracaoFirebase.getReferenciaStorage();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

        //Configuração Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagePerfilEmpresa.setOnClickListener(new View.OnClickListener() {
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

    public void validarDadosEmpresa(View view){
        //Validar campos preenchidos
        String nome = editNomeEmpresa.getText().toString();
        String tipo = editTipoServico.getText().toString();
        String valor = editValorMedio.getText().toString();

        if( !nome.isEmpty() ){
            if( !tipo.isEmpty() ){
                if( !valor.isEmpty() ){

                    Empresa empresa = new Empresa();
                    empresa.setIdUsuario( idUsuarioLogado );
                    empresa.setNome( nome );
                    empresa.setTipo( tipo );
                    empresa.setValor( Double.parseDouble(valor));
                    empresa.setUrlImagem( urlImagemSelecionada );
                    empresa.salvar();

                }else{
                    exibirMensagem("Digite um valor médio dos serviços");
                }

            }else{
                exibirMensagem("Digite os tipos de serviços");
            }

        }else{
            exibirMensagem("Digite um nome para a Empresa");
        }

    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                    imagePerfilEmpresa.setImageBitmap( imagem );

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG,70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    StorageReference imagemRef = storageReference
                            .child("imagens")
                            .child("empresas")
                            .child(idUsuarioLogado + "jpeg");

                    UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ConfiguracoesEmpresaActivity.this, "Erro ao fazer o upload da imagem"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            urlImagemSelecionada  = taskSnapshot.getDownloadUrl().toString();
                            Toast.makeText(ConfiguracoesEmpresaActivity.this, "Sucesso ao fazer o upload da imagem"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    private void incializarComponentes(){
        editNomeEmpresa = findViewById(R.id.editNomeEmpresa);
        editTipoServico = findViewById(R.id.editTipoServico);
        editValorMedio = findViewById(R.id.editValorMedio);
        imagePerfilEmpresa = findViewById(R.id.imagePerfilEmpresa);
    }
}
