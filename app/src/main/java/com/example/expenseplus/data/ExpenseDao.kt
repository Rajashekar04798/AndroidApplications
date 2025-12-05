package com.example.expenseplus.data

import androidx.room.*
import com.example.expenseplus.ui.components.Expense

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<Expense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)
}
