# Koinz-task

android app that contains Recyclerview that presents the photos using Flickr APIs. When a user clicks on any photo it displays a full-screen page.

Features:
- pagination
- caching 
- Add an Advertising Banner image for every five photos
- show progress loading when loading more pages
- show a retry button if any page request fail


The app has the following packages:

data: It contains all the data accessing and manipulating components.

di: Dependency providing classes using Koin.

UI: View classes along with their corresponding ViewModel.

Architecture: MVVM
Libraries: Paging 3, Croutines, Koin, Data binding, Glide, and Room.


Testing:  Unit testing for inserting and delete photos from the database 


