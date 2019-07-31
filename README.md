### 스프링 기반 REST-API 개발하기
스프링 부트를 이용하여 이벤트 REST-API 개발하기

## REST API 
- API 
    - Application Programming Interface 
- REST 
    - REpresentational State Transfer 
```
● 인터넷 상의 시스템 간의 상호 운용성(interoperability)을 제공하는 방법중 하나 
● 시스템 제각각의 독립적인 독립적인 진화진화를 보장하기 위한 방법 
● REST API: REST 아키텍처 스타일을 따르는 API 
```

- REST 아키텍처 스타일 (발표 영상 11분) 
```
● Client-Server 
● Stateless 
● Cache 
● Uniform Interface 
● Layered System 
● Code-On-Demand (optional) 
```

## “Event” REST API 
- 이벤트 등록, 조회 및 수정 API 

- GET /api/events 
```
이벤트 목록 조회 REST API (로그인 안 한 상태) 
- 응답에 보여줘야 할 데이터 
- 이벤트 목록 
- 링크 
    - self 
    - profile: 이벤트 목록 조회 API 문서문서로 링크 
    - get-an-event: 이벤트 하나 조회하는 API 링크 
    - next: 다음 페이지 (optional) 
    - prev: 이전 페이지 (optional) 

- 문서? 
    - 스프링 REST Docs로 만들 예정 

이벤트 목록 조회 REST API (로그인 한 상태) 
- 응답에 보여줘야 할 데이터 
- 이벤트 목록 
- 링크 
    - self 
    - profile: 이벤트 목록 조회 API 문서문서로 링크 
    - get-an-event: 이벤트 하나 조회하는 API 링크 
    - create-new-event: 이벤트를 이벤트를 생성할 생성할 수 있는있는 API 링크링크 
    - next: 다음 페이지 (optional) 
    - prev: 이전 페이지 (optional) 

- 로그인 한 상태???? (stateless라며..) 
- 아니, 사실은 Bearer 헤더에 유효한 AccessToken이 들어있는 경우! 
```
- POST /api/events 
    - 이벤트 생성 

- GET /api/events/{id} 
    - 이벤트 하나 조회 

- PUT /api/events/{id}
    - 이벤트 수정 

## Events API 사용 예제 
1. (토큰 없이) 이벤트 목록 조회 
    - create 안 보임 
2. access token 발급 받기 (A 사용자 로그인)
3. (유효한 A 토큰 가지고) 이벤트 목록 조회 
    - create event 보임 
4. (유효한 A 토큰 가지고) 이벤트 만들기 
5. (토큰 없이) 이벤트 조회 
    - update 링크 안 보임 6. (유효한 A 토큰 가지고) 이벤트 조회 
    - update 링크 보임 
7. access token 발급 받기 (B 사용자 로그인) 
8. (유효한 B 토큰 가지고) 이벤트 조회 
    - update 안 보임 

## REST API 테스트 클라이언트 애플리케이션 
- 크롬 플러그인 
    - Restlet 
- 애플리케이션 
    - Postman 

## 스프링 부트 프로젝트 만들기 

### 추가할 의존성 
- Web 
- JPA 
- HATEOAS 
- REST Docs 
- H2 
- PostgreSQL 
- Lombok 

- 자바 버전 11로 시작 
    - 자바는 여전히 무료다. 

### 스프링 부트 핵심 원리 
- 의존성 설정 (pom.xml) 
- 자동 설정 (@EnableAutoConfiguration) 
- 내장 웹 서버 (의존성과 자동 설정의 일부) 
- 독립적으로 실행 가능한 JAR (pom.xml의 플러그인) 


## Event 생성 API 구현: 비즈니스 로직 
Event 생성 API 
- 다음의 입력 값을 받는다. 
    - name 
    - description 
    - beginEnrollmentDateTime 
    - closeEnrollmentDateTime 
    - beginEventDateTime 
    - endEventDateTime 
    - location (optional) 이게 없으면 온라인 모임 
    - basePrice (optional) 
    - maxPrice (optional) 
    - limitOfEnrollment 
    - basePrice와 maxPrice 경우의 수와 각각의 로직 
    - basePrice maxPrice 
        - 0 100 선착순 등록 
        - 0 0 무료 
        - 100 0 무제한 경매 (높은 금액 낸 사람이 등록) 
        - 100 200 제한가 선착순 등록 
        - 처음 부터 200을 낸 사람은 선 등록. 
        - 100을 내고 등록할 수 있으나 더 많이 낸 사람에 의해 밀려날 수 있음. 

    - 결과값 ○ id 
    - name 
    - ... 
    - eventStatus: DRAFT, PUBLISHED, ENROLLMENT_STARTED, ... 
    - offline 
    - free 
    - _links ■ profile (for the self-descriptive message) 
    - self 
    - publish 
    - ... 

## Event 생성 API 구현: Event 도메인 구현 
```java
public class Event { 
    private String name; 
    private String description; 
    private LocalDateTime beginEnrollmentDateTime; 
    private LocalDateTime closeEnrollmentDateTime; 
    private LocalDateTime beginEventDateTime; 
    private LocalDateTime endEventDateTime; 
    private String location; // (optional) 이게 없으면 온라인 모임 private int basePrice; // (optional) private int maxPrice; // (optional) private int limitOfEnrollment; 
} 
```
추가 필드
```java
private Integer id; 
private boolean offline; 
private boolean free; 
private EventStatus eventStatus = EventStatus.DRAFT; 
```

