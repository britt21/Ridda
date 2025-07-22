package com.mobile.ridda.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.mobile.ridda.navigation.Destinations

@Composable
fun RideScreen(
    navController: NavController,
    viewModel: RideViewModel = hiltViewModel()
) {
    var pickupText by remember { mutableStateOf(TextFieldValue("")) }
    var destText by remember { mutableStateOf(TextFieldValue("")) }
    var showEstimate by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Map as background
        val lagos = LatLng(6.544425604781051, 3.374288248053532)
        val singaporeMarkerState = rememberMarkerState(position = lagos)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(lagos, 5f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = singaporeMarkerState,
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }

        // Overlay UI on top of the map
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            // Add any input fields and buttons you want on top of the map here
            OutlinedTextField(
                value = pickupText,
                onValueChange = { pickupText = it },
                label = { Text("Pickup (lat,lng)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = destText,
                onValueChange = { destText = it },
                label = { Text("Destination (lat,lng)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Button(
                onClick = {
                    val pickup = parseLatLng(pickupText.text)
                    val dest = parseLatLng(destText.text)
                    if (pickup != null && dest != null) {
                        viewModel.pickup = pickup
                        viewModel.destination = dest
                        viewModel.estimateFare()
                        showEstimate = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Get Fare Estimate")
            }

            when (uiState) {
                is RideUiState.FareEstimate -> {
                    val estimate = (uiState as RideUiState.FareEstimate).estimate
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Base Fare: $${estimate.base_fare}")
                            Text("Distance Fare: $${estimate.distance_fare}")
                            Text("Demand Multiplier: x${estimate.demand_multiplier}")
                            Text("Total Fare: $${estimate.total_fare}", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = { viewModel.requestRide() },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Request Ride")
                            }
                        }
                    }
                }

                is RideUiState.RideConfirmed -> {
                    val conf = (uiState as RideUiState.RideConfirmed).confirmation
                    AlertDialog(
                        onDismissRequest = { viewModel.resetState() },
                        title = { Text("Ride Confirmed!") },
                        text = {
                            Column {
                                Text("Driver: ${conf.driver.name}")
                                Text("Car: ${conf.driver.car}")
                                Text("Plate: ${conf.driver.plateNumber}")
                                Text("ETA: ${conf.estimated_arrival}")
                            }
                        },
                        confirmButton = {
                            Button(onClick = { viewModel.resetState() }) { Text("OK") }
                        }
                    )
                }

                is RideUiState.Error -> {
                    val msg = (uiState as RideUiState.Error).message
                    Text(msg, color = MaterialTheme.colorScheme.error)
                }
                is RideUiState.Loading -> {
                    CircularProgressIndicator()
                }
                else -> {}
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate(Destinations.HISTORY) },
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 35.dp)
            ) {
                Text("View Ride History")
            }
        }
    }
}

fun parseLatLng(text: String): LatLng? {
    val parts = text.split(",")
    return if (parts.size == 2) {
        val lat = parts[0].trim().toDoubleOrNull()
        val lng = parts[1].trim().toDoubleOrNull()
        if (lat != null && lng != null) LatLng(lat, lng) else null
    } else null
}

@Preview
@Composable
fun RideScreenPreview() {
    RideScreen(navController = rememberNavController(), viewModel = hiltViewModel())
}