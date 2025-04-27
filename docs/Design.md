## Data Model

### MVVM Architecture

The MVVM (Model-View-ViewModel) architecture is used to separate the development of the graphical user interface from the business logic (The domain layer). 
This helps in organizing the code and making it more maintainable and reusable.

### Components

#### Model
The Model represents the data and business logic of the application. It is responsible for managing the data, whether it is from a local database or a remote server.

**Entities:**
- **Trip**
  - `name`: `String` - Unique identifier for the trip.
  - `destination`: `String` - Destination of the trip.
  - `startDate`: `ZonedDateTime` - Start date of the trip.
  - `endDate`: `ZonedDateTime` - End date of the trip.
  - `itinerary`: `List<Activity>` - List of activities that form a Itinerary for the trip.
  - `images`: `List<Image>` - List of images for the trip.

- **Activity**
  - `id`: `Int` - Unique identifier for the activity.
  - `tripId`: `Int` - Identifier of the trip to which the item belongs.
  - `title`: `String` - Title of the activity.
  - `description`: `String` - Description of the activity.
  - `dateTime`: `ZonedDateTime` - Date and time when the activity starts.

- **Image**
  - `tripId`: `Int` - Identifier of the trip to which the image belongs.
  - `url`: `String` - URL of the image.
  - `description`: `String` - Description of the image.

- **User**
  - `id`: `Int` - Unique identifier for the user.
  - `login`: `String` - Email of the user.
  - `username`: `String` - Username of the user.
  - `birthdate`: `ZonedDateTime` - Birthdate of the user.
  - `address`: `String` - Address of the user.
  - `country`: `String` - Country of the user.
  - `phoneNumber`: `String` - Phone number of the user.
  - `acceptEmails`: `Boolean` - Whether the user accepts to receive emails.

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
- A `User` can have multiple `Activity`s.
- A `Trip` can have multiple `Activity`s.
- A `Trip` can have multiple `Image`s.


### Visualization of the Model
```mermaid
classDiagram
Trip --> Image
Trip --> Activity
User --> Trip
User --> Activity

class Trip {
    String name
    String destination
    ZonedDateTime startDate
    ZonedDateTime endDate
    List~Activity~ itinerary
    List~Image~ images
}

class Image {
    Int id
    String tripName
    String url
    String description
}

class Activity {
    Int id
    String tripName
    String title
    String description
    ZonedDateTime dateTime
}

class User {
    Int id
    String login
    String username
    ZonedDateTime birthdate
    String address
    String country
    String phoneNumber
    Boolean acceptEmails
}
```


  
## Database schema
### Mermaid diagram
```mermaid
classDiagram
class trip_table {
    name TEXT NOT NULL PRIMARY KEY
    destination TEXT NOT NULL
    start_date INTEGER NOT NULL
    end_date INTEGER NOT NULL
    cover_image_id INTEGER
}

class trip_images_table {
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL
    trip_name TEXT NOT NULL
    url TEXT NOT NULL
    description TEXT NOT NULL
}

class activity_table {
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL
    trip_name TEXT NOT NULL
    title TEXT NOT NULL
    description TEXT NOT NULL
    date_time INTEGER NOT NULL
}

  class user_table {
    id TEXT NOT NULL PRIMARY KEY
    login TEXT NOT NULL
    username TEXT NOT NULL
    birthdate INTEGER NOT NULL
    address TEXT NOT NULL
    phoneNumber TEXT NOT NULL
    acceptEmails INTEGER NOT NULL
  }

trip_table "1" --o "*" trip_images_table : has
trip_table "1" --o "*" activity_table : has
user_table "1" --o "*" trip_table : has
user_table "1" --o "*" activity_table : has
```


### SQL table creation

 - trip_table
```
CREATE TABLE IF NOT EXISTS trip_table (
    name TEXT NOT NULL PRIMARY KEY,
    destination TEXT NOT NULL,
    start_date INTEGER NOT NULL,
    end_date INTEGER NOT NULL,
    cover_image_id INTEGER,
    FOREIGN KEY(cover_image_id) REFERENCES trip_images_table(id) ON UPDATE NO ACTION ON DELETE SET NULL
);
```
 - trip_images_table
```
CREATE TABLE IF NOT EXISTS trip_images_table (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    trip_name TEXT NOT NULL,
    url TEXT NOT NULL,
    description TEXT NOT NULL,
    FOREIGN KEY(trip_name) REFERENCES trip_table(name) ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_trip_images_table_trip_name ON trip_images_table (trip_name);
```
- activity_table
```
CREATE TABLE IF NOT EXISTS activity_table (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    trip_name TEXT NOT NULL,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    date_time INTEGER NOT NULL,
    FOREIGN KEY(trip_name) REFERENCES trip_table(name) ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_activity_table_trip_name ON activity_table (trip_name);
```
- user_table
```
CREATE TABLE IF NOT EXISTS user_table (
    id TEXT NOT NULL PRIMARY KEY,
    login TEXT NOT NULL,
    username TEXT NOT NULL,
    birthdate INTEGER NOT NULL,
    address TEXT NOT NULL,
    country TEXT NOT NULL,
    phoneNumber TEXT NOT NULL,
    acceptEmails INTEGER NOT NULL
);
```






### Database usage

  - Create a Trip
```
INSERT INTO trip_table (name, destination, start_date, end_date, cover_image_id)
VALUES ('Summer Vacation', 'Hawaii', 1672531200, 1672617600, NULL);
```

  - Adding an Image to a Trip
```
INSERT INTO trip_images_table (trip_name, url, description)
VALUES ('Summer Vacation', 'https://example.com/image.jpg', 'Beach view');
```

  - Adding an Activity
```
INSERT INTO activity_table (trip_name, title, description, date_time)
VALUES ('Summer Vacation', 'Snorkeling', 'Snorkeling at the coral reef', 1672552800);
```

  - Retrieving Trips with Images and Activities
```
SELECT trip_table.*, trip_images_table.url AS cover_image, activity_table.title AS activity_title
FROM trip_table
LEFT JOIN trip_images_table ON trip_table.name = trip_images_table.trip_name
LEFT JOIN activity_table ON trip_table.name = activity_table.trip_name;
```

  - Deleting a Trip 
```
DELETE FROM trip_table WHERE name = 'Summer Vacation';
```
(This will also remove associated images and activities due to ON DELETE CASCADE)
