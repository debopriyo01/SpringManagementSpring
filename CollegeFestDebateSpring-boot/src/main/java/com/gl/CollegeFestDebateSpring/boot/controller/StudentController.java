package com.gl.CollegeFestDebateSpring.boot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl.CollegeFestDebateSpring.boot.entity.Student;
import com.gl.CollegeFestDebateSpring.boot.service.StudentServiceImpl;

@Controller
@RequestMapping("/students")
public class StudentController {
	@Autowired
	StudentServiceImpl studentServiceImpl;
	
	@RequestMapping("/list")
	public String StudentList(Model model)
	{
		List<Student> students=studentServiceImpl.getAllStudents();
		model.addAttribute("students", students);
		return "list-students";
	}
	

	@RequestMapping("/showFormForAdd")
	public String addStudent(Model model)
	{
		Student student=new Student();
		model.addAttribute("student",student);
		return "student-form";
	}
	
	@RequestMapping("/save")
	public String saveStudent(@RequestParam("id") int id,
			@RequestParam("name") String name,
			@RequestParam("department") String department,
			@RequestParam("country") String country)
	{	
		if (id != 0) {
			Optional<Student> student;
			student = studentServiceImpl.findById(id);
			student.get().setName(name);
			student.get().setDepartment(department);
			student.get().setCountry(country);
			studentServiceImpl.save(student.get());
		} else
		{
			Student student = new Student(name, department, country);
			studentServiceImpl.save(student);
		}
		return "redirect:/students/list";
	}
	
	
	@RequestMapping("/update")
	public String updateStudent(@RequestParam("studentId") int id,Model model)
	{
		Optional<Student> student = studentServiceImpl.findById(id);
		model.addAttribute("student", student.get());
		return "student-update-form";
	}
	
	
	@RequestMapping("/delete")
	public String deleteStudent(@RequestParam("studentId") int id,Model model)
	{
		Optional<Student> student = studentServiceImpl.findById(id);
		studentServiceImpl.delete(student.get());
		return "redirect:/students/list";
	}
	@RequestMapping("/403")
	public String noAccess()
	{
		return "error";
	}

}
