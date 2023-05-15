package com.example.sure_market.screen.intro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sure_market.data.ResponseUser
import com.example.sure_market.data.SignupData
import com.example.sure_market.data.UserData
import com.example.sure_market.data.UserSharedPreference
import com.example.sure_market.network.ApiRepository
import com.example.sure_market.screen.main.MainActivity
import com.example.sure_market.ui.theme.SureMarketTheme
import com.example.sure_market.viewmodel.LoginViewModel
import com.example.sure_market.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

const val LOGIN = "login"
const val SIGNUP = "signup"
const val RESULT_SIGNUP = 1
const val RESULT_SIGNIN = 2

class LoginActivity : AppCompatActivity() {
    private lateinit var prefs: UserSharedPreference
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
    }
    private val viewModelFactory: LoginViewModelFactory by lazy {
        LoginViewModelFactory(ApiRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = UserSharedPreference(this)

        setContent {
            SureMarketTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = LOGIN) {
                    composable(LOGIN) {
                        LoginScreen(signIn) { navController.navigate(SIGNUP) }
                    }
                    composable(SIGNUP) {
                        SignUp(
                            onBack = { navController.popBackStack() },
                            signUp
                        )
                    }
                }
            }
        }
    }


    private val signUp: (String, String, String, () -> Unit) -> Unit = { name, password, email, popBackStack ->
        if (name.isBlank() || password.isBlank() || email.isBlank()) {
            Toast.makeText(this, "빈 항목이 있습니다.", Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch {
                val response = async {
                    viewModel.requestSignUpRepository(SignupData(name, password, email))
                    viewModel.responseSignUp.value
                }.await()
                response?.let {
                    if (it) popBackStack()
                    else { Toast.makeText(applicationContext, "회원가입 실패", Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }

    // 메인 액티비티로 이동, 로그인
    private val signIn: (String, String) -> Unit = { name, password ->
        if (password.isBlank() || name.isBlank()) {
            Toast.makeText(this, "빈 항목이 있습니다.", Toast.LENGTH_SHORT).show()
        } else {

            lifecycleScope.launch {
                val responseUser = async {
                    viewModel.requestLogInRepository(UserData(name = name, password = password))
                    viewModel.responseLogIn.value
                }.await()
                Log.d("DaeYoung", "로그인 결과 responseUser: $responseUser")
                responseUser?.let {
                    prefs.setUserPrefs(userToken = it.token)
                    moveMainScreen()
                }
            }
        }
    }

    private fun moveMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}