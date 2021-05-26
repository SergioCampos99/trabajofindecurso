package com.example.newsapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.newsapp4.Connection.ConnectionClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {

    Button botonInicio, botonRegistro;
    VideoView videoBack;
    EditText pass, email, username;
    Intent intencion;

    //Conexion con SQL Server
    Connection con;
    Statement stmt;

    //TextView
    TextView status;

    //Google SignIn Button
    int RC_SIGN_IN = 0;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoBack = (VideoView) findViewById(R.id.vvBack);

        //btnRegistro
        botonRegistro = (Button) findViewById(R.id.btn_registro);

        //TextView
        status = (TextView) findViewById(R.id.tvRegister);

        //editText
        email = (EditText) findViewById(R.id.etEmail);
        pass = (EditText) findViewById(R.id.etPass);
        username = (EditText) findViewById(R.id.etUsername);

        //Google
        signInButton = findViewById(R.id.googleSignInBtn);

        //El boton de Google, una vez checkeado que es un inicio de sesion correcto, ejecutrá el metodo signIn()
        //el cual
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new MainActivity.registerUser().execute("");
            }
        });

        botonInicio = (Button) findViewById(R.id.btn_inicioSesion);

        String path ="android.resource://com.example.newsapp4/"+R.raw.mix;
        Uri u = Uri.parse(path);
        videoBack.setVideoURI(u);
        videoBack.start();

        videoBack.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        //En el apartado de registro, si pulsamos el boton de inicio de sesion nos mandara a la actividad la cual tendremos que insertar
        //los datos de inicio de sesion
        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intencion);
                finish();
            }
        });
    }

    //Google
    private void signIn(){
        Intent signInIntencion = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntencion, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Este es el resultado de haber presionado e iniciado la intencion desde la variable GoogleSignInClient getSignInIntent
        if (requestCode == RC_SIGN_IN){
            //esta tarea siempre estará activa, por lo que no hace falta añadir un listener
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            startActivity(new Intent(MainActivity.this, menuContent.class));
        }catch(ApiException e){
            Log.w("Google Sign In Error", "SignInResult: failed code=" + e.getStackTrace());
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onStart(){
        //Ahora el metodo onStart va a observar si hay ya una cuenta de google iniciada sesion o registrada
        //El metodo GoogleSignInAccount no sera nulo.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            startActivity(new Intent(MainActivity.this, menuContent.class));
        }
        super.onStart();
    }


    //Registrar un usuario manualmente con la base de datos de SQL Server
    public class registerUser extends AsyncTask<String, String, String>{
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            status.setText("Sending data to Database");
        }

        @Override
        protected void onPostExecute(String s) {
            status.setText("Registration Succesfull");
            username.setText("");
            email.setText("");
            pass.setText("");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                con = connectionClass(ConnectionClass.ip.toString(), ConnectionClass.port.toString(),ConnectionClass.db.toString(),ConnectionClass.un.toString(),ConnectionClass.pass.toString());
                if(con == null){
                    z = "Check your Internet Connection";
                }else{
                    //Inserta lo que escribimos en los campos del usuario, contraseña y email.
                    String sql = "INSERT INTO users (Username, Pass, Email) VALUES ('"+ username.getText()+"', '"+pass.getText()+"','"+email.getText()+"')";
                    stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                }
            }catch(Exception e){
                    isSuccess = false;
                    z = e.getMessage();
            }
            return z;
        }
    }

    //Conexion a la base de datos de SQL Server
    @SuppressLint("NewApi")
    public Connection connectionClass(String server, String port, String database, String username, String pass){
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

    }



    //Metodos para que el video en el fondo funcione.
    protected void onResume(){
        videoBack.resume();
        super.onResume();
    }
    protected void onPause(){
        videoBack.suspend();
        super.onPause();
    }
    protected void onDestroy(){
        videoBack.stopPlayback();
        super.onDestroy();
    }
}