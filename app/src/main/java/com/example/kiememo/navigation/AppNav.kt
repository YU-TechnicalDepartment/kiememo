package com.example.kiememo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.kiememo.data.*
import com.example.kiememo.ui.*

@Composable
fun AppNav(
    userStore: UserStore,
    memoStore: MemoStore
) {
    val navController = rememberNavController()

    val name by userStore.nameFlow.collectAsState(initial = "")
    val storedMemos by memoStore.memosFlow.collectAsState(initial = emptyList())

    MemoRepository.setAll(storedMemos)

    NavHost(
        navController = navController,
        startDestination = if (name.isBlank()) "start" else "list"
    ) {
        composable("start") {
            StartScreen { newName ->
                // 名前保存は MainActivity 側で行うので、ここでは遷移だけ
                navController.navigate("list") {
                    popUpTo("start") { inclusive = true }
                }
            }
        }

        composable("list") {
            MemoListScreen(
                name = if (name.isBlank()) "ゲスト" else name,
                memos = MemoRepository.getAll(),
                onAddClick = { navController.navigate("add") },
                onMemoClick = { memo -> navController.navigate("detail/${memo.id}") },
                onRemixClick = { memo -> navController.navigate("remix/${memo.id}") },
                onDeleteClick = { memo ->
                    MemoRepository.delete(memo.id)
                },
                onQRShareClick = { memo ->
                    navController.navigate("qrshare/${memo.id}")
                },
                onQRScanClick = {
                    navController.navigate("qrscan")
                }
            )
        }

        composable("add") {
            AddMemoScreen(
                onSave = { memo ->
                    MemoRepository.add(memo)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("id") ?: return@composable
            val memo = MemoRepository.find(id) ?: return@composable

            MemoDetailScreen(
                memo = memo,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            "remix/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("id") ?: return@composable
            val memo = MemoRepository.find(id) ?: return@composable

            RemixScreen(
                original = memo,
                onSave = { newMemo ->
                    MemoRepository.add(newMemo)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            "qrshare/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("id") ?: return@composable
            val memo = MemoRepository.find(id) ?: return@composable

            QRShareScreen(
                memo = memo,
                onBack = { navController.popBackStack() }
            )
        }

        composable("qrscan") {
            QRScanScreen(
                onMemoImported = { memo ->
                    MemoRepository.add(memo)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
