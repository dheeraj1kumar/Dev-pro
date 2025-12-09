package in.bushansirgur.springbootrestapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.bushansirgur.springbootrestapi.entity.Employee;
import in.bushansirgur.springbootrestapi.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*") // allow all origins; you can replace "*" with your frontend container URL
public class EmployeeController {

    @Autowired
    private EmployeeRepository eRepo;

    // Get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return eRepo.findAll();
    }

    // Get employee by ID
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = eRepo.findById(id);
        return employee.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new employee
    @PostMapping("/employees")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = eRepo.save(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // Update an existing employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Optional<Employee> optionalEmployee = eRepo.findById(id);

        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Employee employee = optionalEmployee.get();
        employee.setName(employeeDetails.getName());
        employee.setLocation(employeeDetails.getLocation());
        employee.setDepartment(employeeDetails.getDepartment());

        Employee updatedEmployee = eRepo.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Delete an employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
        if (!eRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        eRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
