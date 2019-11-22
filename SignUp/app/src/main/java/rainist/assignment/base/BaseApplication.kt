package rainist.assignment.base

import android.app.Application
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import rainist.assignment.di.dataSourceModule
import rainist.assignment.di.repositoryModule
import rainist.assignment.di.resourceModule
import rainist.assignment.di.viewModelModule
import timber.log.Timber
import java.io.IOException
import java.net.SocketException


class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
        settingRxPlugIn()
        initTimber()
    }


    private fun initKoin() {
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(viewModelModule, repositoryModule, dataSourceModule, resourceModule))
        }
    }

    private fun settingRxPlugIn() {
        RxJavaPlugins.setErrorHandler {
            var error = it
            if (error is UndeliverableException) {
                error = it.cause
            }
            if (error is IOException || error is SocketException) {
                return@setErrorHandler
            }
            if (error is InterruptedException) {
                return@setErrorHandler
            }
            if (error is NullPointerException || error is IllegalArgumentException) {
                Thread.currentThread()
                    .uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            if (error is IllegalStateException) {
                Thread.currentThread()
                    .uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            Timber.tag("RxJavaPluginError")
                .d("Undeliverable Exception received, not sure what to do")
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
