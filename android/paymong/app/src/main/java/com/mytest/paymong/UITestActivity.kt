package com.mytest.paymong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class UITestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main" ){
                composable("main"){
                    MainScreen(navController = navController)
                }
                composable("second"){
                    SecondScreen(navController = navController)
                }
                composable("third/{value}"){backStackEntry->
                    ThirdScreen(navController = navController,value = backStackEntry.arguments?.getString("value")?:"")
                }

            }
            //HelloScreen()
        }
    }
}

@Composable
fun MainScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "메인화면")
        Button(onClick = {
            navController.navigate("second")
        }) {
            Text(text = "세컨화면가기")
        }

        Button(onClick = {
            val value="555"
            navController.navigate("third/$value")
        }) {
            Text(text = "써드화면가기")
        }
    }
}

@Composable
fun SecondScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "두번째화면")
        Button(onClick = {
            navController.navigateUp()
        }) {
            Text(text = "뒤로가기")
        }
    }
}

@Composable
fun ThirdScreen(navController: NavController, value:String){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "세번째화면 +" + value)
        Button(onClick = {
            navController.navigateUp()
        }) {
            Text(text = "뒤로가기")
        }
    }
}

@Composable
fun HelloScreen() {
    var (name,setName) = rememberSaveable { mutableStateOf("") }

    HelloContent(name = name, setName= setName)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelloContent(name : String, setName :(String)->Unit ) {
    Column(modifier = Modifier.padding(16.dp)) {

        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = setName,
            label = { Text("Name") }
        )
    }
}