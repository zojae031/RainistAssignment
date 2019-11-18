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

   end : 2019-11-15 (22:25)

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

- primary_key AutoIncrement 생성하기 ``checkSignUpValidation()``
- Ui 이쁘게 꾸미기
- 주민등록번호 Validation 고치기
- TestCode 작성해보기

<hr>

# 3일차
start : 2019-11-19 (xx:xx)
end : 2019-11-19 (xx:xx)



# 다음 항목들을 반영해 주세요.


#### 1.  각 입력 필드 별로 validation 적용 ``OK``
#### 2. 입력 필드는 Material EditText ``OK``
#### 3. 사용회원가입 요청을 보낸 후 에러케이스에 대한 핸들링 ``OK``
#### 4. 아키텍쳐 혹은 패턴 적용 ``MVVM``

(우대사항 : 사용자 UX 고려)  ``성별, Font, 색상, Visibility, 로고``



####  A - 이메일 :이메일 형식이 맞는지 validation 체크 해주세요. ``OK``
(특수문자(@,.) 사용 및 사용 순서 등으로)



####  B - 패스워드 :(필수) 실시간으로 암호가 안전한지 판단하고 보여주기 ``부족``	

(특수문자, 숫자, 영문자 조합 여부로 판단)	

(조건이 추가될때마다 안정성이 올라갑니다)

(선택-1) 동일한 숫자 세번 반복 되면 막기

(선택-2) 연속하는 숫자/글자(abc, 123, ...) 막기



#### C - 이름 :10글자가 넘지 않도록 validation 체크해주세요.``OK``



#### D - 주민등록번호 :최대한 틀린 주민등록번호를 막을 수 있는 방법 validation 체크해주세요.

``TODO 앞자리 0으로 시작 체크, 하이푼 - 자동 붙이기``



#### E - 성별 :선택가능하게 구현해주세요. ``자동입력``



#### F - 약관동의 :4개의 CheckBox로 구현해주세요. ``OK``

1. 전체동의 CheckBox

2. 이용약관동의 CheckBox

3. 제3자이용약관동의 CheckBox

4. 마케팅 수신동의 CheckBox(선택)

   

   전체동의와 나머지 체크박스는 서로 연동되어야 하고

   마케팅 수신동의 CheckBox는 선택사항으로 체크되지 않더라도 진행 가능해야 합니다.



#### G - 회원가입 버튼 :버튼을 눌렀을때 빈 입력필드가 있는지 validation 해야합니다. ``OK``

패스워드 관련 선택사항을 구현하셨다면 회원가입 버튼을 눌렀을때 validation이 진행되야 합니다.



#### H - 에러케이스 핸들링 :회원가입 버튼을 눌렀을 경우 회원가입 서버 api 통신을 요청 하는 로직을 구현해주세요.

#### (실제 api 호출이 아닌 임의의 entity를 return하도록 구현해주세요.) ``OK``

그리고 해당 요청시 http error 발생시 에러코드가 401일 경우 이미가입된 회원, 404일 경우 알 수 없는 오류라고 핸들링 하는 코드를 구현해주세요.``OK``



#### 추가 구현사항 

1. mock 서버 요청 성공시 ‘User’ entity를 return 하고 해당 entity를 local에 저장하는 코드를 구현해주세요.``OK``
2. 모든 비동기 동작을 rxJava를 활용해서 구현해주세요.``OK``
3. ‘User’ entity의 필드는 입력필드를 모두 포함하고 추가로 ‘id: String’로 구현해주세요.``OK``
4. Local 에 저장된 값이 있을 경우 앱을 실행 했을때 자동으로 입력필드를 채워주세요.``OK``
5. 테스트 코드를 작성해 주세요.``TODO``

