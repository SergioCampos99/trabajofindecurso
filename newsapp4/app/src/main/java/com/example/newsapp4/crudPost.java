package com.example.newsapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsapp4.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CrudPost extends AppCompatActivity {

    EditText etTitle, etDesc;
    Button btnPost, btnBack;

    //Conexion con SQL Server
    Connection con;
    Statement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_post);

        etTitle = findViewById(R.id.etTituloPost);
        etDesc = findViewById(R.id.etDescPost);
        btnPost = findViewById(R.id.btnPostear);
        btnBack = findViewById(R.id.btnAtras);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CrudPost.makePost().execute("");
            }
        });
    }

    public class makePost extends AsyncTask<String, String, String>{
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            Toast.makeText(CrudPost.this, "Sending post to database", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(CrudPost.this, "Data sent!", Toast.LENGTH_SHORT).show();
            etTitle.setText("");
            etDesc.setText("");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                con = connectionClass(ConnectionClass.ip.toString(), ConnectionClass.port.toString(),ConnectionClass.db.toString(),ConnectionClass.un.toString(),ConnectionClass.pass.toString());
                if(con == null){
                    z = "Check your Internet Connection";
                }else{
                    //Inserta lo que escribimos en los campos del usuario, contraseña y email.
                    String sql = "INSERT INTO articles (Title, Descri) VALUES ('"+ etTitle.getText()+"', '"+etDesc.getText()+"')";
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

}