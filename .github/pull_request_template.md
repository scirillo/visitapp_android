## Description
Created `MVVM `to handle api responses and show Rick and Morty characters list.
Using `databindig` to bid fragment and adapters views
Using `viewModels `to observe api responses.
Using `Hilt `as a dependency injection to test repository and vm classes

## Relevant code changes:
**Api package** is created to handle all api related logic.
  - `ApiResponseStatus `class has three sublclasses, Success, Loading, Error
  - `ApiService `has retrofit service
  - `Util `class just to parse the page query parameter from the url
  - `CharacterDTO `simple DTO class equals to the endpoint response
  - `CharacterDTOMapper `class to map DTO to Charter domain object
  - `CharacterApiResponse `class to match endoint response
  - `InfoApiResponse `class to match endoint response
   Commit [ref](https://github.com/devsavant/Android_networking_challenge/commit/440da9ba97c48148c5c46783435843e7616010b1)
   
**Domain package** is just created to hold the Character object.
-  `Constant `class just to hold url constants
-  `Charter `class is to be used by the UI, to avoid used the DTO class
-  `makeServiceCall` function is used from the Repository to get the character list
 Commit [ref](https://github.com/devsavant/Android_networking_challenge/commit/b768306e3134434a3abf3985f1d59a2cec479504)

**Characterlist package** is used to handle the repository, viewmodel, listfragment and adapter
-  `CharacterRepository ` is used to get the characters from the service using `makeServiceCall` function, and is saving the `next` and `prev `page numbers
-  `CharacterListViewModel ` ViewModel that uses `CharacterRepository ` and is observed by the view(CharacterListFragment )
-  `CharacterListFragment ` Acts as the view, is observing the characterList of the ViewModel, and displays the list items into the UI.
- `CharacterListAdapter ` Simple adapter that binds the item view
- Layouts files using databing are created for `CharacterListFragment `and `CharacterListAdapter `
Commit [ref](https://github.com/devsavant/Android_networking_challenge/commit/0a668f13e5bb7ed8191e7fabc3507d9b00a6a2af)

## Tests created or modified 
**Integration test classes**
  -  `CharacterListTest` Simple test to check UI fragment
  
**UnitTest classes**

 - `MainCoroutineRule` Rule to use for coroutines
 - `CharacterRepositoryTest ` Test class to check downloadCharacterList success and failure
 -  `CharacterListViewModelTest` Test class to check downloadCharacterList success and failure
Commit [ref](https://github.com/devsavant/Android_networking_challenge/pull/9/commits/62e2a6b98dacdf59148762f5a69a402b24621967)

## Documentation reference
 - [Testing coroutines and viewmodes](https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-survey#3)
  - [Using Hilt]( https://developer.android.com/training/dependency-injection/hilt-jetpack3)
 


## Screenshot or gif:


## Items pending / known issues
- Issue with coroutines and JUnit. **UnitTest are not working** because of this issue
```"Test worker @coroutine#1" java.lang.RuntimeException: Method getMainLooper in android.os.Looper not mocked.```
- Created test class for `CharacterListAdapter` based on `CharacterListFragment`
 
