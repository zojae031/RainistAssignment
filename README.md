# RainistAssignment
Start : 2019-11-17 (11:17)



## MVP가 아닌 MVVM을 사용한 이유?

1. View와 Presenter가 강하게 묶여있어 서로 계속하여 호출하는 형태가 불편하다.
2. 1:1관계를 깨고 1:n관계를 가지고자 MVVM을 사용했다.
   - Dialog,Activity : ViewModel
   - 관련 데이터를 관리하기 더 쉽다.
   - 보일러 플레이트 코드가 발생하지 않는다.
3. DataBinding에 관련하여 강력하다.

#### 여기서 문제점
 - View가 ViewModel을 알고있다.
 - 코드분리 에러

end : 2019-11-17 (23:10)

# TODO LIST (11-18)
start : 2019-11-18 (09:49)
1. Permission Check해서 버튼 누르기 (o)
2. password 별표 보이게 하기
3. password 정규식 작성하기
4. 회원가입버튼 작성하기(o)
5. Model 설계하기
6. App Logo 삽입하기

## 어려웠던 점
1. Two way binding이 익숙하지 않아서 헤멨다. 아직도 동작방식에 대해 제대로 이해하지 못한 것 같다.
2. 
