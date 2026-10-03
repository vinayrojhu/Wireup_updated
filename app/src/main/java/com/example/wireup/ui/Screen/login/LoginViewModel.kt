package com.example.wireup.ui.Screen.login

import android.content.Intent
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wireup.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

     val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error



    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) = viewModelScope.launch {
        _loading.value = true
        _error.value = null
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Log.d("FB", "signInWithEmailAndPassword: Yayayay! ${result.toString()}")
            home()
        } catch (ex: Exception) {
            Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
            _error.value = ex.message ?: "Unable to sign in. Please try again."
        } finally {
            _loading.value = false
        }
    }
    fun createUserWithEmailAndPassword(email: String, password: String, name: String, home: () -> Unit) = viewModelScope.launch {
        _loading.value = true
        _error.value = null
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val userId = auth.currentUser?.uid
            if (userId == null) {
                _error.value = "Unable to create an account. Please try again."
                return@launch
            }
                val profile_image = ""
                val uniqueId = "Create UID"
                val followers = mutableListOf<String>()
                val following = mutableListOf<String>()
                val user = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "userID" to userId,
                    "profile_image" to profile_image,
                    "uniqueId" to uniqueId,
                    "followers" to followers,
                    "following" to following
                )
            FirebaseFirestore.getInstance().collection("users").document(userId).set(user).await()
            home()
        } catch (ex: Exception) {
            Log.e("FB", "createUserWithEmailAndPassword failed", ex)
            _error.value = ex.message ?: "Unable to create an account. Please try again."
        } finally {
            _loading.value = false
        }
    }



    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    fun getGoogleSignInIntent(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso).signInIntent
    }

    fun signInWithGoogle(idToken: String, displayName: String?, email: String?, photoUrl: String?, home: () -> Unit) = viewModelScope.launch {
        _loading.value = true
        _error.value = null
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            val user = authResult.user
            if (user != null) {
                val userId = user.uid
                val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)
                userRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document.exists()) {
                            // User already exists, navigate to Home screen
                            home()
                        } else {
                            // Create new user
                            val followers = mutableListOf<String>()
                            val following = mutableListOf<String>()
                            val newUser = hashMapOf(
                                "name" to displayName,
                                "email" to email,
                                "userID" to userId,
                                "profile_image" to photoUrl,
                                "uniqueId" to "Create UID",
                                "followers" to followers,
                                "following" to following
                            )
                            userRef.set(newUser).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // User data stored successfully, navigate to Home screen
                                    home()
                                } else {
                                    Log.e("User  Data Storage", "Error storing user data", task.exception)
                                }
                            }
                        }
                    } else {
                        Log.e("User  Data Retrieval", "Error retrieving user data", task.exception)
                    }
                }
            } else {
                Log.e("Google Sign In", "Error signing in with Google")
            }
        } catch (e: Exception) {
            Log.e("Google Sign In", "Error signing in with Google", e)
            _error.value = e.message ?: "Google sign-in failed. Please try again."
        } finally {
            _loading.value = false
        }
    }

    fun reportGoogleSignInError(message: String) {
        _error.value = message
    }


    fun signOut(home: () -> Unit) = viewModelScope.launch {
        try {
            googleSignInClient.signOut().await()
            auth.signOut()
            home()
        } catch (e: Exception) {
            Log.e("Sign Out", "Error signing out", e)
        }
    }



}
