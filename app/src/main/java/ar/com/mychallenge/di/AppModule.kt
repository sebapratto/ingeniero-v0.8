package ar.com.mychallenge.di

import ar.com.mychallenge.data.remote.CityApiService
import ar.com.mychallenge.data.repository.CityRepository
import ar.com.mychallenge.data.repository.CityRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single { CityApiService() }
    single<CityRepository> { CityRepositoryImpl(apiService = get()) }
}