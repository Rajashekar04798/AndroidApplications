package com.example.shoppinglistapp.repository

import com.example.shoppinglistapp.data.ShoppingDao
import com.example.shoppinglistapp.data.ShoppingItem

class ShoppingRepository(private val dao: ShoppingDao) {
    fun getAllItems() = dao.getAllItems()
    suspend fun insert(item: ShoppingItem) = dao.insertItem(item)
    suspend fun delete(item: ShoppingItem) = dao.deleteItem(item)
    suspend fun update(item: ShoppingItem) = dao.updateItem(item)
}
