package com.example.frankito.hive.util

private fun async(call: () -> Unit) = Thread { call() }.start()
