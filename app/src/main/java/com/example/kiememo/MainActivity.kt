package com.example.kiememo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.kiememo.data.MemoRepository
import com.example.kiememo.data.MemoStore
import com.example.kiememo.data.UserStore
import com.example.kiememo.navigation.AppNav
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    private lateinit var userStore: UserStore
    private lateinit var memoStore: MemoStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userStore = UserStore(this)
        memoStore = MemoStore(this)

        setContent {
            MaterialTheme {
                Surface {
                    AppNav(
                        userStore = userStore,
                        memoStore = memoStore
                    )
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        runBlocking {
            memoStore.saveMemos(MemoRepository.getAll())
        }
    }
}
