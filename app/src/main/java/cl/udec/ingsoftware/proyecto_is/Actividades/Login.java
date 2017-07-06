package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import cl.udec.ingsoftware.proyecto_is.Modelo.Usuario;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorUsuario;
import cl.udec.ingsoftware.proyecto_is.R;

public class Login extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    PresentadorUsuario presentadorUsuario;
    Button b1,b2;
    EditText ed1,ed2;
    CheckBox checkBox ;
    public final static String EXTRA_MESSAGE = "cl.udec.ingsoftware.proyecto_is.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        b1 = (Button)findViewById(R.id.button_login);
        ed1 = (EditText)findViewById(R.id.mail);
        ed2 = (EditText)findViewById(R.id.pass);
        checkBox = (CheckBox) findViewById(R.id.guardar_contraseña);
        final boolean checked = getChecked();
        checkBox.setChecked(checked);
        if(checked){
            String name = getUsuarioEmailSP();
            String pass = getUsuarioPasswordSP();
            if(name!=null)
                ed1.setText(name);
            if(pass != null)
                ed2.setText(pass);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presentadorUsuario=new PresentadorUsuario(v.getContext(),ed1.getText().toString(),ed2.getText().toString());
                if(presentadorUsuario.login()){
                    if(checkBox.isChecked()){
                        setChecked();
                    }
                    guardarEnSP(presentadorUsuario.getUsuario(),ed2.getText().toString());
                    if(presentadorUsuario.getUsuario().getRol()==1){
                        vista_empresario("asda");
                    }else{
                        vista_turista("asdas");
                    }
                }else{
                    Toast.makeText(v.getContext(),"Email o Contraseña Incorrecto",Toast.LENGTH_SHORT).show();
                }
            }});
    }

    private void setChecked() {
        SharedPreferences sp = this.getSharedPreferences("checked",0);
        sp.edit().putBoolean("checked",true).commit();
    }

    private boolean getChecked() {
        SharedPreferences sp = this.getSharedPreferences("checked",0);
        if(sp.getBoolean("checked",false)){
            return true;
        }else
            return false;
    }

    private void guardarEnSP(Usuario usuario,String password) {
        SharedPreferences sp = this.getSharedPreferences("usuario",0);
        sp.edit().putString("name",usuario.getName()).apply();
        sp.edit().putString("email",usuario.getEmail()).apply();
        sp.edit().putInt("rol",usuario.getRol()).apply();
        sp.edit().putString("pass",password).apply();
        sp.edit().commit();
    }
    private String getUsuarioEmailSP(){
        SharedPreferences sp = this.getSharedPreferences("usuario",0);
        return sp.getString("email",null);
    }
    private String getUsuarioPasswordSP(){
        SharedPreferences sp = this.getSharedPreferences("usuario",0);
        return sp.getString("pass",null);
    }

    private void vista_empresario(String message){
        Intent intent = new Intent(this, Vista_empresario.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    private void vista_turista(String message){
        Intent intent = new Intent(this, Vista_turista.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.principal) {
            gotoPrincipal();
        } else if (id == R.id.login) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoPrincipal() {
        startActivity(new Intent(this,MapaBusquedaItinerarioActivity.class));
    }
}
