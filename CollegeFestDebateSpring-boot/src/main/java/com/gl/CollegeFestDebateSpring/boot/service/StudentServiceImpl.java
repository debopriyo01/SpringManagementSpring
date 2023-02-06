package com.gl.CollegeFestDebateSpring.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.CollegeFestDebateSpring.boot.entity.Student;
import com.gl.CollegeFestDebateSpring.boot.repository.StudentRepository;
@Service
public class StudentServiceImpl{
	@Autowired
	private StudentRepository studentRepository;
	
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	public Optional<Student> findById(int id) {
		return studentRepository.findById(id);
	}
	
	public Student save(Student student) {
		return studentRepository.saveAndFlush(student);

	}
	
	public void delete(Student student) {
		studentRepository.delete(student);
	}

}
