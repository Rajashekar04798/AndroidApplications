package com.example.shoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.shoppinglistapp.data.ShoppingDatabase
import com.example.shoppinglistapp.repository.ShoppingRepository
import com.example.shoppinglistapp.ui.ShoppingScreen
import com.example.shoppinglistapp.viewmodel.ShoppingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            ShoppingDatabase::class.java,
            "shopping_db"
        ).build()

        val repository = ShoppingRepository(db.shoppingDao())
        val viewModel = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ShoppingViewModel(repository) as T
            }
        }.create(ShoppingViewModel::class.java)

        setContent {
            ShoppingScreen(viewModel)
        }
    }
}


/*@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListAppTheme {
        Greeting("Android")
    }
}*/