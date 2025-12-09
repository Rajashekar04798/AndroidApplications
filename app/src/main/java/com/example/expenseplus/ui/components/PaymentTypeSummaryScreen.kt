package com.example.expenseplus.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PaymentTypeSummaryScreen(
    expenses: List<Expense>,
    navController: NavController
) {
    val cashTotal = expenses.filter { it.paymentType == "Cash" }.sumOf { it.amount }
    val upiTotal = expenses.filter { it.paymentType == "UPI" }.sumOf { it.amount }
    val cardTotal = expenses.filter { it.paymentType == "Card" }.sumOf { it.amount }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Payment Type Summary",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        SummaryCard(label = "Cash", amount = cashTotal)
        Spacer(modifier = Modifier.height(12.dp))

        SummaryCard(label = "UPI", amount = upiTotal)
        Spacer(modifier = Modifier.height(12.dp))

        SummaryCard(label = "Card", amount = cardTotal)

        Spacer(modifier = Modifier.height(24.dp))

        // Optional back button (system back also works)
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}

@Composable
private fun SummaryCard(label: String, amount: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "₹%.2f".format(amount),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


