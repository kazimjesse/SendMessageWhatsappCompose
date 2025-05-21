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
                // on below line we are specifying background color for our application
                Surface(
                    // on below line we are specifying modifier and color for our app
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary
                ) {

                    // on the below line we are specifying
                    // the theme as the scaffold.
                    Scaffold(

                        // in scaffold we are specifying the top bar.
                        topBar = {

                            // inside top bar we are specifying background color.
                            TopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),

                                // along with that we are specifying
                                // title for our top bar.
                                title = {

                                    // in the top bar we are
                                    // specifying tile as a text
                                    Text(
                                        // on below line we are specifying
                                        // text to display in top app bar.
                                        text = "UTP\nAndroid - Ingeniería de Software 2025",

                                        // on below line we are specifying
                                        // modifier to fill max width.
                                        modifier = Modifier.fillMaxWidth(),

                                        // on below line we are specifying
                                        // text alignment.
                                        textAlign = TextAlign.Center,

                                        // on below line we are specifying
                                        // color for our text.
                                        color = Color.White
                                    )
                                })
                        }) {
                        // on below line we are calling connection information
                        // method to display UI
                        sendWhatsAppMessage(context = LocalContext.current)
                    }
                }
            }
        }
    }
}


// on below line creating a
// method to send whatsapp message.
@Composable
fun sendWhatsAppMessage(context: Context) {

    // on below line creating a variable
    // for phone number and message.
    val message = remember {
        mutableStateOf("")
    }
    val phoneNumber = remember {
        mutableStateOf("+507")
    }

    // on below line we are creating a column,
    Column(
        // on below line we are adding a modifier to it,
        modifier = Modifier
            .fillMaxSize()
            // on below line we are adding a padding.
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // on below line we are adding a text for heading.
        Text(
            // on below line we are specifying text
            text = "Enviar Mensaje a WhatsApp",
            // on below line we are
            // specifying text color,
            // font size and font weight
            color = greenColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        // on below line we are creating a
        // text field for our phone number.
        TextField(
            // on below line we are specifying
            // value for our email text field.
            value = phoneNumber.value,
            // on below line we are adding on value
            // change for text field.
            onValueChange = { phoneNumber.value = it },
            // on below line we are adding place holder as text
            // as "Enter your email"
            placeholder = { Text(text = "Ingrese el # de Contacto a Enviar") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )

        // on below line we are adding a spacer.
        Spacer(modifier = Modifier.height(10.dp))

        // on below line we are creating a text
        // field for our message number.
        TextField(
            // on below line we are specifying
            // value for our message text field.
            value = message.value,
            // on below line we are adding on value
            // change for text field.
            onValueChange = { message.value = it },
            // on below line we are adding place holder
            // as text as "Enter your email"
            placeholder = { Text(text = "Ingrese su mensaje") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )
        // on below line adding a spacer.
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                // on below line we are starting activity
                // to send the sms from whatsapp.
                context.startActivity(
                    // on below line we are opening the intent.
                    Intent(
                        // on below line we are calling
                        // uri to parse the data
                        Intent.ACTION_VIEW,
                        java.lang.String.format(
                            "https://api.whatsapp.com/send?phone=%s&text=%s",
                            phoneNumber.value,
                            message.value
                        ).toUri()
                    )
                )

            },
            // on below line adding
            // a modifier for our button.
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            // on below line adding a text for our button.
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
