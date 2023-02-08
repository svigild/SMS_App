package com.example.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //ATRIBUTOS
    EditText etNumero, etMensaje;
    Button btnEnviar;

    //CONSTANTE PERMISOS
    private static final int SOLICITUD_MANDAR_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initReferences();
        setListenersToButtons();

    }

    /**
     * Método que obtiene las referencias a las Views.
     */
    private void initReferences(){
        etNumero = findViewById(R.id.editTextNumero);
        etMensaje = findViewById(R.id.editTextMensaje);
        btnEnviar = findViewById(R.id.buttonEnviar);
    }

    /**
     * Método que asigna los escuchadores a los botones
     */
    private void setListenersToButtons(){
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Comprobar que ambos campos no están vacíos.
                 * Si están vacíos, se muestra el error y no se enviará el mensaje
                 * Si no lo están, se mandará el mensaje
                 */
                if (bothFieldsFilled()){
                    Toast.makeText(MainActivity.this, "Por favor, rellena ambos campos", Toast.LENGTH_SHORT).show();
                } else {

                    enviarMensaje(etNumero.getText().toString(), etMensaje.getText().toString());
                }
            }
        });
    }

    /**
     *
     * @param numTelefono al que se desea enviar un mensaje.
     * @param mensaje el mensaje que se enviará.
     */
    public void enviarMensaje(String numTelefono, String mensaje) {
        //Compruebo si tengo permiso
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            //Permiso condedido
            mandarMensaje(numTelefono, mensaje);
            Toast.makeText(this, getString(R.string.exito), Toast.LENGTH_SHORT).show();
        } else {
            //Permiso no condedido
            solicitarPermiso(Manifest.permission.SEND_SMS, "Debe de activar el permiso de mandar mensajes para poder mandar mensajes.", SOLICITUD_MANDAR_SMS, this);
        }
    }

    public void solicitarPermiso(final String permiso, String justificacion, final int requestCode, final Activity activity){
        //Mostrar solicitud de porque necesito este permiso??
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permiso)){
            new AlertDialog.Builder(activity)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(activity, new String[] {permiso}, requestCode);
                        }
                    }).show();
        } else {
            //Pedir permiso
            ActivityCompat.requestPermissions(activity, new String[]{permiso}, requestCode);
        }
    }

    /**
     *
     * @param numeroTelefono al que se mandará el mensaje.
     * @param mensaje el mensaje en sí.
     */
    private void mandarMensaje (String numeroTelefono, String mensaje){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(numeroTelefono, null, mensaje, null, null);
    }

    /**
     *
     * @return true si no está relleno algún campo.
     */
    private boolean bothFieldsFilled(){
        return (etNumero.getText().toString().matches("") || (etMensaje.getText().toString().matches("")));
    }


}