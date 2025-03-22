## Data Model

### MVVM Architecture

The MVVM (Model-View-ViewModel) architecture is used to separate the development of the graphical user interface from the business logic (The domain layer). 
This helps in organizing the code and making it more maintainable and reusable.

### Components

#### Model
The Model represents the data and business logic of the application. It is responsible for managing the data, whether it is from a local database or a remote server.

**Entities:**
- **User**
  - `id`: `Int` - Unique identifier for the user.
  - `name`: `String` - Name of the user.
  - `email`: `String` - Email address of the user.
  - `language`: `String` - Preferred language of the user.
  - `notificationsEnabled`: `Boolean` - Whether notifications are enabled for the user.

- **Trip**
  - `id`: `Int` - Unique identifier for the trip.
  - `destination`: `String` - Destination of the trip.
  - `startDate`: `ZonedDateTime` - Start date of the trip.
  - `endDate`: `ZonedDateTime` - End date of the trip.
  - `itinerary`: `List<Activity>` - List of activities that form a Itinerary for the trip. In the relation with ROOMdb
  - `images`: `List<Image>` - List of images for the trip. Add when ROOMdb

- **Activity**
  - `id`: `Int` - Unique identifier for the activity.
  - `tripId`: `Int` - Identifier of the trip to which the item belongs.
  - `title`: `String` - Title of the activity.
  - `description`: `String` - Description of the activity.
  - `dateTime`: `ZonedDateTime` - Date and time when the activity starts.

- **Image** (when ROOMdb)
  - `id`: `Int` - Unique identifier for the image.
  - `tripId`: `Int` - Identifier of the trip to which the image belongs.
  - `url`: `String` - URL of the image.
  - `description`: `String` - Description of the image.

##### Mermaid visualization of the Model
![img.png](mermaid.png)


#### View
The View is responsible for displaying the data to the user and handling user interactions.
It is the UI of the application (all the composable functions).


#### ViewModel
The ViewModel acts as a bridge between the Model and the View. It holds the data required by the View and handles the logic to update the Model based on user interactions.

**Responsibilities:**
- Fetching data from the Model and exposing it to the View.
- Handling user actions and updating the Model accordingly.
- Managing the state of the UI components.

### Relationships
- A `User` can have multiple `Trip`s.
- A `Trip` can have multiple `Activity`s.
- A `Trip` can have multiple `Image`s.


```mermaid
classDiagram
User --> Trip
Trip --> Activity
Trip --> Image
Trip --> AIRecommendations

class User {
    Int id
    String name
    String email
    String language
    Boolean notificationsEnabled
}

class Trip {
    Int id
    String destination
    Date startDate
    Date endDate
    List~ItineraryItem~ itinerary
    List~Image~ images
}

class Activity {
    Int id
    Int tripId
    String title
    String description
    ZonedDateTime dateTime
}

class Image {
    Int id
    Int tripId
    String url
    String description
}