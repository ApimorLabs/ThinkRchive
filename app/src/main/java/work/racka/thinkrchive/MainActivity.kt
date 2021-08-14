package work.racka.thinkrchive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import work.racka.thinkrchive.ui.main.viewModel.ThinkpadListViewModel
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThinkRchiveTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Greeting(viewModel: ThinkpadListViewModel = hiltViewModel()) {
    val thinkpadList by  remember {
        derivedStateOf {
            viewModel.thinkpadList
        }
    }
    val loadError by remember {
        viewModel.loadError
    }
    val isLoading by remember {
        viewModel.isLoading
    }

    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(thinkpadList) {
            Timber.d("items called")
            Text(text = it.model, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "Release Data: ${it.releaseDate}")
            Text(text = "Processors: ${it.processors}")
            Text(text = "Platform: ${it.processorPlatforms}")
            Text(text = loadError)
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            AnimatedVisibility(isLoading) {
                CircularProgressIndicator()
            }
        }
    }

}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ThinkRchiveTheme {
        Greeting()
    }
}