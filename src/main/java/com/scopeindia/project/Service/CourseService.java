package com.scopeindia.project.Service;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import com.scopeindia.project.Model.Course;
import com.scopeindia.project.Respository.CourseRepository;

@Service
@EnableAutoConfiguration
public class CourseService {
	  private final CourseRepository courseRepository;

	    public CourseService(CourseRepository courseRepository) {
	        this.courseRepository = courseRepository;
	    }

	    public List<Course> getAllCourses() {
	        return courseRepository.findAll();
	    }
	}
