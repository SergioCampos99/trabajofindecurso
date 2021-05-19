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
import android.widget.VideoView;

import com.example.newsapp4.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {

    Button botonInicio, botonRegistro;
    VideoView videoBack;
    EditText pass, email, username;
    Intent intencion;
    Connection con;
    Statement stmt;
    //TextView
    TextView status;

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
        /*
        email.setVisibility(View.GONE);


        botonRegistro.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                email.setVisibility(View.VISIBLE);
            }
        });

        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setVisibility(View.GONE);
            }
        });
        */
    }

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

    @SuppressLint("NewApi")
    public Connection connectionClass(String server, String port, String database, String username, String pass){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try{
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                connectionURL= "jdbc:jtds:sqlserver://"+ server + ":" +port+ ";"+ "databaseName="+ database +";user="+username+";password="+pass+";";
                connection = DriverManager.getConnection(connectionURL);
                System.out.println(connectionURL);
        }catch (Exception e){
            Log.e("SQL Connection Error : ", e.getMessage());
            System.out.println(connectionURL);
        }

        return connection;

    };


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


    /*public void PasarDatos(View vista){
        intencion = new Intent(this, menuContent.class);
        startActivity(intencion);
    }*/


}