package com.example.sure_market.screen.post

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sure_market.data.UserSharedPreference
import com.example.sure_market.network.PostRepository
import com.example.sure_market.screen.map.MapActivity
import com.example.sure_market.ui.theme.SureMarketTheme
import com.example.sure_market.viewmodel.PostViewModel
import com.example.sure_market.viewmodel.PostViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class PostActivity : AppCompatActivity() {
    private lateinit var viewModel: PostViewModel
    private lateinit var viewModelFactory: PostViewModelFactory
    private val prefs: UserSharedPreference by lazy {
        UserSharedPreference(this)
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = PostViewModelFactory(PostRepository(), application)
        viewModel = ViewModelProvider(this, viewModelFactory)[PostViewModel::class.java]
        val intent = Intent(this, MapActivity::class.java)

        val activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val place = result.data?.getStringExtra("placeName") ?: "nullPointException"
                    val region = result.data?.getStringExtra("region") ?: "nullPointException"
                    viewModel.also {
                        it.setPlace(place)
                        it.setRegion(region)
                    }
                }
            }

        setContent {
            SureMarketTheme {
                PostScreen(
                    viewModel = viewModel,
                    onMoveMain = { onMoveMain() },
                    postComplete = { postComplete() }) {
                    activityResultLauncher.launch(intent)
                }
            }
        }
    }

    private fun postComplete() {
        val userId = prefs.getUserPref("userId")
        val price = if (viewModel.price.value.isBlank()) {
            0
        } else {
            viewModel.price.value.toLong()
        }
        if (userId != 0L) {
//            val postData = PostRegisterData(
//                userId = userId,
//                title = viewModel.title.value,
//                price = price,
//                category = viewModel.category.value,
//                region = viewModel.region.value,
//                content = viewModel.content.value
//            )
            val postData =
                JSONObject(
                    "{\"userId\":\"${userId}\"," +
                            "\"category\":\"${viewModel.category.value}\"," +
                            "\"title\":\"${viewModel.title.value}\"," +
                            "\"contents\":\"${viewModel.content.value}\"," +
                            "\"price\":\"${price}\","
                ).toString()
            val jsonObject = postData.toRequestBody()

            lifecycleScope.launch {
//                val postId = async { viewModel.requestViewRepository(jsonObject) }.await()
                async { viewModel.requestViewRepository(jsonObject) }.await()

                Log.d("daeYoung", "postId: ${viewModel.viewRepository.value?.postId}")
                finish()
            }
        }
    }

    private fun onMoveMain() {
        finish()
    }

}

