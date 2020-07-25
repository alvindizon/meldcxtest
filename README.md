## meldcxtest
An Android app written as part of MeldCX's hiring process.


## Libraries/frameworks used
- [Android Jetpack](https://developer.android.com/jetpack)  
	- [Room](https://developer.android.com/jetpack/androidx/releases/room)  
	- [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)  
		- ViewModel  
		- LiveData
	- [Databinding](https://developer.android.com/jetpack/androidx/releases/databinding)  
	- [Test](https://developer.android.com/jetpack/androidx/releases/test)  
		- Espresso  
		- Intents  
- [Dagger](https://github.com/google/dagger)  
- [RxJava2](https://github.com/ReactiveX/RxJava)  
	- [RxKotlin](https://github.com/ReactiveX/RxKotlin)
- [Glide](https://github.com/bumptech/glide)  
- [JUnit4](https://github.com/junit-team/junit4)  
- [Mockito](https://github.com/mockito/mockito)

## Specifications
### Main Activity:
1. Structure  
	- Input field: ​URL  
	- 3 buttons: ​GO​, ​CAPTURE​, ​HISTORY  
	- Webview component: ​WEBVIEW  
2. Once we enter any URL into the ​URL​ field and press the ​GO​ button - the content should
be loaded into the ​WEBVIEW
3. Once the content is loaded and we press the ​CAPTURE​ button - the ​WEBVIEW  
screenshot should be captured and saved
4. The captured screenshot should only contain the ​WEBVIEW​ contents, no other visual
elements should be visible.  
5. Once we press the ​HISTORY​ button - the app should switch to the ​Secondary Activity  

### Secondary Activity:
1. Structure  
	- Input field: ​SEARCH  
	- List component: ​HISTORY  
		- Image component: ​IMAGE  
		- Text label: ​URL  
		- Text label: ​DATETIME  
		- Button: ​DELETE  
2. The list should be scrollable and searchable by URL using the ​SEARCH​ field  
3. Once we press the ​DELETE ​button - the element should be deleted from the list  
4. Once we press the ​IMAGE ​or ​URL ​- the app should switch to the ​Main Activity
​
and load
the ​URL ​into the ​WEBVIEW ​automatically  
 
***NOTE:​ The ​WEBVIEW ​in the​ ​Main Activity should be replaced by the previously stored image until the web page is fully loaded***  

### Requirements:
- Kotlin  
- Unit Tests  
- Comments 

## Notes about the app
I think the app I made does the basic functionality laid out in the specifications. It's written 100% in Kotlin, and it was quite a challenge since I just started learning the language. The most difficult part was coding the unit tests -- I've also just started learning about unit testing, and I've tried my best to learn about them in a short timeframe by cloning apps found on Github, reading blog posts, reading and trying out code from Stackoverflow posts, etc.