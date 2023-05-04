package com.example.sure_market.screen.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.sure_market.data.PostRegisterData
import com.example.sure_market.data.ResponseListData
import com.example.sure_market.data.UserSharedPreference
import com.example.sure_market.network.ApiRepository
import com.example.sure_market.network.PostRepository
import com.example.sure_market.screen.detail.DetailActivity
import com.example.sure_market.screen.intro.LoginActivity
import com.example.sure_market.screen.post.PostActivity
import com.example.sure_market.ui.theme.SureMarketTheme
import com.example.sure_market.viewmodel.LoginViewModel
import com.example.sure_market.viewmodel.LoginViewModelFactory
import com.example.sure_market.viewmodel.MainViewModel
import com.example.sure_market.viewmodel.MainViewModelFactory
import kotlinx.coroutines.async

class MainActivity : ComponentActivity() {
    private lateinit var prefs: UserSharedPreference
    private lateinit var intent: Intent

    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory


    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("DaeYoung", "activity SUCCESS")
            if (result.resultCode == RESULT_OK) {
                val userData = com.example.sure_market.data.UserData(
                    name = result.data?.getStringExtra("name") ?: "asd",
                    password = result.data?.getStringExtra("password") ?: "",
                    phoneNum = result.data?.getStringExtra("phoneNum") ?: "",
                    email = result.data?.getStringExtra("email") ?: ""
                )
//              viewModel.requestSignUp(userData = userData)
//              prefs.setUserPrefs(userId = viewModel.viewRepository.value)
                prefs.setUserPrefs(userId = 1L)
            } else if (result.resultCode == 4) { // 로그인 result 코드 변경할 것
                val userId: Int = result.data?.getIntExtra("userId", 0) ?: 0
//                prefs.setUserPrefs(userId = userId)
                prefs.setUserPrefs(userId = 1L)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {

        viewModelFactory = MainViewModelFactory(PostRepository())
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        val str = "userId"
        prefs = UserSharedPreference(this)

        Log.d("DaeYoung", "user_id: ${prefs.getUserPref(str)}")

        if (prefs.getUserPref("userId") == 0L) {
            intent = Intent(this, LoginActivity::class.java)
            activityResultLauncher.launch(intent)
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
        activityResultLauncher.launch(intent)
    }

}