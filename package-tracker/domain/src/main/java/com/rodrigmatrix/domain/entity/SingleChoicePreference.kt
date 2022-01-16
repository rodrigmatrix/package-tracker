package com.rodrigmatrix.domain.entity

class SingleChoicePreference<T>(
    val name: String,
    val value: T,
    val selected: Boolean
)