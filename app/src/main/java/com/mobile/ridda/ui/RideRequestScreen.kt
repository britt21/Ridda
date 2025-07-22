package com.mobile.ridda.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideHistoryScreen(
    navController: NavController,
    viewModel: RideViewModel = hiltViewModel()
) {
    val rides = viewModel.rideHistory.collectAsState().value
    LaunchedEffect(Unit) { viewModel.loadRideHistory() }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ride History") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (rides.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No rides yet.")
                }
            } else {
                LazyColumn {
                    items(rides) { ride ->
                        Card(Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)) {
                            Column(Modifier.padding(12.dp)) {
                                Text("From: ${ride.pickupLat},${ride.pickupLng}")
                                Text("To: ${ride.destinationLat},${ride.destinationLng}")
                                Text("Fare: $${ride.fare}")
                                Text("Driver: ${ride.driverName ?: "-"}")
                                Text("Car: ${ride.car ?: "-"}")
                                Text("Plate: ${ride.plateNumber ?: "-"}")
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                Text("Back to Main")
            }
        }
    }
}