- EventStatus 이늄 추가 
```java
public enum EventStatus { 
    DRAFT, PUBLISHED, BEGAN_ENROLLMEND, CLOSED_ENROLLMENT, STARTED, ENDED 
}
```

- 롬복 애노테이션 추가 
```java
@Getter @Setter @EqualsAndHashCode(of = "id") @Builder @NoArgsConstructor @AllArgsConstructor 
public class Event {
```

- 왜 @EqualsAndHasCode에서 of를 사용하는가 
- 왜 @Builder를 사용할 때 @AllArgsConstructor가 필요한가 
- @Data를 쓰지 않는 이유 
- 애노테이션 줄일 수 없나 

## Event 생성 API 구현: 테스트 만들자 

### 스프링 부트 슬라이스 테스트 
- @WebMvcTest 
    - MockMvc 빈을 자동 설정 해준다. 따라서 그냥 가져와서 쓰면 됨. 
    - 웹 관련 빈만 등록해 준다. (슬라이스) 

- MockMvc 
    - 스프링 MVC 테스트 핵심 클래스 
    - 웹 서버를 띄우지 않고도 스프링 MVC (DispatcherServlet)가 요청을 처리하는 과정을 확인할 수 있기 때문에 컨트롤러 테스트용으로 자주 쓰임. 

- 테스트 할 것 
- 입력값들을 전달하면 JSON 응답으로 201이 나오는지 확인. 
- Location 헤더에 생성된 이벤트를 조회할 수 있는 URI 담겨 있는지 확인. 
- id는 DB에 들어갈 때 자동생성된 값으로 나오는지 확인 
- 입력값으로 누가 id나 eventStatus, offline, free 이런 데이터까지 같이 주면? 
- Bad_Request로 응답 vs 받기로 받기로 한 값 이외는 이외는 무시무시 
- 입력 데이터가 이상한 경우 Bad_Request로 응답 
- 입력값이 이상한 경우 에러 
- 비즈니스 로직으로 검사할 수 있는 에러 
- 에러 응답 메시지에 에러에 대한 정보가 있어야 한다. 
- 비즈니스 로직 적용 됐는지 응답 메시지 확인 
- offline과 free 값 확인 
- 응답에 HATEOA와 profile 관련 링크가 있는지 확인. 
- self (view) 
- update (만든 사람은 수정할 수 있으니까) 
- events (목록으로 가는 링크) 
- API 문서 만들기 
- 요청 문서화 
- 응답 문서화 
- 링크 문서화
- profile 링크 추가 


## Event 생성 API 구현: 201 응답 받기 
```
@RestController 
- @ResponseBody를 모든 메소드에 적용한 것과 동일하다. 

ResponseEntity를 사용하는 이유 
- 응답 코드, 헤더, 본문 모두 다루기 편한 API 

Location URI 만들기 
- HATEOS가 제공하는 linkTo(), methodOn() 사용 

객체를 JSON으로 변환 
- ObjectMapper 사용 
```
- 테스트 할 것 
    - 입력값들을 전달하면 JSON 응답으로 201이 나오는지 확인. 
    - Location 헤더에 생성된 이벤트를 조회할 수 있는 URI 담겨 있는지 확인. 
    - id는 DB에 들어갈 때 자동생성된 값으로 나오는지 확인 

## Event 생성 API 구현: EventRepository 구현 

### 스프링 데이터 JPA 
- JpaRepository 상속 받아 만들기 
    - Enum을 JPA 맵핑시 주의할 것 
        - @Enumerated(EnumType.STRING) 


@MockBean 
- Mockito를 사용해서 mock 객체를 만들고 빈으로 등록해 줌. 
- (주의) 기존 빈을 테스트용 빈이 대체 한다. 

테스트 할 것 
- 입력값들을 전달하면 JSON 응답으로 201이 나오는지 확인. 
- Location 헤더에 생성된 이벤트를 조회할 수 있는 URI 담겨 있는지 확인. 
- id는 DB에 들어갈 때 자동생성된 값으로 나오는지 확인 

## Event 생성 API 구현: 입력값 제한하기 

### 입력값 제한 
- id 또는 입력 받은 데이터로 계산해야 하는 값들은 입력을 받지 않아야 한다. 
- EventDto 적용 
- DTO -> 도메인 객체로 값 복사 
- ModelMapper 
```
<dependency> 
    <groupId>org.modelmapper</groupId> 
    <artifactId>modelmapper</artifactId> 
    <version>2.3.1</version> 
</dependency> 
```

###  통합 테스트로 전환 
- @WebMvcTest 빼고 다음 애노테이션 추가 
- @SpringBootTest 
- @AutoConfigureMockMvc 
- Repository @MockBean 코드 제거 

### 테스트 할 것 
- 입력값으로 누가 id나 eventStatus, offline, free 이런 데이터까지 같이 주면? 
- Bad_Request로 응답 vs 받기로 받기로 한 값 이외는 이외는 무시무시 