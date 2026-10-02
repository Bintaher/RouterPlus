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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RouterPlusApp() }
    }
}

@Composable
fun RouterPlusApp() {
    var screen by remember { mutableStateOf("welcome") }
    var networkName by remember { mutableStateOf("") }
    var ip by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            when (screen) {
                "welcome" -> WelcomeScreen { screen = "add" }
                "add" -> AddNetworkScreen(
                    networkName, { networkName = it },
                    ip, { ip = it },
                    username, { username = it },
                    password, { password = it },
                    onSave = { screen = "dashboard" }
                )
                else -> DashboardScreen(networkName, ip)
            }
        }
    }
}

@Composable
fun WelcomeScreen(onStart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Router Plus", fontSize = 36.sp)
        Spacer(Modifier.height(12.dp))
        Text("إدارة شبكات MikroTik بسهولة وسرعة", fontSize = 18.sp)
        Spacer(Modifier.height(40.dp))
        Button(
            onClick = onStart,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(16.dp)
        ) { Text("ابدأ الآن", fontSize = 18.sp) }
    }
}

@Composable
fun AddNetworkScreen(
    name: String, onName: (String) -> Unit,
    ip: String, onIp: (String) -> Unit,
    user: String, onUser: (String) -> Unit,
    pass: String, onPass: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(35.dp))
        Text("إضافة شبكة MikroTik", fontSize = 28.sp)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(name, onName, Modifier.fillMaxWidth(), label = { Text("اسم الشبكة") }, singleLine = true)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(ip, onIp, Modifier.fillMaxWidth(), label = { Text("عنوان IP الشبكة") }, singleLine = true)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(user, onUser, Modifier.fillMaxWidth(), label = { Text("اسم المستخدم") }, singleLine = true)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            pass, onPass, Modifier.fillMaxWidth(),
            label = { Text("كلمة المرور") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(28.dp))
        Button(
            onClick = onSave,
            enabled = name.isNotBlank() && ip.isNotBlank() && user.isNotBlank(),
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(16.dp)
        ) { Text("حفظ والاتصال", fontSize = 18.sp) }
    }
}

@Composable
fun DashboardScreen(name: String, ip: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(45.dp))
        Text("لوحة التحكم", fontSize = 30.sp)
        Spacer(Modifier.height(20.dp))
        Text("الشبكة: $name", fontSize = 19.sp)
        Text("IP: $ip", fontSize = 17.sp)
        Spacer(Modifier.height(30.dp))
        Text("تم حفظ الشبكة بنجاح", fontSize = 18.sp)
    }
}
