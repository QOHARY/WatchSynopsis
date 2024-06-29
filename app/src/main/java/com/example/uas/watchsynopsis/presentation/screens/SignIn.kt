package com.example.uas.watchsynopsis.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uas.watchsynopsis.R
import com.example.uas.watchsynopsis.presentation.viewmodels.UserViewModel
import com.example.uas.watchsynopsis.presentation.components.showToast
import com.example.uas.watchsynopsis.presentation.navigation.Screen
import com.example.uas.watchsynopsis.presentation.navigation.releasesListNavigationRoutes
import com.example.uas.watchsynopsis.presentation.theme.MyApplicationTheme
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController,
                 preferenceHelper: PreferenceHelper,
                 userViewModel: UserViewModel = hiltViewModel()) {
    val firebaseAnalytics = Firebase.analytics
    FirebaseCrashlytics.getInstance().log("User action logged: SignIn.kt")
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.film_reel),
                contentDescription = "Logo",
                modifier = Modifier.size(70.dp)
            )
            }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            text = "WatchSynopsis",
            style = TextStyle(
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                lineBreak = LineBreak.Paragraph
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            text = "LOGIN",
            style = TextStyle(
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                lineBreak = LineBreak.Paragraph
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        val maxCharEmail = 50
        val maxCharPassword = 30
        var email by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            value = email,
            onValueChange = {
                if (it.length <= maxCharEmail) {
                    email = it
                    firebaseAnalytics.logEvent("email_input_change") {
                        param("email", it)
                        param("screen", "SignIn")
                    }
                }
            },
            placeholder = { Text(text = "Email") },
            label = { Text(text = "Email") },
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                IconButton(onClick = {
                    FirebaseCrashlytics.getInstance().log("SignIn button clicked: SignIn.kt")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.None
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        var password by rememberSaveable { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }

        val icon = if (passwordVisibility) {
            painterResource(id = R.drawable.baseline_visibility_24)
        } else {
            painterResource(id = R.drawable.baseline_visibility_off_24)
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            value = password,
            onValueChange = {
                if (it.length <= maxCharPassword) {
                    password = it
                    firebaseAnalytics.logEvent("password_input_change") {
                        param("password", it)
                        param("screen", "SignIn")
                    }
                }
            },
            singleLine = true,
            maxLines = 1,
            placeholder = { Text(text = "Password") },
            label = { Text(text = "Password") },
            leadingIcon = {
                IconButton(onClick = {
                    FirebaseCrashlytics.getInstance().log("SignIn button clicked: SignIn.kt") }) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password Icon"
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    FirebaseCrashlytics.getInstance().log("SignIn button clicked: SignIn.kt")
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = if (passwordVisibility) "Hide Password" else "Show Password",
                        tint = Color.Gray
                    )
                }
            },
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            onClick = {
                FirebaseCrashlytics.getInstance().log("SignIn button clicked: SignIn.kt")
                firebaseAnalytics.logEvent("sign_in_click") {
                    param("email", email)
                    param("password", password)
                    param("screen", "SignIn")
                }
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    userViewModel.viewModelScope.launch {
                        val isAuthenticated = userViewModel.authenticateUser(email, password)
                        if (isAuthenticated) {
                            preferenceHelper.setUserLoggedIn(email)
                            showToast(context, "SignIn Successfully!")
                            navController.popBackStack()
                            navController.navigate(releasesListNavigationRoutes)
                        } else {
                            showToast(context, "Invalid Email or Password!")
                        }
                    }
                } else {
                    showToast(context, "Enter Email and Password!")
                }
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Text(text = "LOGIN")
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier
                    .width(40.dp)
                    .padding(end = 5.dp)
            )
        }
        Text(
            text = "New User?",
            modifier = Modifier
                .padding(3.dp)
                .wrapContentSize(Alignment.Center)
        )
        Text(
            text = "Sign Up",
            style = TextStyle(color = Color.Blue, fontSize = 15.sp),
            modifier = Modifier
                .padding(3.dp, end = 10.dp)
                .clickable {
                    firebaseAnalytics.logEvent("sign_up_click") {
                        param("screen", "SignIn")
                    }
                    navController.navigate(Screen.SignUpScreen.route)
                }
                .wrapContentSize(Alignment.Center)
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreviewDayMode() {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val context = LocalContext.current
            val preferenceHelper = PreferenceHelper(context)
            SignInScreen(
                navController = rememberNavController(),
                preferenceHelper = preferenceHelper
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignInScreenPreviewNightMode() {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val context = LocalContext.current
            val preferenceHelper = PreferenceHelper(context)
            SignInScreen(
                navController = rememberNavController(),
                preferenceHelper = preferenceHelper
            )
        }
    }
}
