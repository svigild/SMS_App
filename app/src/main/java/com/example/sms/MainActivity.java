package com.example.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //TEXTVIEW
    EditText etNumero, etMensaje;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initReferences();
        setListenersToButtons();
        declareSMSPermissions();
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
                SmsManager sms = SmsManager.getDefault();

                /**
                 * Comprobar que ambos campos no están vacíos.
                 * Si están vacíos, se muestra el error y no se enviará el mensaje
                 * Si no lo están, se mandará el mensaje
                 */
                if (bothFieldsFilled()){
                    Toast.makeText(MainActivity.this, "Por favor, rellena ambos campos", Toast.LENGTH_SHORT).show();
                } else {
                    //Enviar mensaje
                    sms.sendTextMessage(etNumero.getText().toString(), null, etMensaje.getText().toString(), null, null );
                    //Texto mensaje enviado con éxito
                    //Texto de mensaje enviado con éxito
                    Toast.makeText(MainActivity.this, "SMS Enviado con éxito", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Método que declara los permisos de mensajes SMS
     */
    private void declareSMSPermissions(){
        //Si no tiene permisos:
        if ((ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS))!= (PackageManager.PERMISSION_GRANTED)){
            //Manda cuadro de diálogo que pide al usuario aceptar el permiso de mandar SMS.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    /**
     * Comprueba que ambos campos están rellenados (EditText Número y EditText Mensaje)
     * Devuelve 'true' si algún campo está vacío
     * Devuelve 'false' si ambos campos tienen texto
     */
    private boolean bothFieldsFilled(){
        return (etNumero.getText().toString().matches("") || (etMensaje.getText().toString().matches("")));
    }
}