package com.dragos.ageguessing.android

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dragos.ageguessing.android.confetti.ConfettiView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val speechLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {

                val res: ArrayList<String> =
                    result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                viewModel.getAge(res[0])
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState = viewModel.uiState.collectAsState()

            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.padding(4.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Button(onClick = {
                            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

                            intent.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                            )
                            intent.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE,
                                Locale.getDefault()
                            )
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
                            speechLauncher.launch(intent)
                        }) {
                            Text(text = "Hit me to say something")
                        }

                        if (uiState.value.showError) {
                            Text(text = "uhh ohh, we couldn't detect your age")
                        }
                    }

                    uiState.value.age?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.padding(4.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Your calculated age is",
                                    style = TextStyle(
                                        fontSize = 14.sp
                                    ),
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = it.toString(),
                                    style = TextStyle(
                                        fontSize = 30.sp
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }

                            ConfettiView(
                                imageList = getConfettiImageList(this@MainActivity),
                                colorList = listOf(
                                    Color.Red,
                                    Color.Blue,
                                    Color.Yellow,
                                    Color.Green
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
