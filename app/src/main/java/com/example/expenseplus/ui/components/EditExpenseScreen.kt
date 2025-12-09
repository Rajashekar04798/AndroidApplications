package com.example.expenseplus.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.expenseplus.data.expenseCategories
import com.example.expenseplus.data.paymentTypes
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenseScreen(
    expense: Expense,
    onEditExpense: (Expense) -> Unit,
    onCancelEdit: () -> Unit
) {
    var amount by remember(expense.id) { mutableStateOf(expense.amount.toString()) }
    var selectedCategory by remember(expense.id) { mutableStateOf(expense.category) }
    var remarks by remember(expense.id) { mutableStateOf(expense.remarks ?: "") }

    // 👇 NEW
    var selectedPaymentType by remember(expense.id) { mutableStateOf(expense.paymentType) }

    var categoryExpanded by remember { mutableStateOf(false) }
    var paymentExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Edit Expense", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { if (it.matches(Regex("^\\d*\\.?\\d*"))) amount = it },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Category
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded }
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    value = selectedCategory,
                    readOnly = true,
                    onValueChange = {},
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(categoryExpanded) }
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    expenseCategories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                selectedCategory = cat
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 👇 NEW Payment Type
            ExposedDropdownMenuBox(
                expanded = paymentExpanded,
                onExpandedChange = { paymentExpanded = !paymentExpanded }
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    value = selectedPaymentType,
                    readOnly = true,
                    onValueChange = {},
                    label = { Text("Payment Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(paymentExpanded) }
                )
                ExposedDropdownMenu(
                    expanded = paymentExpanded,
                    onDismissRequest = { paymentExpanded = false }
                ) {
                    paymentTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                selectedPaymentType = type
                                paymentExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = remarks,
                onValueChange = { remarks = it },
                label = { Text("Remarks") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val amt = amount.toDoubleOrNull()
                    if (amt != null) {
                        val updatedExpense = expense.copy(
                            amount = amt,
                            category = selectedCategory,
                            remarks = remarks.ifEmpty { null },
                            paymentType = selectedPaymentType
                        )
                        onEditExpense(updatedExpense)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = amount.isNotEmpty()
            ) {
                Text("Save Changes")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onCancelEdit,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors()
            ) {
                Text("Cancel")
            }
        }
    }
}
