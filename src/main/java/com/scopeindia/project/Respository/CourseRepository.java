package com.scopeindia.project.Respository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.scopeindia.project.Model.Course;


public interface CourseRepository extends JpaRepository<Course, Long>  {
Course findByname(String name);
}
