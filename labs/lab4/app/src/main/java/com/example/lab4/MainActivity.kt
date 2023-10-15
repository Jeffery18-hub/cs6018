package com.example.lab4

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab4.ui.theme.Lab4Theme
import java.util.Date


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm: JokeViewModel by viewModels{ JokeViewModelFactory((application as JokeApplication).jokeRepo)}

        setContent {
            Lab4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val currentJoke by vm.currJoke.observeAsState()
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ButtonArea{
                            vm.fetchJoke()
                        }
                        DisplayJoke(data = currentJoke)

                        Spacer(modifier = Modifier.padding(32.dp))

                        Text(text = "Previous Jokes")

                        Spacer(modifier = Modifier.padding(16.dp))

                        val allJokes by vm.allJokes.observeAsState()

                        Box(modifier = Modifier.fillMaxSize()){
                            LazyColumn{
                                for (data in allJokes?: listOf()){
                                    item {
                                        DisplayJoke(data = data)
                                    }
                                }
                            }
                        }

                    }

                }
            }
        }
    }
}

@Composable
fun ButtonArea(onClick: ()-> Unit){
//    var jokes by remember { mutableStateOf(listOf<String>()) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // // Center children horizontally.
            verticalArrangement = Arrangement.spacedBy(8.dp) // // Space between children.
        ) {
            Image(
                painter = painterResource(id = R.drawable.joke),
                contentDescription = "Joke png",
                modifier = Modifier.size(150.dp)
            )
            Button(
                onClick = {
                    onClick()
                }) {
                Text(text = "Click to Fetch a joke!")
            }
        }
}

@Composable
fun DisplayJoke(data: JokeData?, modifier: Modifier = Modifier){
    val scrollState = rememberScrollState()

    Surface(color = MaterialTheme.colorScheme.surface) {
        Text(
            text = if (data != null)  "${data.timestamp} : ${data.content}" else
            "No Joke Back!",
            modifier = Modifier.padding(10.dp)
        )
    }
}


@Preview
@Composable
fun ButtonAreaPreview(){
    ButtonArea {}
}

@Preview
@Composable
fun DisplayJokePreview(){
    DisplayJoke(
        data = JokeData(Date(), "this is a joke")
    )
}



