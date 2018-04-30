package com.dev.essentials.springbootessentials.repository;

import com.dev.essentials.springbootessentials.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Samuel on 28/04/18.
 */
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    List<Student> findByNameIgnoreCaseContaining(String name);
}
