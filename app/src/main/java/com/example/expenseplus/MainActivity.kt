package com.example.expenseplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expenseplus.ui.components.Expense
import com.example.expenseplus.ui.components.ExpenseCategoryDetailScreen
import com.example.expenseplus.ui.components.MainScreen
import com.example.expenseplus.ui.components.MonthlyExpenseGraphScreen
import com.example.expenseplus.ui.components.MonthlyTransactionsScreen
import com.example.expenseplus.ui.components.PaymentTypeSummaryScreen   // 👈 NEW IMPORT
import com.example.expenseplus.ui.theme.ExpensePlusTheme
import com.example.expenseplus.ui.viewmodel.ExpenseViewModel
import java.time.LocalDate

class MainActivity : ComponentActivity() {

    // ✅ ViewModel that talks to Room database
    private val expenseViewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ExpensePlusTheme {
                val navController = rememberNavController()

                // ✅ List of expenses now comes from ViewModel (which reads from Room)
                val expenses = expenseViewModel.expenses

                NavHost(
                    navController = navController,
                    startDestination = "main_screen"
                ) {
                    // 🏠 Main screen
                    composable("main_screen") {
                        MainScreen(
                            expenses = expenses,
                            onAddExpenseClick = { newExpense ->
                                // save to Room via ViewModel
                                expenseViewModel.addExpense(newExpense)
                            },
                            onDeleteExpense = { expenseToDelete ->
                                expenseViewModel.deleteExpense(expenseToDelete)
                            },
                            onEditExpense = { updatedExpense ->
                                expenseViewModel.editExpense(updatedExpense)
                            },
                            navController = navController
                        )
                    }

                    // 📂 Category detail screen
                    composable(
                        route = "category_detail_screen/{category}",
                        arguments = listOf(
                            navArgument("category") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val encodedCategory =
                            backStackEntry.arguments?.getString("category")
                        if (encodedCategory != null) {
                            val category =
                                java.net.URLDecoder.decode(encodedCategory, "UTF-8")
                            ExpenseCategoryDetailScreen(
                                category = category,
                                expenses = expenses,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }

                    // 📅 Monthly transactions screen
                    composable(
                        route = "monthly_transactions_screen/{year}/{month}",
                        arguments = listOf(
                            navArgument("year") { type = NavType.IntType },
                            navArgument("month") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val year = backStackEntry.arguments?.getInt("year")
                        val month = backStackEntry.arguments?.getInt("month")
                        if (year != null && month != null) {
                            MonthlyTransactionsScreen(
                                year = year,
                                month = month,
                                expenses = expenses,
                                navController = navController
                            )
                        }
                    }

                    // 💳 NEW: Payment type summary screen
                    composable("payment_type_summary_screen") {
                        PaymentTypeSummaryScreen(
                            expenses = expenses,
                            navController = navController
                        )
                    }

                    // 🔍 If later you add a route for MonthlyExpenseGraphScreen,
                    // you can define another composable here.
                }
            }
        }
    }
}

// 👇 Preview still uses an in-memory list (only for design preview, not Room)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExpensePlusTheme {
        val sampleExpenses = remember {
            mutableStateListOf(
                Expense(
                    amount = 10.0,
                    category = "Food",
                    remarks = "Dinner",
                    date = LocalDate.now(),
                    paymentType = "Cash"
                ),
                Expense(
                    amount = 20.0,
                    category = "Travel",
                    remarks = "Bus fare",
                    date = LocalDate.now(),
                    paymentType = "UPI"
                )
            )
        }

        MainScreen(
            expenses = sampleExpenses,
            onAddExpenseClick = { newExpense -> sampleExpenses.add(newExpense) },
            onDeleteExpense = { expense -> sampleExpenses.remove(expense) },
            onEditExpense = { updatedExpense ->
                val index = sampleExpenses.indexOfFirst { it.id == updatedExpense.id }
                if (index != -1) {
                    sampleExpenses[index] = updatedExpense
                }
            },
            navController = rememberNavController()
        )
    }
}
