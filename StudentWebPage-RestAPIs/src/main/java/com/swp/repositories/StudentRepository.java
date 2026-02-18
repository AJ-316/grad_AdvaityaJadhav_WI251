package com.swp.repositories;

import com.swp.entities.Gender;
import com.swp.entities.Student;
import com.swp.entities.data.StandardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    int countAllBySchool_Name(String schoolName);

    int countAllByGenderAndStandard(Gender gender, Integer standard);

    List<Student> findAllByPercentageGreaterThanEqualOrderByPercentageDesc(Float percentage);
    List<Student> findAllByPercentageLessThanEqualOrderByPercentageDesc(Float percentage);

    @Query(
            """
            select sc.name as schoolName, COUNT(st.regNo) as strength
            from School sc
            left join Student st on st.school.regNo = sc.regNo and st.standard = ?1
            group by sc.name
            """
    )
    List<StandardData> countStudentByStandard(Integer standard);
}
