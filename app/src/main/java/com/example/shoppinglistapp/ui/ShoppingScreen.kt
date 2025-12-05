package com.example.shoppinglistapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglistapp.data.ShoppingItem
import com.example.shoppinglistapp.viewmodel.ShoppingViewModel

@Composable
fun ShoppingScreen(viewModel: ShoppingViewModel) {

    val items by viewModel.items.collectAsState()

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Item Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Qty") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(100.dp)
            )
        }

        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            if (name.isNotEmpty() && quantity.isNotEmpty()) {
                viewModel.addItem(name, quantity.toInt())
                name = ""
                quantity = ""
            }
        }) {
            Text("Add Item")
        }

        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(items) { item ->
                ShoppingListItem(item = item, onToggle = { viewModel.toggleBought(item) }, onDelete = { viewModel.deleteItem(item) })
            }
        }
    }
}

@Composable
fun ShoppingListItem(item: ShoppingItem, onToggle: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Quantity: ${item.quantity}")
            }
            Row {
                Checkbox(checked = item.isBought, onCheckedChange = { onToggle() })
                Spacer(Modifier.width(8.dp))
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewShoppingListItem() {
    val sampleItem = ShoppingItem(
        id = 1,
        name = "Milk",
        quantity = 2,
        isBought = false
    )

    ShoppingListItem(
        item = sampleItem,
        onToggle = {},
        onDelete = {}
    )
}
