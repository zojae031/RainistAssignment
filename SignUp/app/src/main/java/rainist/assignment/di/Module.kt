package rainist.assignment.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rainist.assignment.data.Repository
import rainist.assignment.data.RepositoryImpl
import rainist.assignment.data.datasource.DataBase
import rainist.assignment.data.datasource.local.LocalDataSource
import rainist.assignment.data.datasource.local.LocalDataSourceImpl
import rainist.assignment.data.datasource.remote.RemoteDataSource
import rainist.assignment.data.datasource.remote.RemoteDataSourceImpl
import rainist.assignment.util.ResourceUtil
import rainist.assignment.viewmodel.MainViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}
val repositoryModule = module {
    single<Repository> { RepositoryImpl(get(), get()) }
}
val dataSourceModule = module {
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl() }
    single {
        Room.databaseBuilder(
            androidContext(),
            DataBase::class.java,
            "db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

val resourceModule = module {
    single { ResourceUtil(androidContext().resources) }
}