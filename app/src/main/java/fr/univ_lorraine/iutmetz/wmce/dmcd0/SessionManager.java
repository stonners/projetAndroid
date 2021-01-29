package fr.univ_lorraine.iutmetz.wmce.dmcd0;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import fr.univ_lorraine.iutmetz.wmce.dmcd0.ui.ConnexionFragment;

public class SessionManager {

SharedPreferences sharedPreferences;
public  SharedPreferences.Editor editor;
public Context context;



//video a finir :
    //7min10 : https://www.youtube.com/watch?v=bBJo0Gj69Ug
    //https://www.youtube.com/watch?v=q6a4h_7AtOE


public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("test",0);
        editor=sharedPreferences.edit();
    }
    public void createSession(String id){
        editor.putBoolean("login", true);
        editor.putString("id_client",id);
        editor.apply();
    }

    public boolean isLogin(){
    return sharedPreferences.getBoolean("login",false);
    }

    public String getIdClient(){

    return sharedPreferences.getString("id_client","-1");
    }

    public  void logout(){
        editor.clear();
        editor.commit();

    }
}
