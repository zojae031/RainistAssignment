package rainist.assignment.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rainist.assignment.data.Repository
import rainist.assignment.data.RepositoryImpl
import rainist.assignment.viewmodel.MainViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}
val repositoryModule = module {
    single<Repository> { RepositoryImpl() }
}