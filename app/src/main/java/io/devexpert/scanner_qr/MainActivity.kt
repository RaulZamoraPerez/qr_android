package io.devexpert.scanner_qr

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
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
import com.google.zxing.integration.android.IntentIntegrator
import io.devexpert.scanner_qr.ui.theme.SCANNER_QRTheme

class MainActivity : ComponentActivity() {


    var MY_PERMISSION_CAMERA = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            solicitarPermisos()

        }else{
            agregarEventoBoton()
            Toast.makeText(applicationContext,"Permisos ya consedidos", Toast.LENGTH_LONG).show()
        }
    }
    fun solicitarPermisos(){
        if (ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            //solicitar el permiso
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),MY_PERMISSION_CAMERA)
        }else{
            agregarEventoBoton()
            Toast.makeText(applicationContext, "permisos consedidos anteriormentete", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            MY_PERMISSION_CAMERA->{
                if (grantResults.isNotEmpty()&& grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    agregarEventoBoton()
                    Toast.makeText(applicationContext,"Permisos concedidos", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,"Permisos denegados", Toast.LENGTH_LONG).show()
                }
            }else->{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        }
    }
    fun agregarEventoBoton(){
        val btneScannear: Button = findViewById(R.id.btnScannear) // Asegúrate de tener esta línea si no la tienes en el código
        btneScannear.isEnabled = true
        btneScannear.setOnClickListener{
            IntentIntegrator(this)
                .initiateScan()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if (result != null){
            if (result.contents == null){
                Toast.makeText(applicationContext, "cancelado", Toast.LENGTH_SHORT).show()
            }else{
                var contenido = result.contents
                Toast.makeText(applicationContext, "contenido $contenido", Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}