package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.Context;

import cl.udec.ingsoftware.proyecto_is.Modelo.Usuario;

/**
 * Created by meraioth on 05-07-17.
 */

public class PresentadorUsuario {
    private int id;
    private String name,pass;
    private Context mcontext;
    private Usuario usuario;
    private Formateador formateador;
    public PresentadorUsuario(Context mcontext,String name,String pass){
        this.mcontext=mcontext;
        this.name=name;
        this.pass=pass;
        this.formateador = new Formateador(this.mcontext);

    }
    public boolean login(){
        usuario=formateador.getUsuario(this.name,this.pass);
        if(usuario==null){
            return false;
        }else return true;

    }
    public int getRol(){
        return this.usuario.getRol();
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

}
