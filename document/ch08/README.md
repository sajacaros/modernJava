### [CHAPTER 8 컬렉션 API 개선](https://livebook.manning.com/book/modern-java-in-action/chapter-8/)
* 이장의 목표
    - 컬렉션 팩토리 사용하기
    - 리스트 및 집합과 사용할 새로운 관용 패턴 배우기
    - 맵과 사용할 새로운 관용 패턴 배우기
    
#### 8.1 컬렉션 팩토리
* 간단한 리스트 만들기
``` 
List<String> friends = new ArrayList<>();
friends.add("Raphael");
friends.add("Olivia");
friends.add("Thibaut");
```
* Arrays.asList 이용
``` 
List<String> friends
   = Arrays.asList("Raphael", "Olivia", "Thibaut");
```
* 갱신 및 요소 추가
``` 
List<String> friends = Arrays.asList("Raphael", "Olivia");
friends.set(0, "Richard");
friends.add("Thibaut"); // UnsupportedOperationException 발생
```
* 집합 만들기
    - HashSet 생성자 이용
    ``` 
    Set<String> friends "
       = new HashSet<>(Arrays.asList("Raphael", "Olivia", Thibaut"));
    ```
    - Stream api 이용
    ```
    Set<String> friends
       = Stream.of("Raphael", "Olivia", "Thibaut")
               .collect(Collectors.toSet()); 
    ```
##### 8.1.1 리스트 팩토리
* List.of 활용
``` 
List<String> friends = List.of("Raphael", "Olivia", "Thibaut");
```
* 갱신 및 요소 추가
``` 
List<String> friends = List.of("Raphael", "Olivia", "Thibaut");
friends.set(0, "Richard"); // UnsupportedOperationException 발생
friends.add("Chih-Chun"); // UnsupportedOperationException 발생
```
* 팩토리 메서드를 사용할 수 있는 상황이면 생성자나 Stream api보단 팩토리 메서드의 이용 추천
##### 8.1.2 집합 팩토리
* Set.of 활용
``` 
Set<String> friends = Set.of("Raphael", "Olivia", "Olivia");
```
##### 8.1.3 맵 팩토리
* Immutable Map 초기화
    - Map.of 팩토리 메서드
    ```
    Map<String, Integer> ageOfFriends
       = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26); 
    ```
    - Map.ofEntries 팩토리 메서드
        - Map.Entry<K, V> 객체를 가변 인수로 받음
        ```
        Map<String, Integer> ageOfFriends
               = Map.ofEntries(Map.entry("Raphael", 30),
                               Map.entry("Olivia", 25),
                               Map.entry("Thibaut", 26)); 
        ```
    - 10개 이하일 경우에는 Map.of가 유용함
#### 8.2 리스트와 집합 처리
* List와 Set 인터페이스에 추가된 메서드
    - removeIf 메서드
    - replaceAll
    - sort
    - 기존 컬렉션을 바꿈
##### 8.2.1 removeIf 메서드
* iterator 예제
``` 
for (Transaction transaction : transactions) {
   if(Character.isDigit(transaction.getReferenceCode().charAt(0))) {
       transactions.remove(transaction); // ConcurrentModificationException 발생
   }
}
```
* 풀어쓰기
``` 
for (Iterator<Transaction> iterator = transactions.iterator();
     iterator.hasNext(); ) {
   Transaction transaction = iterator.next();
   if(Character.isDigit(transaction.getReferenceCode().charAt(0))) {
       transactions.remove(transaction);
   }
}
```
    - iterator 객체와 transactions 객체에서 내부 요소에 접근
* 해결 방법
``` 
for (Iterator<Transaction> iterator = transactions.iterator();
     iterator.hasNext(); ) {
   Transaction transaction = iterator.next();
   if(Character.isDigit(transaction.getReferenceCode().charAt(0))) {
       iterator.remove();
   }
}
```
* removeIf 메서드 이용하기
``` 
transactions.removeIf(transaction ->
     Character.isDigit(transaction.getReferenceCode().charAt(0)));
```
##### 8.2.2 replaceAll 메서드
* 요소 바꾸기
``` 
referenceCodes.stream() // a12,C14, b13
              .map(code -> 
                    Character.toUpperCase(code.charAt(0))+code.substring(1)
              )
              .collect(Collectors.toList())
              .forEach(System.out::println);
```
    - 기존 리스트가 새로운 리스트로 변환됨
* 요소 바꾸기
``` 
for (ListIterator<String> iterator = referenceCodes.listIterator();
     iterator.hasNext(); ) {
   String code = iterator.next();
   iterator.set(Character.toUpperCase(code.charAt(0)) + code.substring(1));
}
```
    - 복잡함
