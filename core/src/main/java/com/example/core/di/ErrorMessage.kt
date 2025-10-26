package com.example.core.di

import java.io.IOException

class ApiException(
    override val message: String,
    val code: Int
) : IOException(message)