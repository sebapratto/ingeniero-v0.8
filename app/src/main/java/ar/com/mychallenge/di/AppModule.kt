package ar.com.mychallenge.di

import ar.com.mychallenge.data.remote.CityApiService
import ar.com.mychallenge.data.repository.CityRepository
import ar.com.mychallenge.data.repository.CityRepositoryImpl
import ar.com.mychallenge.presentation.city.viewmodel.CityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CityApiService() }
    single<CityRepository> { CityRepositoryImpl(get()) }
    viewModel { CityViewModel(get()) }
}