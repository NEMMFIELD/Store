package com.example.basket.network

sealed class State<out T> {
    class Failure(val message: Throwable) : State<Nothing>()
    class Success<T>(val data: T) : State<T>()
    object Empty : State<Nothing>()
}
