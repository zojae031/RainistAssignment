package rainist.assignment

import com.google.gson.JsonArray
import org.junit.Test
import rainist.assignment.data.dao.UserEntity
import rainist.assignment.data.datasource.remote.Http401Exception
import rainist.assignment.data.datasource.remote.Http404Exception
import rainist.assignment.data.datasource.remote.RemoteDataSourceImpl
import rainist.assignment.util.ConstUtil
import rainist.assignment.util.ConstUtil.AUTO_INCREMENT_DATA


class RemoteDataSourceTest {
    private val permissionList = listOf(0, 0, 0, 0)

    private val entity =
        UserEntity(AUTO_INCREMENT_DATA,
            "edgar3@naver.com",
            "123456a!",
            "조재영",
            "940228-10000000",
            0,
            JsonArray().apply {
                permissionList.map { add(it) }
            }
        )

    private val entity2 = UserEntity(AUTO_INCREMENT_DATA,
        "recruit@rainist.com",
        "123123A#",
        "레이니스트",
        "901010-1000000",
        1,
        JsonArray().apply {
            permissionList.map { add(it) }
        }
    )

    @Test
    fun getData() {
        RemoteDataSourceImpl().requestSignUp(entity2)
            .subscribe(
                { data ->
                    assert(data.equals(entity))
                },
                { error ->
                    when (error) {
                        is Http404Exception -> assert(ConstUtil.ERROR_404 == error.toString())
                        is Http401Exception -> assert(ConstUtil.ERROR_401 == error.toString())
                    }
                }
            )
    }

}