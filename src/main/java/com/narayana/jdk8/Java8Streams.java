package com.narayana.jdk8;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Java8Streams {
    public static void main(String[] args) {

        String str = "abc123xyz"; // use java 8 and print abcxyz
        Optional<String> reduce = Stream.of(str).flatMap(x -> Stream.of(x.split("")))
                .filter(x -> !Character.isDigit(x.charAt(0))).reduce((res, x) -> res + x);
        System.out.println("reduce : " + reduce.get());
        //TODO User POJO
        // reduce
        List<User> usersList = Stream.of(new User(10, "abc"), new User(20, "xyz"), new User(40, "pqr"), new User(10, "mno")
        ,new User(50, "Thisislongestname"), new User(50, "Thisisshortname")).toList();
        Integer ageSum = usersList.stream().reduce(0, (result, user) -> result + user.getAge(), Integer::sum); // 30
        System.out.println(" ageSum : "+ ageSum);
        Map<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        //TODO
        Optional<User> reduce1 = usersList.stream().reduce(
                (ele, user) -> ele.getName().length() > user.getName().length() ? ele : user);
        System.out.println("max length name object : " + (reduce1.isPresent() ? reduce1.get() : ""));
        Optional<String> reduce2 = usersList.stream().map(u -> u.getName())
                .reduce(BinaryOperator.maxBy(Comparator.comparing(String::length)));
        System.out.println("max length name : " + (reduce2.isPresent() ? reduce1.get() : ""));
        Set<String> namesTreeSet = usersList.stream().map(x -> x.getName()).collect(Collectors.toCollection(TreeSet::new));
        System.out.println(" namesTreeSet : " + namesTreeSet);
        List<String> namesArrayList = usersList.stream().map(x -> x.getName()).collect(Collectors.toCollection(ArrayList::new));
        System.out.println(" namesArrayList : " + namesArrayList);
        //TODO map and reduce to generate output -> k1=v1&k2=v2&k3=v3
        Optional<String> concatStr = map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).reduce((result, val) -> result +"&"+ val);
        String concatStr2 = map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce("$", (result, val) -> result +"&"+ val);
        System.out.println("concatStr : " + (concatStr.isPresent() ? concatStr.get() : "no data")); // k1=v1&k2=v2&k3=v3
        System.out.println("concatStr2 : " + concatStr2); // $&k1=v1&k2=v2&k3=v3
        //TODO reduce with method reference
        String concatStr3 = Stream.of("a", "b", "c").reduce("", String::concat);
        System.out.println("concatStr3 : " + concatStr3);
        //TODO GroupBy
        Map<Integer, Map<String, List<User>>> userByAgeByName = usersList.stream().collect(Collectors.groupingBy(User::getAge, Collectors.groupingBy(User::getName)));
        System.out.println("userByAgeByName : "+ userByAgeByName);
        //TODO GroupBy age, get unique names for the same age group
        Map<Integer, Set<String>> userByAgeNamesSet = usersList.stream().collect(Collectors.groupingBy(User::getAge, Collectors.mapping(User::getName, Collectors.toSet())));
        System.out.println("userByAgeNamesSet : "+ userByAgeNamesSet);
        //TODO predicate 1
        List<User> agegt10 = usersList.stream().filter(u -> {
            if (u.getAge() > 10) {
                return true;
            }
            return false;
        })//.toList().forEach(x -> System.out.println(x));
        .collect(Collectors.toList());//.forEach(x -> System.out.println(x.getAge()));
        System.out.println(" agegt10 " + agegt10);
        //TODO predicate 2
        List<User> agegt10_2 = usersList.stream().filter(u -> u.getAge() > 10).toList();
        System.out.println(" agegt10_2 " + agegt10_2);
        //TODO predicate 3
        List<User> agegt10_3 = usersList.stream().filter(predicateMethod()).toList();
        System.out.println(" agegt10_3 " + agegt10_3);
        //TODO BiPredicate 4
        List<User> agegt10_4 = usersList.stream().filter(u -> biPredicateMethod().test(u.getAge(), u.getName())).toList();
        System.out.println(" agegt10_4 " + agegt10_4);
        //TODO Aggregate
        List<Integer> integerList = Stream.of(1, 2, 3, 4, 5, 6).toList();
        int sum = integerList.stream().mapToInt(x -> x).sum();
        long count = integerList.stream().mapToInt(x -> x).count();
        OptionalDouble average = integerList.stream().mapToInt(x -> x).average();
        OptionalInt min = integerList.stream().mapToInt(x -> x).min();
        OptionalInt max = integerList.stream().mapToInt(x -> x).max();
        System.out.println("sum : " + sum + ", count : " + count +", avg : " + average + ", min : " +min + ", max : " + max);
        //TODO flatMap
        List<Integer> integerList2 = Stream.of(1, 2, 3, 4, 5, 6).toList();
        List<List<Integer>> combsList = integerList2.stream().flatMap(x1 ->
                integerList2.stream().flatMap(x2 -> (x1 + x2 == 5 ? Stream.of(Arrays.asList(x1, x2)) : Stream.empty()))
        ).toList();//.collect(Collectors.toList());
        System.out.println("sum=5 combinations of 2 ints : " + combsList);
        //TODO count the words in text line
        List<String> strList = Stream.of("This is Narayana", "This is flatMap testing", "this is test2").toList();
        Map<String, Long> wordCount = strList.stream().flatMap(text -> Stream.of(text.split(" ")))
                .collect(Collectors.groupingBy(String::trim, Collectors.counting()));
        System.out.println("wordCount : " + wordCount);
        Map<String, Long> wordCount2 = strList.stream().flatMap(text -> Stream.of(text.split(" ")))
                .collect(Collectors.groupingBy(String::trim, Collectors.reducing(0L, e -> 1L, Long::sum))
                );
        System.out.println("wordCount2 : " + wordCount2);
        //TODO joining
        String joining = usersList.stream().map(u -> u.getName()).collect(Collectors.joining(", "));
        System.out.println(" uses joining : " + joining);
        List<Book> booksList = Arrays.asList(new Book("Balaguruswamy", "Programming with Java"),
                new Book("Balaguruswamy", "Programming in ANSI C"),
                new Book("Balaguruswamy", "Programming in C#"),
                new Book("Martin Odersky", "Programming in Scala"));
        //TODO groupby and mapping
        Map<String, Set<String>> booksByAuthor = booksList.stream()
                .collect(Collectors.groupingBy(Book::author, Collectors.mapping(Book::book, Collectors.toSet())));
        System.out.println("booksByAuthor : " + booksByAuthor);
        //TODO Users by age partition
        Map<Boolean, List<User>> usersByAgePartition = usersList.stream().collect(Collectors.partitioningBy(u -> u.getAge() >= 20 && u.getAge() <= 40));
        Map<Boolean, List<User>> ageGt20 = usersList.stream().collect(Collectors.partitioningBy(u -> u.getAge() >= 20));
        Map<Boolean, List<User>> ageGTE20LTE40FilterNameStartswithX = usersList.stream().collect(Collectors.partitioningBy(u -> u.getAge() >= 20 && u.getAge() <= 40,
                Collectors.filtering(u -> u.getName().toLowerCase().startsWith("x"), Collectors.toList())));
        System.out.println("usersByAgePartition : " + usersByAgePartition);
        System.out.println("usersByAgePartition ageGTE20 : " + ageGt20);
        System.out.println("usersByAgePartition ageGTE20LTE40FilterNameStartswithX : " + ageGTE20LTE40FilterNameStartswithX);
        //TODO For example, given a stream of User, to calculate the longest name of residents in each age:
        Map<Integer, String> longestNameByAgeGroup = usersList.stream()
                .collect(Collectors.groupingBy(User::getAge, Collectors.reducing("", User::getName, BinaryOperator.maxBy(Comparator.comparing(String::length)))));
        System.out.println("longestNameByAgeGroup : " + longestNameByAgeGroup);
        //For example, given a stream of Person, to calculate the longest last name of residents in each city:
//        Comparator<String> byLength = Comparator.comparing(String::length);
//        Map<City, String> longestLastNameByCity =
//                people.stream().collect(groupingBy(Person::getCity, reducing("",Person::getLastName, BinaryOperator.maxBy(byLength))));
        boolean allMatch = usersList.stream().allMatch(x -> x.getName().toLowerCase().startsWith("this"));
        boolean anyMatch = usersList.stream().anyMatch(x -> x.getName().toLowerCase().startsWith("this"));
        boolean noneMatch = usersList.stream().noneMatch(x -> x.getName().toLowerCase().startsWith("this"));
        System.out.println("allMatch " +  allMatch + ", anyMatch "  + anyMatch + ", noneMatch " + noneMatch);
        Map<Integer, List<String>> usersByAgeGroup = usersList.stream()
                .collect(Collectors.groupingBy(User::getAge, Collectors.mapping(u -> u.getName(), Collectors.toList())));
        System.out.println("usersByAgeGroup : " + usersByAgeGroup);



    }
    private static Predicate<? super User> predicateMethod() {
        return x -> x.getAge() > 10;
    }
    private static BiPredicate<Integer, String> biPredicateMethod() {
        return (x, y) -> x > 10 && y.equalsIgnoreCase("pqr");
    }
}
