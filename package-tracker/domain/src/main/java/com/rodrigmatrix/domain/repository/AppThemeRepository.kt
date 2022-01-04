package com.rodrigmatrix.domain.repository

import com.rodrigmatrix.domain.entity.SingleChoicePreference
import kotlinx.coroutines.flow.Flow

interface AppThemeRepository {

    fun getThemeOptions(): Flow<List<SingleChoicePreference>>

    fun setTheme(theme: String): Flow<Unit>

    fun getSelectedThemeKey(): String
}