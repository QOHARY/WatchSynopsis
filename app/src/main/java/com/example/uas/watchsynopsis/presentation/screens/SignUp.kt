package com.example.uas.watchsynopsis.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.uas.watchsynopsis.data.local.User
import com.example.uas.watchsynopsis.presentation.viewmodels.UserViewModel
import com.example.uas.watchsynopsis.presentation.components.showToast
import com.example.uas.watchsynopsis.presentation.navigation.Screen
import com.example.uas.watchsynopsis.presentation.theme.MyApplicationTheme
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.example.uas.watchsynopsis.presentation.util.validateEmail
import com.example.uas.watchsynopsis.presentation.util.validatePassword
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController, preferenceHelper: PreferenceHelper) {

    val firebaseAnalytics = Firebase.analytics
    FirebaseCrashlytics.getInstance().log("User action logged: SignUp.kt")
    val context = LocalContext.current
    val viewModel = hiltViewModel<UserViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.film_reel),
                contentDescription = "Logo",
                modifier = Modifier.size(70.dp)
            )
        }
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
            text = "REGISTER",
            style = TextStyle(
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                lineBreak = LineBreak.Paragraph
            )
        )

        Spacer(modifier = Modifier.height(25.dp))

        //Character Count Limit on Text Field:
        val maxCharEmail = 50
        val maxCharPassword = 30
        val maxCharUsername = 50

        //UserName TextField:
        var name by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            value = name,
            onValueChange = {
                if (it.length <= maxCharUsername) {
                    name = it
                    firebaseAnalytics.logEvent("username_input_change") {
                        param("username", it)
                        param("screen", "SignUp")
                    }
                }
            },
            placeholder = { Text(text = "Username") },
            label = { Text(text = "Username") },
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                IconButton(onClick = {
                    FirebaseCrashlytics.getInstance().log("SignUp button clicked: SignUp.kt") }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Person Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.None
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Email Text Field:
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
                        param("screen", "SignUp")
                    }
                }
            },
            placeholder = { Text(text = "Email") },
            label = { Text(text = "Email") },
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                IconButton(onClick = {
                    FirebaseCrashlytics.getInstance().log("SignUp button clicked: SignUp.kt") }) {
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

        //Password Text Field:
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
                }
            },
            singleLine = true,
            maxLines = 1,
            placeholder = { Text(text = "Password") },
            label = { Text(text = "Password") },
            leadingIcon = {
                IconButton(onClick = {
                    FirebaseCrashlytics.getInstance().log("SignUp button clicked: SignUp.kt")}) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password Icon"
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
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        var passwordConfirmation by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            value = passwordConfirmation,
            onValueChange = {
                if (it.length <= maxCharPassword) {
                    passwordConfirmation = it
                    firebaseAnalytics.logEvent("password_input_change") {
                        param("password", it)
                        param("screen", "SignUp")
                    }
                }
            },
            singleLine = true,
            maxLines = 1,
            placeholder = { Text(text = "Confirm Password") },
            label = { Text(text = "Confirm Password") },
            leadingIcon = {
                IconButton(onClick = {
                    FirebaseCrashlytics.getInstance().log("SignUp button clicked: SignUp.kt") }) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password Icon"
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
        Spacer(modifier = Modifier.height(15.dp))
        //Button Login
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            onClick = {
                FirebaseCrashlytics.getInstance().log("SignUp button clicked: SignUp.kt")
                FirebaseCrashlytics.getInstance().log("SignUp button clicked: SignUp.kt")
                firebaseAnalytics.logEvent("sign_up_click") {
                    param("username", name)
                    param("email", email)
                    param("password", password)
                    param("password_confirmation", passwordConfirmation)
                    param("screen", "SignUp")
                }
                if (name.isNotEmpty()) {

                    if (validateEmail(email)) {

                        if (password.isNotEmpty()) {

                            if (validatePassword(password)) {

                                if (passwordConfirmation.isNotEmpty()) {

                                    if (password == passwordConfirmation) {

                                        viewModel.viewModelScope.launch {
                                            val isAuthenticated = viewModel.authenticateUser(email, password)
                                            if (!isAuthenticated) {
                                                val newUser = User(
                                                    userName = name,
                                                    email = email,
                                                    password = password
                                                )
                                                viewModel.insertUser(newUser)
                                                preferenceHelper.setUserLoggedIn(email)
                                                showToast(context, "Account Created Successfully!")
                                                navController.popBackStack()
                                                navController.navigate(Screen.SignInScreen.route)
                                            } else {
                                                showToast(context, "Email already exists!")
                                            }
                                        }

                                    } else {
                                        showToast(context, "Your Password Confirmation Needs to be the same!")
                                    }
                                } else {
                                    showToast(context, "Confirm Your Password Please!")
                                }
                            } else {
                                showToast(context, "Needs to contain at least one uppercase letter and one number")
                            }
                        } else {
                            showToast(context, "Invalid Password!")
                        }
                    } else {
                        showToast(context, "Invalid Email!")
                    }
                } else {
                    showToast(context, "Enter an Username Please!")
                }

            },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Text(text = "SIGN UP")
        }

        Spacer(modifier = Modifier.height(30.dp))

        //Login Methods
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
        //SignUp Text/Button
        Text(
            text = "Already Have an Account?",
            modifier = Modifier
                .padding(3.dp)
                .wrapContentSize(Alignment.Center)
        )
        Text(
            text = "Sign In",
            style = TextStyle(color = Color.Blue, fontSize = 15.sp),
            modifier = Modifier
                .padding(3.dp, end = 10.dp)
                .clickable {
                    firebaseAnalytics.logEvent("sign_in_click") {
                        param("screen", "SignUp")
                    }
                    navController.navigate(Screen.SignInScreen.route)
                }
                .wrapContentSize(Alignment.Center)
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreviewDayMode() {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SignUpScreen(rememberNavController(), PreferenceHelper(LocalContext.current))
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpScreenPreviewNightMode() {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SignUpScreen(rememberNavController(), PreferenceHelper(LocalContext.current))
        }
    }
}
