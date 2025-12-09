package com.example.expenseplus.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.expenseplus.data.expenseCategories
import com.example.expenseplus.data.paymentTypes
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onAddExpense: (Double, String, String?, LocalDate, String) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(expenseCategories.first()) }
    var selectedPaymentType by remember { mutableStateOf(paymentTypes.first()) }
    var remarks by remember { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }
    var paymentExpanded by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf(LocalDate.now()) }

    Card(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Add Expense", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { if (it.matches(Regex("^\\d*\\.?\\d*"))) amount = it },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // CATEGORY
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
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
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    value = selectedPaymentType,
                    onValueChange = {},
                    readOnly = true,
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
                label = { Text("Remarks (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val amt = amount.toDoubleOrNull()
                    if (amt != null) {
                        onAddExpense(
                            amt,
                            selectedCategory,
                            remarks.ifEmpty { null },
                            date,
                            selectedPaymentType
                        )
                    }
                },
                enabled = amount.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add")
            }
        }
    }
}
