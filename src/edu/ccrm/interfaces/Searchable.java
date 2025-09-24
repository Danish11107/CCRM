package edu.ccrm.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Generic searchable interface demonstrating default methods and functional interface usage.
 */
public interface Searchable<T> {
    Optional<T> findFirst(Predicate<T> predicate);

    default List<T> findAll(Predicate<T> predicate, List<T> source){
        return source.stream().filter(predicate).toList();
    }
}
