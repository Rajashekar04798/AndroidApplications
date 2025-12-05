package com.example.shoppinglistapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.data.ShoppingItem
import com.example.shoppinglistapp.repository.ShoppingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ShoppingRepository) : ViewModel() {

    private val _items = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val items: StateFlow<List<ShoppingItem>> = _items

    init {
        viewModelScope.launch {
            repository.getAllItems().collectLatest {
                _items.value = it
            }
        }
    }

    fun addItem(name: String, quantity: Int) {
        viewModelScope.launch {
            repository.insert(ShoppingItem(name = name, quantity = quantity))
        }
    }

    fun deleteItem(item: ShoppingItem) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }

    fun toggleBought(item: ShoppingItem) {
        viewModelScope.launch {
            repository.update(item.copy(isBought = !item.isBought))
        }
    }
}
