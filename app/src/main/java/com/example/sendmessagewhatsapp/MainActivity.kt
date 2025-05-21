package com.example.sendmessagewhatsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import androidx.core.content.ContextCompat.startActivity
import com.example.sendmessagewhatsapp.ui.theme.SendMessageWhatsappTheme
import androidx.core.net.toUri
import com.example.sendmessagewhatsapp.ui.theme.greenColor

class MainActivity : ComponentActivity() {

    var contactName: String = ""
    var contactNumber: String = ""

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

                        sendWhatsAppMessage(context = LocalContext.current)

                    }
                }
            }
        }
    }
}


@Composable
fun sendWhatsAppMessage(context: Context) {

    val message = remember {
        mutableStateOf("")
    }
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

            text = "Enviar Mensaje a WhatsApp",
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

        Spacer(modifier = Modifier.height(10.dp))

        TextField(

            value = message.value,

            onValueChange = { message.value = it },

            placeholder = { Text(text = "Ingrese su mensaje") },

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

            singleLine = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        java.lang.String.format(
                            "https://api.whatsapp.com/send?phone=%s&text=%s",
                            phoneNumber.value,
                            message.value
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
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SendMessageWhatsappTheme {
        Greeting("Android")
    }
}
