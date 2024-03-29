package io.devexpert.scanner_qr

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.devexpert.scanner_qr.ui.theme.SCANNER_QRTheme

class MainActivity : ComponentActivity() {

    var MY_PERMISSION_CAMERA = 100

//jpaña
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){//validar versION PREGUNTAMOS SI ES MAYOR A LA VERSION 6
            solicitarPermisos()
        }else{
            Toast.makeText(applicationContext, "permisos ya concedidos", Toast.LENGTH_LONG).show()
        }

    }

    //"ContextCompat.checkSelfPermission" es un método que se utiliza para comprobar si la aplicación tiene un permiso específico.
    //"applicationContext" es el contexto de la aplicación, que se utiliza para realizar la verificación del permiso.
    //"Manifest.permission.CAMERA" es la cadena que representa el permiso de la cámara en el archivo de manifiesto de la aplicación.
    //"PackageManager.PERMISSION_GRANTED" es una constante que indica que el permiso ha sido concedido.
    fun solicitarPermisos(){
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){//mainifes des del android no de kotlin

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), MY_PERMISSION_CAMERA)

        }else{
            Toast.makeText(applicationContext, "permisos concedidos anteriormente", Toast.LENGTH_LONG).show()
        }
    }


    //revuelve un codigo de respuesta el int que es el mmandas en el MY_PERMISSION_CAMARE
    //MANDA UN ARREGLO DE PERMISO QUE ES EL MANDAMOS EN EL ARRAYOF(MANIFEST)
    //DESPUES UN ARREGLO DE RESPUESTAS DONDE DICE QUE FUERON NEGADOS O NO LOS PERMISOS

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){//codigo de respuesta
            MY_PERMISSION_CAMERA->{
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){//tiene arreglo de respuestas QUE NO ESTE VACIO
                    Toast.makeText(applicationContext,"permisos concedidos", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,"permisos denegados", Toast.LENGTH_LONG).show()
                }
            }else->{

            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }//api :)
        }

    }

}

//en el manifest
// android:usesCleartextTraffic="true"    //hacer peticiones http que no seguras
//        android:hardwareAccelerated="true"  //mejor rendimiento del hardwaew