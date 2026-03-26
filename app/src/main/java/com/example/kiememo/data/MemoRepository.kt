package com.example.kiememo.data

object MemoRepository {
    private val memos = mutableListOf<Memo>()

    fun setAll(list: List<Memo>) {
        memos.clear()
        memos.addAll(list)
        cleanupExpired()
    }

    fun getAll(): List<Memo> {
        cleanupExpired()
        return memos
    }

    fun add(memo: Memo) {
        memos.add(memo)
        cleanupExpired()
    }

    fun delete(id: String) {
        memos.removeAll { it.id == id }
    }

    fun find(id: String): Memo? = memos.find { it.id == id }

    private fun cleanupExpired() {
        val now = System.currentTimeMillis()
        memos.removeAll { it.expiresAt < now }
    }
}
