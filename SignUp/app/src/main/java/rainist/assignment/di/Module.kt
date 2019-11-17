package rainist.assignment.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rainist.assignment.viewmodel.MainViewModel

val viewModelModule = module {
    viewModel { MainViewModel() }
}