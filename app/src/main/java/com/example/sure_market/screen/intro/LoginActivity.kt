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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sure_market.network.ApiRepository
import com.example.sure_market.screen.main.MainActivity
import com.example.sure_market.ui.theme.SureMarketTheme
import com.example.sure_market.viewmodel.LoginViewModel
import com.example.sure_market.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

const val LOGIN = "login"
const val SIGNUP = "signup"
const val RESULT_SIGNUP = 1
const val RESULT_SIGNIN = 2

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
    }
    private val viewModelFactory: LoginViewModelFactory by lazy {
        LoginViewModelFactory(ApiRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private suspend fun getRepository(email: String, password: String):Long {
        val job = lifecycleScope.async {
            var result: Long = 0
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    requestViewRepository(email = email, password = password)
                    viewRepository.collect { responseUserId ->
                        responseUserId?.let { result = it.userId }
                    }
                }
            }
            result
        }.await()
        return job
    }


    private val signUp: (String, String, String, String) -> Unit = { name, password, phone, email ->
        if (name.isBlank() || password.isBlank() || phone.isBlank() || email.isBlank()) {
            Toast.makeText(this, "빈 항목이 있습니다.", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("name", name)
                putExtra("password", password)
                putExtra("phoneNum", phone)
                putExtra("email", email)
            }
            setResult(RESULT_OK, intent)
            Log.d("DaeYoung", "name: $name, password: $password, phoneNum: $phone, email: $email")
            finish()
        }
    }

    // 메인 액티비티로 이동, 로그인
    private val signIn: (String, String) -> Unit = { email, password ->
        if (password.isBlank() || email.isBlank()) {
            Toast.makeText(this, "빈 항목이 있습니다.", Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch {
                val userId = getRepository(email = email, password = password)
                Log.d("DaeYoung", " 로그인 결과 userId: $userId")
                val intent = Intent(applicationContext, MainActivity::class.java).apply {
                    putExtra("userId", userId)
                }
                setResult(RESULT_SIGNIN, intent)
                Log.d("DaeYoung", "password: $password, email: $email")
                finish()
            }
        }
    }
}