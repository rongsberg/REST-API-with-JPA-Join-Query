package payroll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
interface EmployeeRepository extends JpaRepository<Employee, Long> {
  @Query("SELECT e FROM Employee e WHERE e.role = :role")
    public List<Employee> findbyRole(@Param("role") String role);

  @Query("SELECT e FROM Employee e WHERE e.name LIKE CONCAT('%',:name,'%')")
    public List<Employee> findNameLike(@Param("name") String nameLike);

  @Query("SELECT e FROM Employee e WHERE e.dept.name = :name")
    public List<Employee> findEmployeeByDeptName(@Param("name") String deptName);
}