* 단순화 하기
``` 
referenceCodes.replaceAll(code -> 
    Character.toUpperCase(code.charAt(0))+code.substring(1)
);
```
#### 8.3 맵 처리
##### 8.3.1 forEach 메서드
* 요소 탐색
``` 
for(Map.Entry<String, Integer> entry: ageOfFriends.entrySet()) {
   String friend = entry.getKey();
   Integer age = entry.getValue();
   System.out.println(friend + " is " + age + " years old");
}
```
* forEach 메서드 활용
``` 
ageOfFriends.forEach((friend, age) -> System.out.println(friend + " is " +
     age + " years old"));
```
##### 8.3.2 정렬 메서드
* 값 또는 키를 기준으로 정렬
    - Entry.comparingByValue
    ```
    Map<String, String> favouriteMovies
           = Map.ofEntries(entry("Raphael", "Star Wars"),
           entry("Cristina", "Matrix"),
           entry("Olivia",
           "James Bond"));
    
    favouriteMovies
        .entrySet()
        .stream()
        .sorted(Entry.comparingByKey())
        .forEachOrdered(System.out::println); 
    ```
    - Entry.comparingByKey
##### 8.3.3 getOrDefault 메서드
* 요청 결과가 null인지 확인하기
```
Map<String, String> favouriteMovies
       = Map.ofEntries(entry("Raphael", "Star Wars"),
       entry("Olivia", "James Bond"));

System.out.println(favouriteMovies.getOrDefault("Olivia", "Matrix"));
System.out.println(favouriteMovies.getOrDefault("Thibaut", "Matrix"));
```
##### 8.3.4 계산 패턴
* 멥에 키가 존재 여부에 따른 동작 실행
    - computeIfAbsent
        - 제공된 키에 해당하는 값이 없으면 키를 이용해 새 값을 계산하고 맵에 추가
        ```
        String friend = "Raphael";
        List<String> movies = friendsToMovies.get(friend);
        if(movies == null) {
           movies = new ArrayList<>();
           friendsToMovies.put(friend, movies);
        }
        movies.add("Star Wars");
        
        System.out.println(friendsToMovies);
        ```
        - computeIfAbsent 메서드 이용
        ```
        friendsToMovies.computeIfAbsent("Raphael", name -> new ArrayList<>())
                    .add("Star Wars"); 
        ```
    - computeIfPresent
        - 제공된 키가 존재하면 새 값을 계산하고 맵에 추가
    - compute
        - 제공된 키로 새 값을 계산하고 맵에 저장
##### 8.3.5 삭제 패턴
* 삭제하기
``` 
String key = "Raphael";
String value = "Jack Reacher 2";
if (favouriteMovies.containsKey(key) &&
     Objects.equals(favouriteMovies.get(key), value)) {
   favouriteMovies.remove(key);
   return true;
}
else {
   return false;
}
```
* remove 이용
    - 키가 특정한 값과 연관되어있을 때만 항목 제거
    ``` 
    favouriteMovies.remove(key, value);
    ```
##### 8.3.6 교체 패턴
* replaceAll
    - BiFunction을 적용한 결과로 각 항목의 값을 교체
* replace
    - 키가 존재하면 맵의 값을 바꿈
``` 
Map<String, String> favouriteMovies = new HashMap<>();
favouriteMovies.put("Raphael", "Star Wars");
favouriteMovies.put("Olivia", "james bond");
favouriteMovies.replaceAll((friend, movie) -> movie.toUpperCase());
System.out.println(favouriteMovies);
```
##### 8.3.7 합침
* putAll 이용
``` 
Map<String, String> family = Map.ofEntries(
   entry("Teo", "Star Wars"), entry("Cristina", "James Bond"));
Map<String, String> friends = Map.ofEntries(
   entry("Raphael", "Star Wars"));
Map<String, String> everyone = new HashMap<>(family);
everyone.putAll(friends);
System.out.println(everyone);
```
    - 중복된 키가 있을경우 처리 방안 필요
* 문자열 합치기로 해결
``` 
Map<String, String> family = Map.ofEntries(
    entry("Teo", "Star Wars"), entry("Cristina", "James Bond"));
Map<String, String> friends = Map.ofEntries(
    entry("Raphael", "Star Wars"), entry("Cristina", "Matrix"));
Map<String, String> everyone = new HashMap<>(family);
friends.forEach((k, v) ->
   everyone.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
System.out.println(everyone);
```
* 초기화 검사
``` 
Map<String, Long> moviesToCount = new HashMap<>();
String movieName = "JamesBond";
long count = moviesToCount.get(movieName);
if(count == null) {
   moviesToCount.put(movieName, 1);
}
else {
   moviesToCount.put(moviename, count + 1);
}
```
* merge이용
``` 
moviesToCount.merge(movieName, 1L, (key, count) -> count + 1L);
```
#### 8.4 개선된 ConcurrentHashMap
##### 8.4.1 리듀스와 검색
* ConcurrentHashMap의 새로운 연산
    - forEach
    - reduce
    - search
* 지원 형태
    - 키, 값으로 연산(forEach, reduce, search)
    - 키로 연산(forEachKey, reduceKeys, searchKeys)
    - 값으로 연산(forEachValue, reduceValues, searchValues)
    - Map.Entry 객체로 연산(forEachEntry, reduceEntries, searchEntries)
```
ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
long parallelismThreshold = 1;
Optional<Integer> maxValue =
Optional.ofNullable(map.reduceValues(parallelismThreshold, Long::max));
```
##### 8.4.2 계수(Counting)
* mappingCount 메서드 제공
##### 8.4.3 집합뷰
* 집합류로 반환하는 keySet 메서드 제공
#### 8.5 마치며