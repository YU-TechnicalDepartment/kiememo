package com.example.kiememo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kiememo.data.Memo
import com.example.kiememo.util.generateQr
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun QRShareScreen(
    memo: Memo,
    onBack: () -> Unit
) {
    val json = Json.encodeToString(memo)
    val qr = generateQr(json)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text("QRコードで共有", fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))

        Image(
            bitmap = qr.asImageBitmap(),
            contentDescription = "QRコード",
            modifier = Modifier.size(256.dp)
        )

        Spacer(Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text("戻る")
        }
    }
}
