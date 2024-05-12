package com.codewiz.java22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class StreamGatherers {

    public static void main(String[] args) {
        record Employee(String name, int age, String department) {}
        List<Employee> employees = Arrays.asList(
                new Employee("John", 30, "HR"),
                new Employee("Alice", 25, "Engineering"),
                new Employee("Bob", 35, "Finance"),
                new Employee("Mary", 28, "Engineering"),
                new Employee("David", 40, "HR"),
                new Employee("John", 32, "Engineering"),
                new Employee("Ramesh", 31, "Engineering"),
                new Employee("Jen", 26, "Engineering")
        );

        List<String> engineeringEmployeeNames = employees.stream()
                .filter(employee -> employee.department().equals("Engineering"))
                .map(Employee::name)
                .toList();
        System.out.println(engineeringEmployeeNames);//[Alice, Mary, John, Ramesh, Jen]

        var fixedWindowGatherer = getFixedWindowGatherer(2);

        var employeePairList = employees.stream()
                .filter(employee -> employee.department().equals("Engineering"))
                .gather(map(Employee::name))
                .gather(fixedWindowGatherer)
                .toList();


//        var employeePairList = employees.stream()
//                .filter(employee -> employee.department().equals("Engineering"))
//                .gather(map(Employee::name))
//                .gather(Gatherers.windowFixed(2))
//                .toList();
        System.out.println(employeePairList);//[[Alice, Mary], [John, Ramesh], [Jen]]


        Stream.of("a", "b", "c", "d", "e", "f", "g", "h")
                .gather(Gatherers.fold(()->"", (joined, element) -> STR."\{joined}\{element}"))
                .forEach(System.out::println);//abcdefgh


        Stream.of("a", "b", "c", "d", "e", "f", "g", "h")
                .gather(Gatherers.scan(()->"", (joined, element) -> STR."\{joined}\{element}"))
                .forEach(System.out::println);//a, ab, abc, abcd, abcde, abcdef, abcdefg, abcdefgh


        var employeePairListSliding = employees.stream()
                .filter(employee -> employee.department().equals("Engineering"))
                .gather(map(Employee::name))
                .gather(Gatherers.windowSliding(2))
                .toList();
        System.out.println(employeePairListSliding);//[[Alice, Mary], [Mary, John], [John, Ramesh], [Ramesh, Jen]]


    }

    private static <T,R> Gatherer<T,?,R> map(Function<T,R> mapper) {
        Gatherer.Integrator<Void,T,R> integrator = (_,element,downStream) -> {
            R newElement = mapper.apply(element);
            downStream.push(newElement);
            return true;
        };
        return Gatherer.of(integrator);
    }

    private static <T> Gatherer<T,List<T>,List<T>> getFixedWindowGatherer(int limit) {
        Supplier<List<T>> initializer = ArrayList::new;
        Gatherer.Integrator<List<T>,T,List<T>> integrator = (state, element, downstream) -> {
            state.add(element);
            if(state.size() == limit){
                var group = List.copyOf(state);
                downstream.push(group);
                state.clear();
            }
            return true;
        };
        BiConsumer<List<T>, Gatherer.Downstream<? super List<T>>> finisher =
                (state,downStream) ->{
                    if(!state.isEmpty()){
                        downStream.push(List.copyOf(state));
                    }
                };
        return Gatherer.ofSequential(initializer,integrator,finisher);
    }


}