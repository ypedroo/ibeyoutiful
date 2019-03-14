package ibeyoutiful.github.ibeyoutiful.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {

    private static DatabaseReference refereciaFirebase;
    private static FirebaseAuth refereciaAutenticacao;
    private static StorageReference referenciaStorage;

    //Retorna instancia do DatabaseReference reference
    public static DatabaseReference getFirebase() {
        if ( refereciaFirebase == null) {
            refereciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return refereciaFirebase;
    }

    //Retorna instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAuttenticacao(){
        if( refereciaAutenticacao == null) {
            refereciaAutenticacao = FirebaseAuth.getInstance();
        }
        return refereciaAutenticacao;
    }

    //Retorna instancia do StorageReference
    public static StorageReference getReferenciaStorage() {
        if( referenciaStorage == null ){
            referenciaStorage = FirebaseStorage.getInstance().getReference();
        }
        return  referenciaStorage;
    }
}
