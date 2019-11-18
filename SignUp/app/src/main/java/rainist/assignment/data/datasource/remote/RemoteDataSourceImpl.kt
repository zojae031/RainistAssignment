package rainist.assignment.data.datasource.remote

import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.schedulers.Schedulers
import rainist.assignment.data.dao.UserEntity

class RemoteDataSourceImpl : RemoteDataSource {
    override fun requestSignUp(entity: UserEntity): Single<String> {
        return Single.create(SingleOnSubscribe<String> {
            if (entity.id == "1")
                it.onError(throw Http401Exception())
            else it.onSuccess("회원가입에 성공하였습니다.")
        }).subscribeOn(Schedulers.io())
    }
}

class Http401Exception : Exception()
class Http404Exception : Exception()