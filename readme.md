# 기술적인 구조도

## 호출 흐름
MainActivity (some Calculator Fragments) -> Services.calc (e.g.)
-> ReportActivity (some Fragments) -> Services.get (e.g.) 결과값들을 Services 에서 Get 해서 가져옴.

정리하자면,
* 호출 액티비티 -> 결과 액티비티
* 중간에서 Services 가 있음.


이런 구조로 가게 된 이유
* 매번 '결과 액티비티' 에서 무언가를 new 하는 과정을 생략시키고 싶기 때문. (결과 액티비티는 결과값들을 단순히 layout 으로 긁어와서 보여주는 역할만 하기 때문)
* '계산 액티비티'에서 new 하는 과정을 생략시키고 싶기 때문. options 값만 바뀔 뿐이기 때문.
* HTTP 과정을 흉내내고 싶었기 때문.


# 코드 구조도
메인 액티비티
- '원금 균등 계산기' 프레그먼트
- '원리금 균등 계산기' 프레그먼트


레포트 액티비티
- '종합 레포트' 프레그먼트
- '스케쥴 레포트' 프레그먼트