package com.example.layoutsinjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.layoutsinjetpackcompose.ui.theme.LayoutsInJetpackComposeTheme

class ScaffoldActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsInJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun LayoutCodelab(){
    Scaffold(
        //Add TopBar
    topBar = {
//        Text(text = "Jetpact Compose",
//        style = MaterialTheme.typography.h3)
        TopAppBar(
            title = {
                Text(text = "Compose!")
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }
            }
        )
    }
    ) { innerPadding ->
    /**    Text(text = "Hi There !", modifier = Modifier.padding(innerPadding)) */
       
      /**  
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Hi There !")
            Text(text = "Thanks for going through the Layouts codelabs")
        }
        */
        
        //To Make Code Reusable dan testable, use this
        
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp)
        )
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    /**
    Column(modifier = modifier
        .padding(8.dp)) {
        Text(text = "hi there !")
        Text(text = "tested of jetpact compose android kotlin")
    }
    */

    //-----------------------Custom Layout
    MyOwnColumn(modifier.padding(8.dp)) {
        Text("MyOwnColumn")
        Text("place items")
        Text("vertically.")
        Text("we've done it by hand!")
    }
}

@Composable
fun SimpleList(){
    // Save the Scrolling position with this state that can also be used to programmatically scroll the list
    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)) {
        repeat(100){
            Text("Item #$it")
        }
    }
}

@Composable
fun LazyList(){
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState){
        items(100){
            Text("ITEM #$it")
        }
    }
}

@Preview
@Composable
fun LazyListPreview(){
    LazyList()
}

@Preview
@Composable
fun SimpleListPreview(){
    SimpleList()
}

@Preview
@Composable
fun LayoutsCodelabPreview(){
    LayoutsInJetpackComposeTheme {
        LayoutCodelab()
    }
}