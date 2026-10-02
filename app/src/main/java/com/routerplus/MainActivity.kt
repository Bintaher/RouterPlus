package com.routerplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RouterPlusApp()
        }
    }
}

@Composable
fun RouterPlusApp() {

    var screen by remember { mutableStateOf("login") }

    MaterialTheme {

        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Rtl
        ) {

            Surface(
                modifier = Modifier.fillMaxSize()
            ) {

                when (screen) {

                    "login" -> LoginScreen(
                        onLogin = {
                            screen = "add"
                        },
                        onCreateAccount = {
                            // سيتم ربط إنشاء الحساب لاحقًا
                        },
                        onForgotPassword = {
                            // سيتم إضافة استعادة كلمة المرور لاحقًا
                        }
                    )

                    "add" -> AddRouterScreen(
                        onSaved = {
                            screen = "dashboard"
                        }
                    )

                    "dashboard" -> DashboardScreen()
                }
            }
        }
    }
}


/* =========================
   تسجيل الدخول
   ========================= */

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onCreateAccount: () -> Unit,
    onForgotPassword: () -> Unit
) {

    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Router Plus",
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "إدارة MikroTik بسهولة واحترافية",
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = emailOrPhone,
            onValueChange = { emailOrPhone = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("البريد الإلكتروني أو رقم الهاتف")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("كلمة المرور")
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = onForgotPassword,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("نسيت كلمة المرور؟")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onLogin,
            enabled = emailOrPhone.isNotBlank() && password.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "تسجيل الدخول",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text("ليس لديك حساب؟")

            TextButton(
                onClick = onCreateAccount
            ) {
                Text(
                    text = "إنشاء حساب",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


/* =========================
   إضافة راوتر MikroTik
   ========================= */

@Composable
fun AddRouterScreen(
    onSaved: () -> Unit
) {

    var routerName by remember { mutableStateOf("") }
    var ip by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "إضافة راوتر",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "أضف بيانات MikroTik للبدء"
        )

        Spacer(modifier = Modifier.height(28.dp))

        OutlinedTextField(
            value = routerName,
            onValueChange = { routerName = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("اسم الراوتر") },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = ip,
            onValueChange = { ip = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("IP / Host") },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("اسم المستخدم") },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("كلمة المرور") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(26.dp))

        Button(
            onClick = onSaved,
            enabled = routerName.isNotBlank() &&
                    ip.isNotBlank() &&
                    username.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            Text(
                text = "اختبار الاتصال وحفظ",
                fontSize = 18.sp
            )
        }
    }
}


/* =========================
   لوحة التحكم
   ========================= */

@Composable
fun DashboardScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Router Plus",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "لوحة التحكم",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "مرحبًا بك في Router Plus"
        )
    }
}
