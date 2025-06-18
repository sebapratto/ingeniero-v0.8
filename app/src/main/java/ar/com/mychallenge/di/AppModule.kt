package ar.com.mychallenge.di

import ar.com.mychallenge.data.local.SharedPreferencesManager
import ar.com.mychallenge.data.remote.CityApiService
import ar.com.mychallenge.data.repository.CityRepository
import ar.com.mychallenge.data.repository.CityRepositoryImpl
import ar.com.mychallenge.presentation.city.viewmodel.CityViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<HttpClient> { HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    } }

    single<Json> { Json {
        ignoreUnknownKeys = true
        isLenient = false
    } }

    single { SharedPreferencesManager(androidContext()) }
    single { CityApiService(get(), get()) }
    single<CityRepository> { CityRepositoryImpl(get()) }
    viewModel { CityViewModel(get(), get()) }
}