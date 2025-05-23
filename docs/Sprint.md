# Task assignments

## Alberto
### Sprint 1
- [x] T1.4. Create a README.md file including the project description and team information.
- [x] T1.5. Add a documentation folder.
- [x] T1.6. Include a design.md file explaining architectural choices.
- [x] T2.4. Include a CONTRIBUTING.md file outlining the team's branching strategy.
- [x] T2.5. Set up required remote and local branches.
- [x] T2.7. Create the first release using GitHub Actions.
- [x] T3.1. Develop the core screen layouts (e.g., Home with Menu and Navigation, Trip, Itinerary, User Preference).
- [x] T3.3. Document the data model in design.md.
- [x] T4.2. Generate a logo for you app
- [x] T4.3. Create a Splash Screen with the app logo.
- [x] T4.5. Add a Terms & Conditions screen.
- [x] T4.6. Ensure the app supports multiple languages.

### Sprint 2
- [x] Correction T2.4 of sprint 1. Fix CONTRIBUTING.md so that teams branching strategy reflect real use.
- [x] T1.2 Implement CRUD operations for itinerary items (addActivity, updateActivity, deleteActivity).
- [x] T1.4 Implement user settings.
- [x] T1.5 Implement multi-language with user changing.
- [x] T3.3 Simulate user interactions and log errors or unexpected behaviors.
- [x] T3.4 Update documentation with test results and fixes applied.

### Sprint 3
- [x] T2.2 Ensure UI updates when database changes.
- [x] T3.1 Write unit tests for DAOs and database interactions.
- [x] T3.2 Implement data validation (e.g., prevent duplicate trip names, check valid dates).
- [x] T3.3 Use Logcat to track database operations and errors.
- [x] T3.4 Update documentation with database schema and usage at design.md.

### Sprint 4
- [x] T1.1: Connect your app to Firebase.
- [x] T1.2: Design android screen (login form).
- [x] T1.3: Implement the actions to be logged in using Firebase (auth & password).
- [x] T1.4: Create an action in the app to allow the user log out.
- [x] T1.5: Use Logcat to track all operations and errors.


- [x] T2.1: Design android screen (register form).
    • See T3.1
- [x] T2.2: Implement the actions to be registered using Firebase (auth & password). The implementation must use the design pattern repository.
    • Implement email verification, see firebase documentation.
- [x] T2.3: Implement the actions and the view in order to create an option to recover the password. It depends on where you create this feature (new Screen)


- [x] T3.1 Persist the user information in the local DB (create user table).
    • Minimum information: login, username, birthdate (date field), address, country and phone number, accept receive emails.
    • Must check if the username is already in use.
- [ ] T3.2 Change the trip table structure to accept multiple users and show only the trips for the user logged in.
- [x] T3.4 Update documentation with database schema and usage at design.md.
- [x] T3.5 Persist the application access. It is needed to be persisted every time a user is logged in and logged out, persisting userid and datetime


- [x] T4.1 Improve trip/itinerary screens accordingly to professor feedback.
    • THE TRIPS MUST HAVE LOCATION, INIT DATE AND END DATE. Itineraries MUST have minimum Title, Description, DATE, START TIME and END TIME.
- [x] T4.2 Implement data validation, date and time pickers (e.g., prevent duplicate trip names, check valid dates for an itinerary).
- [x] T4.3 Update documentation with database schema and usage at design.md.

### Sprint 5
- [x] T1.1 Add Retrofit dependency and configure the HTTP client.  
- [x] T1.2 Create the necessary data models and API interfaces to access the hotel API implementing MVVM structure.  
- [x] T1.3 Create a repository layer to abstract the API usage.  
- [x] T1.4 Create a unit test to test and connection with /hotels/G16/availability.

- [x] T2.1 Create a screen to search for hotels in London, Paris, or Barcelona, with city, start and end dates.  
- [x] T2.2 Show hotel and room data returned by the API (typically 3 rooms per hotel).  
- [ ] T2.3 Allow users to book a room and save the reservation info (ID, room, hotel, price, etc) locally in the trip table or another table creating a new trip and save all reservation info.  
- [ ] T2.4 Display images of the hotel and rooms on the booking.

- [ ] T3.1 Allow the user to attach multiple images to a trip.  
- [ ] T3.2 Save images locally in the device database or storage.  
- [ ] T3.3 Display trip-specific galleries in the trip details screen

- [ ] T4.1 Create a screen to list all local reservations indicating the trip related.  
- [ ] T4.2 Add functionality to delete a reservation locally and via API (if required).  
- [ ] T4.3 Show the associated hotel and room images in the list, similar to the example seen in class (RemotePersistence.zip)  
- [ ] T4.4 Update the "My Trips" screen to indicate whether a trip includes a hotel reservation, and display the corresponding reservation details.

----------------------------------------------------------------------------------------------------
## Alex
### Sprint 1
- [x] T1.1. Define the Android version to ensure compatibility with most devices.
- [x] T1.2. Select and configure the Kotlin version.
- [x] T1.3. Initialize the project using Android Studio.
- [x] T2.1. Create a GitHub organization for the project.
- [x] T2.2. Initialize a Git repository (local).
- [x] T2.3. Add a LICENSE file.
- [x] T2.6. Push the initial code to GitHub.
- [x] T3.1. Develop the core screen layouts (e.g., Home with Menu and Navigation, Trip, Itinerary, User Preference).
- [x] T3.2. Implement core classes and its functions, annotating pending logic with @TODO.
- [x] T4.1. Create a Product Name.
- [x] T4.4. Implement an About Page (team info, version, summary).

### Sprint 2
- [x] Improvements T1.6 of sprint 1.
- [x] T1.1 Implement CRUD operations for trips (addTrip, editTrip, deleteTrip).
- [x] T1.3 Ensure proper data validation (e.g., dates must be in the future, required fields).
- [x] T1.4 Implement user settings.
- [x] T1.5 Implement multi-language with user changing.
- [x] T2.1 Structure how users will interact with the itinerary.
- [x] T2.2 Implement a basic UI flow for adding and modifying trip details.
- [x] T2.3 Ensure updates reflect dynamically in the main trip list.
- [x] T3.1 Implement basic input validation (e.g., empty fields, incorrect dates).
- [x] T3.2 Write unit tests for trip and itinerary CRUD operations.
- [x] T3.5 Add logs (to be seen in logcat) and commentaries applying good practices.

### Sprint 3
- [x] T1.1 Create Room Database class.
- [x] T1.2 Define Entities for Trip and ItineraryItem. Must contain at least one datetime field, text, and integer.
- [x] T1.3 Create Data Access Objects (DAOs) for database operations.
- [x] T1.4 Implement CRUD operations using DAO for trips and itineraries.
- [x] T2.1 Modify ViewModels to use Room Database instead of in-memory storage.