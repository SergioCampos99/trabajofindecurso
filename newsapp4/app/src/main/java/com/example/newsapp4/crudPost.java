package com.example.newsapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsapp4.Connection.ConnectionClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class crudPost extends AppCompatActivity {

    EditText etTitle, etDesc;
    Button btnPost, btnBack, btnSee;

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
        btnSee = findViewById(R.id.btnVerPost);

        btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(), postsBarrio.class);
                startActivity(intencion);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = etTitle.getText().toString();
                String desc = etDesc.getText().toString();
                if(TextUtils.isEmpty(titulo) || TextUtils.isEmpty(desc)){
                    Toast.makeText(crudPost.this, "Titulo o Description vacío", Toast.LENGTH_SHORT).show();
                }
                try{
                    new crudPost.makePost().execute("");
                        }catch(Exception e){
                            e.printStackTrace();
                        }


            }
        });
    }

    public class makePost extends AsyncTask<String, String, String>{
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            Toast.makeText(crudPost.this, "Sending", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(crudPost.this, "Sent", Toast.LENGTH_SHORT).show();
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
                    //Inserta lo que escribimos en los campos del Titulo y descripcion.
                    String sql = "INSERT INTO posts (Title, Description) VALUES ('"+ etTitle.getText()+"', '"+etDesc.getText()+"')";
                    System.out.println("Hola1 " + sql);
                    stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    System.out.println("Hola2 " + sql);
                }
            }catch(Exception e){
                isSuccess = false;
                z = e.getMessage();
            }
            return z;
        }
    }

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
        }catch (Exception e){
            Log.e("SQL Connection Error : ", e.getMessage());
            System.out.println(connectionURL);
        }

        return connection;

    }















    /*public Connection bdConec() throws ClassNotFoundException, InstantiationException, IllegalAccessError, SQLException {
        Connection connection = null;
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String bd = "AppNews";
            String server = "85.251.26.50:8887";

            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://" + server +";database="+bd+";user=scampos;password=12345");
            System.out.println("Hola" + connection);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public void clear(){
        etTitle.setText("");
        etDesc.setText("");
    }
    public void agregarPost() throws IOException {
        try{
            //INSERT INTO posts (Title, Description) VALUES ('"+etTitle.getText()+"', '"+etDesc.getText()+"')
            String sql = "INSERT INTO dbo.posts (Title, Description) VALUES (?,?)";
            System.out.println("Hola " + sql);
            PreparedStatement ps = bdConec().prepareStatement(sql);
            ps.setString(1, etTitle.getText().toString());
            System.out.println("Hola " + ps);
            ps.setString(2, etDesc.getText().toString());
            System.out.println("Hola " + ps);
            ps.executeUpdate();
            System.out.println("Hola " + ps);
            *//*PreparedStatement ps = bdConec().prepareStatement("INSERT INTO dbo.posts (Title, Description) VALUES ('"+ etTitle.getText()+"', '"+etDesc.getText()+"')");
            ps.setString(1, etTitle.getText().toString());
            ps.setString(2, etDesc.getText().toString());
            ps.executeUpdate();
            clear();*//*
            Toast.makeText(this, "Post added to database!", Toast.LENGTH_SHORT).show();
            clear();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

}