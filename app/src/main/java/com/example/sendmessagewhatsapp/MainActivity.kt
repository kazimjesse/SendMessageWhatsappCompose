package com.example.sendmessagewhatsapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sendmessagewhatsapp.ui.theme.SendMessageWhatsappTheme
import androidx.core.net.toUri
import com.example.sendmessagewhatsapp.ui.theme.greenColor
import java.net.URLEncoder

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SendMessageWhatsappTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary
                ) {

                    Scaffold(

                        topBar = {

                            TopAppBar(
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary,
                                ),

                                title = {
                                    Text(
                                        text = "UTP\nAndroid - Ingeniería de Software 2025",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                })
                        }) {

                        SendWhatsAppMessage(context = LocalContext.current)

                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!hasNotificationPerm()) {
                requestMultiplePermissions.launch(
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            } else {
                checkLocationPerm()
            }
        } else {
            checkLocationPerm()
        }
    }

private val requestMultiplePermissions = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) { permissions ->
    permissions.entries.forEach {
        Log.d("DEBUG", "${it.key} = ${it.value}")
        if (it.key == "android.permission.POST_NOTIFICATIONS" && it.value) {
            askForBGPermission()
        }
    }
}

private val requestLocationPerm = registerForActivityResult(
    ActivityResultContracts.RequestPermission()
) { isGranted ->
    if (isGranted) {
        askForBGPermission()
    } else {
        Log.d(TAG, "Permission is not $isGranted")
    }
}

private val requestBGLocationPerm = registerForActivityResult(
    ActivityResultContracts.RequestPermission()
) { isGranted ->
    if (isGranted) {
        Log.d(TAG, "Background Permission is $isGranted")
    } else {
        Log.d(TAG, "Background Permission is not $isGranted")
    }
}

private fun checkLocationPerm() {
    if (!hasLocationPermission()) {
        requestLocationPerm.launch(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    } else {
        askForBGPermission()
    }
}

private fun askForBGPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (!hasBGLocationPermission()) {
            requestBGLocationPerm.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
    }
}
}



@Composable
fun SendWhatsAppMessage(context: Context) {

    val phoneNumber = remember {
        mutableStateOf("+507")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()

            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(

            text = "Estoy perdido, por favor encuentrenme",
            color = greenColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        TextField(

            value = phoneNumber.value,

            onValueChange = { phoneNumber.value = it },

            placeholder = { Text(text = "Ingrese el # de Contacto a Enviar") },

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

            singleLine = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val locationText = LocationService.lastKnownLocation

                if (locationText == "Ubicación no disponible") {
                    Toast.makeText(
                        context,
                        "La ubicación aún no está disponible. Inicia el servicio y espera unos segundos.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@Button
                }

                val message = "Mi ubicacion actual es: $locationText"
                val encodedMessage = URLEncoder.encode(message, "UTF-8")

                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        java.lang.String.format(
                            "https://api.whatsapp.com/send?phone=%s&text=%s",
                            phoneNumber.value,
                            encodedMessage
                        ).toUri()
                    )
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Text(text = "Enviar Mensaje a WhatsApp")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            //Start Service
            Toast.makeText(context, "Service Start button clicked", Toast.LENGTH_SHORT).show()
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_SERVICE_START
                context.startService(this)
            }
        }) {
            Text(text = "Start Service")
        }
        Spacer(modifier = Modifier.padding(12.dp))
        Button(onClick = {
            //Stop Service
            Toast.makeText(context, "Service Stop button clicked", Toast.LENGTH_SHORT).show()
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_SERVICE_STOP
                context.startService(this)
            }
        }) {
            Text(text = "Stop Service")

        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    SendMessageWhatsappTheme {
        Greeting("Android")
    }
}
