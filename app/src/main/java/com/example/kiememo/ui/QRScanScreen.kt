package com.example.kiememo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kiememo.data.Memo
import com.example.kiememo.util.memoFromJson

@Composable
fun QRScanScreen(
    onMemoImported: (Memo) -> Unit,
    onBack: () -> Unit
) {
    var raw by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text("QRコードを読み取る（簡易版）", fontSize = 20.sp)
        Spacer(Modifier.height(16.dp))

        Text(
            "※ 実際のカメラ読み取りは後で追加。ここではQRから得た文字列を貼り付けてテストできます。",
            fontSize = 12.sp
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = raw,
            onValueChange = { raw = it },
            label = { Text("QRから得た文字列（JSON）") },
            modifier = Modifier.fillMaxWidth()
        )

        error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }

        Spacer(Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = onBack) {
                Text("戻る")
            }
            Button(onClick = {
                val memo = memoFromJson(raw)
                if (memo != null) {
                    onMemoImported(memo)
                } else {
                    error = "読み取りに失敗しました"
                }
            }) {
                Text("インポート")
            }
        }
    }
}
