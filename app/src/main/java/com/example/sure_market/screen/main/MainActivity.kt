package com.example.sure_market.screen.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModelFactory = MainViewModelFactory(PostRepository())
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

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

    private val onMoveDetail:(Int) -> Unit =  { postId ->
        val intent = Intent(this, DetailActivity::class.java).also {
            it.putExtra("postId", postId)
            Log.d("daeYoung", "postId: ${postId}")
        }
        startActivity(intent)
    }

    private fun clearUser() {
        prefs.clearUser()
        finish()
        startActivity(Intent(this, LoginActivity::class.java))

    }

}