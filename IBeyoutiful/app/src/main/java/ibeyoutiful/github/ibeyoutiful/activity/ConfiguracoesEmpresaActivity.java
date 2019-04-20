package ibeyoutiful.github.ibeyoutiful.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import ibeyoutiful.github.ibeyoutiful.R;

public class ConfiguracoesEmpresaActivity extends AppCompatActivity {

    private EditText editNomeEmpresa, editTipoServico, editValorMedio;
    private ImageView perfilEmpresa;

    private static final int SELECAO_GALERIA = 200;

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

        perfilEmpresa.setOnClickListener(new View.OnClickListener() {
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
                    perfilEmpresa.setImageBitmap( imagem );
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
        perfilEmpresa = findViewById(R.id.perfilEmpresa);
    }
}
