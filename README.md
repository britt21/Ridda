# Ridda Ride-Hailing App (Demo)

A simple ride-hailing demo app built with Kotlin, Jetpack Compose, MVVM, Hilt, and Room. Features a beautiful Compose UI, fare estimation, simulated backend, and persistent ride history.

## Features
- **Modern UI**: Built with Jetpack Compose
- **Map Placeholder**: (Google Maps integration ready)
- **Pickup & Destination Input**: Enter coordinates
- **Fare Estimation**: Base fare, per km, demand surge, and traffic multiplier
- **Simulated Backend**: No real network calls
- **Ride Request**: Simulated driver assignment
- **Ride History**: Stored locally with Room
- **MVVM Architecture**: Clean separation of concerns
- **Dependency Injection**: Hilt
- **Navigation**: Jetpack Navigation Compose
- **Testing**: JUnit, MockK, Espresso

## Setup Instructions
1. **Clone the repository**
2. **Open in Android Studio** (Giraffe or newer recommended)
3. **Sync Gradle**
4. **Got to Manifest and paster Google map API Key in line 30**
5. **Run on emulator or device** (minSdk 24)

## How to Run the App
- Launch the app
- Enter pickup and destination as `lat,lng` (e.g., `6.5244,3.3792`)
- Tap "Get Fare Estimate"
- Review fare, then tap "Request Ride"
- See confirmation and assigned driver
- View ride history from the main screen

## How to Run Tests
- **Unit tests**: Run with
  ```
  ./gradlew test
  ```
- **UI tests**: Run with
  ```
  ./gradlew connectedAndroidTest
  ```

## Test Cases
| Test Case                  | Input                | Expected Output                  |
|---------------------------|----------------------|----------------------------------|
| Basic Fare Calculation    | 5km, normal demand   | $7.50 (Base + Distance)          |
| Surge Pricing (High Demand)| 8km, peak hour      | $14.00 (1.5x demand multiplier)  |
| Traffic Surge Pricing     | 6km, heavy traffic   | $12.00 (1.3x traffic multiplier) |
| Ride Request Confirmation | Valid pickup/dest    | Ride assigned with driver details|
| Database Ride History     | Store past rides     | Data persists locally            |

## Notes
- For a real map, integrate Google Maps Compose SDK and use Places API for address input.
- All backend logic is simulated for demo purposes. 