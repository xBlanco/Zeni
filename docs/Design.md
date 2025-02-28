## Data Model

### MVVM Architecture

The MVVM (Model-View-ViewModel) architecture is used to separate the development of the graphical user interface from the business logic or back-end logic. This helps in organizing the code and making it more maintainable.

### Components

#### Model
The Model represents the data and business logic of the application. It is responsible for managing the data, whether it is from a local database or a remote server.

**Entities:**
- **User**
    - `id`: `Int` - Unique identifier for the user.
    - `name`: `String` - Name of the user.
    - `email`: `String` - Email address of the user.
    - `preferences`: `UserPreferences` - User-specific preferences.

- **Trip**
    - `id`: `Int` - Unique identifier for the trip.
    - `userId`: `Int` - Identifier of the user who created the trip.
    - `destination`: `String` - Destination of the trip.
    - `startDate`: `Date` - Start date of the trip.
    - `endDate`: `Date` - End date of the trip.
    - `itinerary`: `List<ItineraryItem>` - List of itinerary items for the trip.

- **ItineraryItem**
    - `id`: `Int` - Unique identifier for the itinerary item.
    - `tripId`: `Int` - Identifier of the trip to which the item belongs.
    - `title`: `String` - Title of the itinerary item.
    - `description`: `String` - Description of the itinerary item.
    - `dateTime`: `DateTime` - Date and time of the itinerary item.

- **UserPreferences**
    - `language`: `String` - Preferred language of the user.
    - `notificationsEnabled`: `Boolean` - Whether notifications are enabled for the user.

#### View
The View represents the UI components that the user interacts with. It displays the data and sends user actions to the ViewModel.

**Examples:**
- Home Screen
- Trip Planning Screen
- Itinerary Screen
- User Preferences Screen

#### ViewModel
The ViewModel acts as a bridge between the Model and the View. It holds the data required by the View and handles the logic to update the Model based on user interactions.

**Responsibilities:**
- Fetching data from the Model and exposing it to the View.
- Handling user actions and updating the Model accordingly.
- Managing the state of the UI components.

### Relationships
- A `User` can have multiple `Trip`s.
- A `Trip` can have multiple `ItineraryItem`s.
- A `User` has one `UserPreferences`.