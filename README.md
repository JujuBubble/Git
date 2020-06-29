# Git

Welcome to the Git app wiki!

This is a sample app that checks the top repositories on Github and shows title, author’s image and description of repository as a staggered column.

Top repos is loosely defined as repos with the most stars. For simplicity, repos with stars greater than 1600 will be queried. At the moment of this wiki's writing, the query is expected to return 15k+ results.

## Running the project

1. Requirements
- Android Studio 4.0 (Android SDK API level 29)

![API 29](https://github.com/JujuBubble/Git/blob/master/wiki/images/1-AndroidStudio.png)
	
2. Clone this project: https://github.com/JujuBubble/Git
    
3. Go to `Git > Settings > Developer Settings > Personal access tokens`

4. Generate a new token and copy it

![Git Token](https://github.com/JujuBubble/Git/blob/master/wiki/images/4-Token.png)
    
5. Open the project, open `keys.properties` and paste the git token to `GIT_TOKEN_DEVELOPMENT`/`GIT_TOKEN_RELEASE`

![Git Token](https://github.com/JujuBubble/Git/blob/master/wiki/images/5-GitToken.png)
    
6. Initially, some classes like `RepoListQuery` and `DaggerBaseComponent`”` will show an error, hit Build so the annotation processor can generate these classes

![Build](https://github.com/JujuBubble/Git/blob/master/wiki/images/6-Build.png)
    
7. Run

![Build Success](https://github.com/JujuBubble/Git/blob/master/wiki/images/7-BuildSuccess.png)


## Builds

[Download the APK here](https://github.com/JujuBubble/Git/raw/master/wiki/builds/Git.apk.zip)

## Design Approach

- Use MVVM
- Use Dagger for dependency injection
- Use Jetpack Paging Library
  - ViewModelFactory and ViewModel lifecycle handling
  - DataSource + Factory backed by ApolloClient and RxJava. This will provide loading with pagination and allow caching of the next page request to retry if the request fails (e.g. due to network connectivity)

### Dependency Graph

![Dependency Graph](https://github.com/JujuBubble/Git/blob/master/wiki/images/Design_Dep_Graph.png)

### UML

![UML](https://github.com/JujuBubble/Git/blob/master/wiki/images/Design_UML.png)

## Github GraphQL

The app utilizes Github's GraphQL API: https://developer.github.com/v4/

The query below is used with the following values: 
- query: `stars:>1600`
- first: `20` 
- after: cursor of the last item of the current page, null on initial load

```
   search(type: REPOSITORY, query: $query, first: $first, after: $cursor) {
     edges {
       cursor
       node {
         ... on Repository {
           name
           description
           owner {
             avatarUrl
           }
         }
       }
     }
   }
```
A sample call will be:
`   search(type: REPOSITORY, query: "stars:>1600", first: 20, after: "Yd9812fd-")`

## Gradle Dependencies

- [ApolloClient](https://www.apollographql.com/docs/android/) for generating Java/Kotlin models from GraphQL queries
- [Picasso](https://square.github.io/picasso/) for image downloading and caching
- [Dagger](https://developer.android.com/training/dependency-injection/dagger-android) for dependency injection
- [Shimmer](https://github.com/facebook/shimmer-android) for animated loading screen
- [RxJava](https://github.com/ReactiveX/RxAndroid) for async ops

## Screenshots

#### Loading and Swipe Refresh
![Loading](https://github.com/JujuBubble/Git/blob/master/wiki/images/Screen_Loading.png) ![Swipe to Refresh](https://github.com/JujuBubble/Git/blob/master/wiki/images/Screen_Swipe_Refresh.png)

#### List
![List](https://github.com/JujuBubble/Git/blob/master/wiki/images/Screen_List.png)

#### Error (Refresh | Next Page)
![Error Refresh](https://github.com/JujuBubble/Git/blob/master/wiki/images/Screen_Error.png) ![Next Page Error](https://github.com/JujuBubble/Git/blob/master/wiki/images/Screen_Page_Error.png)
