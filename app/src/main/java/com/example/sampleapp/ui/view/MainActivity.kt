package com.example.sampleapp.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.sampleapp.models.Employee
import com.example.sampleapp.ui.theme.HiltComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalStdlibApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiltComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(viewModel = viewModel)
                    viewModel.loadUser()
                }
            }
        }
    }
}

@Composable
fun MainView(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()
    when (state) {
        State.START -> {

        }

        State.LOADING -> {
            Loader()
        }

        is State.SUCCESS -> {
            val employees = (state as State.SUCCESS).users
            ShowEmployees(list = employees)
        }

        is State.FAILURE -> {
            ErrorContent(content = (state as State.FAILURE).message)
        }
    }
}

@Preview
@Composable
fun Loader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Loading", style = TextStyle(color = Color.Black))
    }
}

@Composable
fun ErrorContent(content: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = content, color = Color.Black)
    }
}

@Composable
fun ShowEmployees(list: List<Employee>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        items(list) { it ->
            MaterialTheme {
                Row(modifier = Modifier
                    .background(color = Color.White)
                    .padding(all = 16.dp)
                    .clickable {
                        Log.d("Tag", "Item:$it")
                    }) {

                    Image(
                        rememberAsyncImagePainter(it.avatar),
                        contentDescription = it.first_name,
                        modifier = Modifier
                            .height(70.dp)
                            .width(70.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Column {
                        Text(
                            text = it.first_name,
                            style = TextStyle(
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 2.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = it.email,
                                style = TextStyle(fontSize = 15.sp, color = Color.Black),
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .weight(1f)
                                    .padding(start = 8.dp, top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}