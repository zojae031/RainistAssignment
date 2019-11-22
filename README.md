# RainistAssignment


   # 1일차 (기본Ui, Validation, Architecture)
   start : 2019-11-17 (11:17)  
   end : 2019-11-17 (23:10)

   ### MVP가 아닌 MVVM을 사용한 이유?

   1. View와 Presenter가 강하게 묶여있어 서로 계속하여 호출하는 형태가 불편하다.
   2. 1:1관계를 깨고 1:n관계를 가지고자 MVVM을 사용했다.
      - Dialog,Activity : ViewModel
      - 관련 데이터를 관리하기 더 쉽다.
      - 보일러 플레이트 코드가 발생하지 않는다.
      - ViewModel이 View를 몰라도 된다.
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


   # 2일차 (Repository)
   start : 2019-11-18 (09:49)

   end : 2019-11-15 (23:11)

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



<hr>

# 3일차 (Ui)
start : 2019-11-19 (14:03)  
end : 2019-11-19 (22:44)

### Test Code

- 컨퍼런스에서 내용만 접해보다 처음 작성을 시도해보았는데 공부의 필요성을 느낌
- 모르고 대충 쓰는것보다 확실하게 알고 쓰는게 낫다는 생각에 과제에서 작성 포기

```kotlin
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
```



### UI 변경

![1574165887710](https://github.com/zojae031/RainistAssignment/blob/master/image.png?raw=true)




<hr>  

# 동작방식

1. Email : edgar3@naver.com 으로 가입시 401 발생
2. Email : recruit@rainist.com 으로 가입시 404 발생
3. 다른 이메일로 가입시 정상 가입

 ``Local 에 저장된 값이 있을 경우 앱을 실행 했을때 자동으로 입력필드를 채워주세요.``조건에 따라 AutoGenerate Primary Key를 추가.
 따라서, 데이터를 계속 넣으면 DB에 저장은 계속 되지만 SIngle을 사용하여 단일 Entity를 받아오기 떄문에 가장 처음에 삽입한 값만 나타남  
 
<hr>  

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



#### D - 주민등록번호 :최대한 틀린 주민등록번호를 막을 수 있는 방법 validation 체크해주세요. ``OK``



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

<hr>

## 피드백 내용
1. xml 파일에 어떻게 접근 할 것인가?
2. View에서 Viewmodel로 요청을 하는 상황인데 이것을 어떻게 더 간단하게 만들 수 있을까?
3. 데이터소스의 비즈니스 로직을 어떻게 분리할 수 있을까?
4. 모두선택 키의 비즈니스 로직의 변경
