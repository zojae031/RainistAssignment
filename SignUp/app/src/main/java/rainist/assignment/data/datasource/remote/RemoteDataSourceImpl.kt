package rainist.assignment.data.datasource.remote

import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.schedulers.Schedulers
import rainist.assignment.data.dao.UserEntity

class RemoteDataSourceImpl : RemoteDataSource {
    override fun requestSignUp(entity: UserEntity): Single<String> {
        return Single.create(SingleOnSubscribe<String> {
            when {
                entity.email == "edgar3@naver.com" -> it.onError(throw Http401Exception())
                entity.email == "recruit@rainist.com" -> it.onError(throw Http404Exception())
                else -> it.onSuccess(entity.toString())
            }
        }).subscribeOn(Schedulers.io())
    }
}

class Http401Exception : Exception()
class Http404Exception : Exception()