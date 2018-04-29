package com.dev.essentials.springbootessentials.endpoint;

import com.dev.essentials.springbootessentials.exception.ResourceNotFoundException;
import com.dev.essentials.springbootessentials.model.Student;
import com.dev.essentials.springbootessentials.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("students")
public class StudentEndpoint {

    private final StudentRepository studentDao;

	@Autowired
	public StudentEndpoint(StudentRepository studentDao) {
		this.studentDao = studentDao;
	}

    @GetMapping
	public ResponseEntity<?> listAll() {
		return new ResponseEntity<>(studentDao.findAll(), HttpStatus.OK);
	}

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        verifyStudentExists(id);
        Optional<Student> student = studentDao.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<?> findStudentsByName(@PathVariable String name){
        return new ResponseEntity<>(studentDao.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Student student){
        return new ResponseEntity<>(studentDao.save(student), HttpStatus.OK);
    }

    // Study REST idempotent

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        verifyStudentExists(id);
        studentDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student){
        verifyStudentExists(student.getId());
        studentDao.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyStudentExists(Long id){
        if(!studentDao.findById(id).isPresent()){
            throw new ResourceNotFoundException("Student not found for ID: " + id);
        }
    }
}
