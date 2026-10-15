package com.example.wireup.ui.Screen.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.wireup.model.FlipNews
import com.example.wireup.model.MUser
import com.example.wireup.model.NewsData1
import com.example.wireup.model.SearchData
import com.example.wireup.model.Comment
import com.example.wireup.model.Tweet
import com.example.wireup.model.VideoPodcast
import com.example.wireup.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _tweets = MutableLiveData<List<Tweet>>()
    val tweets: LiveData<List<Tweet>> = _tweets

    private val _tweet = MutableLiveData<Tweet?>()
    val tweet: LiveData<Tweet?> = _tweet

    private val _deleteTweetStatus = MutableLiveData<Boolean>()
    val deleteTweetStatus: LiveData<Boolean> = _deleteTweetStatus

    private val _news1 = MutableLiveData<List<NewsData1>>()
    val news1: LiveData<List<NewsData1>> = _news1

    private val _video = MutableLiveData<List<VideoPodcast>>()
    val video: LiveData<List<VideoPodcast>> = _video

    private val _flipNews = MutableLiveData<List<FlipNews>>()
    val flipNews: LiveData<List<FlipNews>> = _flipNews

    private val _searchResults = MutableLiveData<List<SearchData>>()
    val searchResults: LiveData<List<SearchData>> = _searchResults

    private val _users = MutableLiveData<List<MUser>>()
    val users: LiveData<List<MUser>> = _users

    private val _isUniqueIdAvailable = MutableLiveData<Boolean>()
    val isUniqueIdAvailable: LiveData<Boolean> = _isUniqueIdAvailable

    private val _followerCount = MutableStateFlow(0)
    val followerCount: StateFlow<Int> = _followerCount

    private val _followerss = MutableStateFlow<List<String>>(emptyList())
    val followerss: StateFlow<List<String>> = _followerss

    private val _following = MutableStateFlow<List<String>>(emptyList())
    val following: StateFlow<List<String>> = _following

    private val _comment = MutableStateFlow<List<Comment>>(emptyList())
    val comment: StateFlow<List<Comment>> = _comment

    private val _likeCommentResult = MutableLiveData<Boolean>()
    val likeCommentResult: LiveData<Boolean> get() = _likeCommentResult
    private val _addCommentResult = MutableLiveData<Boolean>()
    val addCommentResult: LiveData<Boolean> get() = _addCommentResult

    private val _commentsMap = mutableMapOf<String, MutableStateFlow<List<Comment>>>()

    private suspend fun <T> LiveData<T>.awaitValue(): T = asFlow().first()

    fun likeComment(tweetId: String, commentId: String, userId: String) {
        viewModelScope.launch {
            _likeCommentResult.value = firestoreRepository.likeComment(tweetId, commentId, userId).awaitValue()
        }
    }
    fun getUserData(): LiveData<MUser> {
        val uuid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        return firestoreRepository.getUserData(uuid)
    }
    fun getUserData2(uuid: String): LiveData<MUser> {
        return firestoreRepository.getUserData(uuid)
    }

    fun getUser(userId: String): Flow<MUser> = flow {
        firestoreRepository.getUser(userId).collect { user ->
            emit(user)
        }
    }

    fun updateUserData(userData: MUser): LiveData<Boolean> {
        val uuid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        return firestoreRepository.updateUserData(uuid, userData)
    }

    fun uploadImageToFirebaseStorage(imageUri: Uri): LiveData<String> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        return firestoreRepository.uploadImageToFirebaseStorage(imageUri, userId)
    }

    fun updateUserProfileImage(imageUrl: String): LiveData<Boolean> {
        val uuid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val liveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val userData = getUserData().awaitValue()
            userData.profileImage = imageUrl
            liveData.value = updateUserData(userData).awaitValue()
        }
        return liveData
    }

    fun deleteTweet(tweetId: String, imageUri: String) {
        viewModelScope.launch {
            _deleteTweetStatus.value = firestoreRepository.deleteTweet(tweetId, imageUri).awaitValue()
        }
    }

    fun getComments(tweetId: String){
        viewModelScope.launch {
            _comment.value = firestoreRepository.getComments(tweetId).awaitValue()
        }
    }
    fun getComment(tweetId: String){
        val commentsFlow = _commentsMap.getOrPut(tweetId) { MutableStateFlow(emptyList()) }
        viewModelScope.launch {
            commentsFlow.value = firestoreRepository.getComments(tweetId).awaitValue()
        }
    }
    fun getCommentsFlow(tweetId: String): StateFlow<List<Comment>> {
        return _commentsMap[tweetId] ?: MutableStateFlow(emptyList())
    }


    fun fetchTweet(tweetId: String) {
        viewModelScope.launch {
            _tweet.value = firestoreRepository.fetchTweetFromFirestore(tweetId).awaitValue()
        }
    }

    fun loadVideoPodcasts() {
        viewModelScope.launch {
            _video.value = firestoreRepository.fetchYTlink().awaitValue()
        }
    }

    fun loadNews() {
        viewModelScope.launch {
            _news1.value = firestoreRepository.fetchNewsFromFirestore().awaitValue()
        }
    }

    fun getNewsData(id: String): LiveData<NewsData1> {
        return firestoreRepository.getNewsData(id)
    }

    fun loadFlipNews() {
        viewModelScope.launch {
            _flipNews.value = firestoreRepository.fetchFlipNews().awaitValue()
        }
    }


    fun loadTweets() {
        viewModelScope.launch {
            _tweets.value = firestoreRepository.fetchTweetsFromFirestore().awaitValue()
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            _users.value = firestoreRepository.fetchUsersFromFirestore().awaitValue()
        }
    }

    fun addTweet(tweetText: String, imageUri: Uri? = null) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        viewModelScope.launch {
            val imageUrl = imageUri?.let { uploadImageToStorage(it).awaitValue() }
            if (imageUrl != null && imageUrl.isEmpty()) return@launch
            if (firestoreRepository.addTweet(Tweet(userId = userId, description = tweetText, imageUrl = imageUrl)).awaitValue()) {
                loadTweets()
            }
        }
    }

    private fun uploadImageToStorage(imageUri: Uri): LiveData<String> {
        val liveData = MutableLiveData<String>()
        val storageRef = FirebaseStorage.getInstance().reference
        val userId = FirebaseAuth.getInstance().currentUser?.uid
            ?: return MutableLiveData("")
        val imageRef = storageRef.child("images/$userId/${UUID.randomUUID()}.jpg")
        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    liveData.value = uri.toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("ViewModel", "Error uploading image", exception)
                liveData.value = ""
            }
        return liveData
    }

    fun checkUniqueIdAvailability(uniqueId: String) {
        viewModelScope.launch {
            _isUniqueIdAvailable.value = firestoreRepository.checkUniqueIdAvailability(uniqueId).awaitValue()
        }
    }

    fun addFollowerToUser(userId: String, followerId: String, /*followerName: String, followerImage: String*/) {
        firestoreRepository.addFollowerToUser(userId, followerId) { success ->
            if (success) _followerCount.value += 1
        }
    }

    fun getFollowerCount(userId: String) {
        viewModelScope.launch {
            val count = firestoreRepository.getFollowerCount(userId)
            _followerCount.value = count
        }
    }

    fun getFollowersOfUser(userId: String) {
        viewModelScope.launch {
            val result = firestoreRepository.getFollowersOfUser(userId)
            _followerss.value = result
        }
    }

    fun fetchTweetsForCurrentUser(userId: String) {
        viewModelScope.launch {
            val followers = getFollowingsOfUser2(userId)
            if (followers.isNotEmpty()) {
                _tweets.value = firestoreRepository.fetchTweetsFromFollowedUsers(followers).awaitValue()
            }
        }
    }

    fun addFollowingToUser(userId: String, followingId: String, /*followerName: String, followerImage: String*/) {
        firestoreRepository.addFollowingToUser(userId, followingId) { success ->

        }
    }

    suspend fun getFollowingsOfUser2(userId: String): List<String> {
        return withContext(Dispatchers.IO) {
            firestoreRepository.getFollowingOfUser(userId)
        }
    }

    fun fetchTweetsofCurrentUser(userId: String) {
        viewModelScope.launch {
            _tweets.value = firestoreRepository.fetchTweetsofCurrentUser(userId).awaitValue()
        }
    }

    fun searchNews(query: String) {
        Log.d("UserViewModel", "Searching for news with query: $query")
        viewModelScope.launch {
            val results = firestoreRepository.searchNews(query)
            Log.d("UserViewModel", "Search results: $results")
            _searchResults.value = results
        }
    }

    fun verifyUserID(uuid: String, callback: () -> Unit) {
        viewModelScope.launch {
            firestoreRepository.VerifyUserID(uuid)
        }
        callback()
    }

    fun addComment(tweetId: String, comment: Comment) {
        viewModelScope.launch {
            _addCommentResult.value = firestoreRepository.addComment(tweetId, comment).awaitValue()
        }
    }

    fun checkIfTweetHasComments(tweetId: String): LiveData<Boolean> {
        return firestoreRepository.hasComments(tweetId)
    }


}

