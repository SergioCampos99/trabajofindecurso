package com.example.newsapp4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newsapp4.Connection.ConnectionClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class profile_activity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Button signOut;
    TextView nameTV, emailTV, memberTV;
    ImageView ivProfile;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    /*Connection con;
    String connectionResult = "";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);
        //se relacionan las variables con sus objetos
        signOut = findViewById(R.id.btnSignOut);
        nameTV = findViewById(R.id.tvUsername);
        emailTV = findViewById(R.id.tvEmail);
        memberTV = findViewById(R.id.tvMiembro);
        ivProfile = findViewById(R.id.ivProfile);


        //Se configura el siguiente metodo para pedir tanto la ID como el email y otros elementos
        //basicos del perfil
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        //Ahora se construye un cliente de google con las especificaciones que le dimos a la variable gso
        //proveniente de GoogleSignInOptions
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(profile_activity.this);
        if(acct != null){
            String personUsername = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri profilePhoto = acct.getPhotoUrl();

            nameTV.setText(personUsername);
            emailTV.setText(personEmail);
            memberTV.setText(personId);
            //Con la herramienta glide podremos obtener la foto y colocarla en el recuadro del ImageView
            Glide.with(this).load(profilePhoto).into(ivProfile);
        }
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutMethod();
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeria = new Intent();
                galeria.setType("image/*");
                galeria.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galeria, "Selecciona una foto de tu galeria"), PICK_IMAGE);
            }
        });
    }

   /* public void getDataFromDatabase(View v){
        try{
            ConnectionClass connectionHelper = new ConnectionClass();
            con = connectionClass(ConnectionClass.ip.toString(), ConnectionClass.port.toString(),ConnectionClass.db.toString(),ConnectionClass.un.toString(),ConnectionClass.pass.toString());
            if(con != null){
                String query = "SELECT * FROM users";
            }

        }catch (Exception e){
            Log.e("Error" + e.getMessage());
        }
    }*/

    //En este metodo, llamaremos al almacenamiento de nuestro telefono para poder asi
    //obtener las fotografias que tengamos y a??adir una como foto de perfil.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ivProfile.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void signOutMethod () {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(profile_activity.this, "Sesion cerrada con exito", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(profile_activity.this, MainActivity.class));
                finish();
            }
        });

    }

    /*public Connection connectionClass(String server, String port, String database, String username, String pass){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try{
            //Obtencion del driver jtds para la conexion con la base de datos
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL= "jdbc:jtds:sqlserver://"+ server + ":" +port+ ";"+ "databaseName="+ database +";user="+username+";password="+pass+";";
            connection = DriverManager.getConnection(connectionURL);
            System.out.println(connectionURL);
        }catch (Exception e){
            Log.e("SQL Connection Error : ", e.getMessage());
            System.out.println(connectionURL);
        }

        return connection;

    }*/




    public void atras(View vista){
        finish();
    }
}