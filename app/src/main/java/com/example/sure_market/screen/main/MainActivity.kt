package com.example.sure_market.screen.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.sure_market.data.UserSharedPreference
import com.example.sure_market.network.PostRepository
import com.example.sure_market.screen.detail.DetailActivity
import com.example.sure_market.screen.intro.LoginActivity
import com.example.sure_market.screen.post.PostActivity
import com.example.sure_market.ui.theme.SureMarketTheme
import com.example.sure_market.viewmodel.MainViewModel
import com.example.sure_market.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var prefs: UserSharedPreference

    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory


//    private val activityResultLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            Log.d("DaeYoung", "activity SUCCESS")
//            if (result.resultCode == RESULT_OK) {
//                val userid = result.data?.getLongExtra("userId", 0)
//                userid?.let {
//                    prefs.setUserPrefs(userId = it)
//                }
//            }
////             else {
////                val userId: Int = result.data?.getIntExtra("userId", 0) ?: 0
//////                prefs.setUserPrefs(userId = userId)
////                prefs.setUserPrefs(userId = 1L)
////            }
//
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModelFactory = MainViewModelFactory(PostRepository())
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        Log.d("daeYoung", "성공?")


        val str = "userToken"
        prefs = UserSharedPreference(this)

        if (prefs.getUserPref(str) == "") {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        super.onCreate(savedInstanceState)
        setContent {

            SureMarketTheme {
                val navController = rememberNavController()
                MainScreen(
                    viewModel = viewModel,
                    navController = navController,
                    onMovePost = { onMovePost() },
                    onMoveDetail = onMoveDetail,
                    clearUser = { clearUser() }
                )
            }
        }
    }

    private fun onMovePost() {
        startActivity(Intent(this, PostActivity::class.java))
    }

    private val onMoveDetail:(Long) -> Unit =  { postId ->
        startActivity(Intent(this, DetailActivity::class.java).also {
            it.putExtra("postId", postId)
        })
    }

    private fun clearUser() {
        prefs.clearUser()
        finish()
        startActivity(Intent(this, LoginActivity::class.java))

    }

}