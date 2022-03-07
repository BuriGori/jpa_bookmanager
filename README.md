# jpa_bookmanager

## jpa의 기본 Entity

1. 기본적으로 java class를 통해서 db에 맞는 객체를 생성할 수 있다.
2. lombok 라이브러리를 통해서 생성자에 대한 부분을 해결할 수 있다.
3. @Data, @NoArgsConstructor, @Entity를 추가해준다.
4. 오류가 발생할텐데 Entity에 해당하는 ID가 없기 때문이다.
5. @Id와 @GeneratedValue를 활용하여 자동적으로 증가하는 ID를 만들어준다!

## jpa의 기본 Repository

1. interface class로 db에 맞는 repo를 생성할 수 있다.
2. 생성한 interface에 JpaReopsitory< 생성한 Entity class, Entity의 Id 자료형>
3. 이렇게 작성하면 JPA의 database기능들을 사용할 수 있다.
