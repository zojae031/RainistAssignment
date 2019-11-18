1. # RainistAssignment


   # 1일차
   start : 2019-11-17 (11:17)
   end : 2019-11-17 (23:10)

   ### MVP가 아닌 MVVM을 사용한 이유?

   1. View와 Presenter가 강하게 묶여있어 서로 계속하여 호출하는 형태가 불편하다.

   2. 1:1관계를 깨고 1:n관계를 가지고자 MVVM을 사용했다.
      - Dialog,Activity : ViewModel
      - 관련 데이터를 관리하기 더 쉽다.
      - 보일러 플레이트 코드가 발생하지 않는다.
      
   3. DataBinding에 관련하여 강력하다.

   ### View로 Binding하기
   1. 평소 ViewModel에서 View로 Binding 하는 코드만 작성해봄 (개요)
   2. 반대로 View에서 ViewModel로 Inversion Binding에 개념이 헷갈렸음 (문제발생)
   3. 수정 할 수 없는 LiveData를 Inversion 시키려 하니 발생한 에러였음 (문제인지)
   4. 아래와 같이 해결함(문제해결)
   ```kotlin
   //Email
       val emailText = MutableLiveData<String>("")
   ```

   ```xml
   <EditText
   android:text="@={vm.emailText}"
   />
   ```


   # 2일차
   start : 2019-11-18 (09:49)

   end : 2019-11-15 (xx:xx)

   ### Room과 JsonArray

   1. JsonArray를 RoomDataBase에 넣는방법을 고민하게됨(개요)
   2. SQLite에서 JsonArray형태를 지원해주지 않음 (문제발생)
   3. TypeConverter를 만들어 문제를 해결 할 수 있음(문제인지)
   4. 아래와 같이 해결함(문제해결)

   ```kotlin
   class DataConverterUtil {
       @TypeConverter
       fun fromJsonArray(data: String): JsonArray? {
           return Gson().fromJson(data, JsonArray::class.java)
       }
   
       @TypeConverter
       fun toJsonArray(data: JsonArray): String? {
           return Gson().toJson(data)
       }
   }
   ```

   ### Repository 분리

   - 앱 시작시 로컬에 데이터가 존재한다면 가져옴

   ```kotlin
   override fun onCreate(savedInstanceState:Bundle?){
       super.onCreate(savedInstanceState)
       with(binding){
           vm = viewModel.apply{
               getUserData() //초기화 하면서 실행
           }
       }
   }
   ```

   ```kotlin
   class RepositoryImpl(private val remote: RemoteDataSource, private val local: LocalDataSource) :
       Repository {
       override fun requestSignUp(entity: UserEntity): Single<String> {
           // 서버 요청에 성공한 경우만 로컬에 저장
           return remote.requestSignUp(entity).doOnSuccess { local.saveUserInfo(entity) }
       }
   		
       override fun getUserInfo(): Single<UserEntity> {
           return local.getUserInfo()//로컬에서 가져오는 로직
       }
   }
   ```



## 현재 문제점

```kotlin
password.addTextChangedListener {
                    checkPasswordValidation(password.text.toString(), password2.text.toString())
                }
                password2.addTextChangedListener {
                    checkPasswordValidation(password.text.toString(), password2.text.toString())
                }
```

- password 관련 Invalidation이 너무 비효율적이다. //시간 남으면 수정하기