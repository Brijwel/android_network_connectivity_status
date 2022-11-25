package com.brijwel.networkconnectivity.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun isNetworkAvailable(): Boolean

    fun observeConnectivity(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}