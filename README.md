# RainistAssignment
Start : 2019-11-17 (11:17)



## MVP가 아닌 MVVM을 사용한 이유?

1. View와 Presenter가 강하게 묶여있어 서로 계속하여 호출하는 형태가 불편하다.
2. 1:1관계를 깨고 1:n관계를 가지고자 MVVM을 사용했다.
   - Dialog,Activity : ViewModel
   - 관련 데이터를 관리하기 더 쉽다.
   - 보일러 플레이트 코드가 발생하지 않는다.
3. DataBinding에 관련하여 강력하다.





# TODO LIST (11-18)

1. Permission Check해서 버튼 누르기
2. password 별표 보이게 하기
3. password 정규식 작성하기
4. 회원가입버튼 작성하기
5. Model 설계하기
6. App Logo 삽입하기
