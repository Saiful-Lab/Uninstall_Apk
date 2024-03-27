package com.saiful.apkcheckanduninstall

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.saiful.apkcheckanduninstall.ui.theme.ApkCheckAndUninstallTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApkCheckAndUninstallTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val installedApplications = packageManager
        .getInstalledApplications(PackageManager.GET_META_DATA)
        .filter { appInfo ->
            appInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
        }
    Log.d("TAG", "AppContent: ${installedApplications.size}")
    Column {
        Text(text = "Installed Applications")
        AppList(installedApplications = installedApplications)
    }
}

@Composable
fun AppList(installedApplications: List<ApplicationInfo>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(installedApplications) { appInfo ->
            AppItem(appInfo = appInfo)
        }
    }
}

@Composable
fun AppItem(appInfo: ApplicationInfo) {
    val context = LocalContext.current
    val packageName = appInfo.packageName
    Button(
        onClick = {
            Log.d("TAG", packageName)
            val intent = Intent(Intent.ACTION_DELETE)
            intent.data = Uri.parse("package:$packageName")
            context.startActivity(intent)
//            val pkg = listOf("com.saiful.deleteme", "com.saiful.deleteme2")
//            pkg.forEach {
//                val intent = Intent(Intent.ACTION_DELETE)
//                intent.data = Uri.parse("package:$it")
//                context.startActivity(intent)
//            }
        }
    ) {
        Text(text = packageName)
    }
}

//fun uninstallApp(context: Context, packageName: String) {
//    val intent = Intent(Intent.ACTION_UNINSTALL_PACKAGE).apply {
//        data = Uri.parse("package:$packageName")
//    }
//    context.startActivity(intent)
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApkCheckAndUninstallTheme {
        AppContent()
    }
}