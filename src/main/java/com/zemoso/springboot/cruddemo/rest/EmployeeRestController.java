package com.zemoso.springboot.cruddemo.rest;

import com.zemoso.springboot.cruddemo.dao.EmployeeDto;
import com.zemoso.springboot.cruddemo.entity.Employee;
import com.zemoso.springboot.cruddemo.exception.EmployeeNotFoundException;
import com.zemoso.springboot.cruddemo.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	private EmployeeService employeeService;
	private ModelMapper modelMapper;

	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService, ModelMapper modelMapper) {

		employeeService = theEmployeeService;
		this.modelMapper=modelMapper;
	}
	
	// add mapping for  "/employees" and return list of employees

	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}

	// add mapping for GET /employees/{employeeId}
	
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		
		Employee theEmployee = employeeService.findById(employeeId);
		
		if (theEmployee == null) {
			throw new EmployeeNotFoundException("Employee id not found - " + employeeId);
		}
		return theEmployee;
	}
	
	// add mapping for POST /employees - add new employee

	/*
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {

		// set id to 0- to force a save of new item ... instead of update
		theEmployee.setId(0);
		employeeService.save(theEmployee);
		return theEmployee;
	}
	 */

	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody EmployeeDto employeeDto) {
		Employee theEmployee=new Employee();
		// set id to 0- to force a save of new item ... instead of update
		theEmployee.setId(0);
		theEmployee.setFirstName(employeeDto.getFirstName());
		theEmployee.setLastName(employeeDto.getLastName());
		theEmployee.setEmail(employeeDto.getEmail());
		employeeService.save(theEmployee);
		return theEmployee;
	}


	// add mapping for PUT /employees - update existing employee

	/*
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		employeeService.save(theEmployee);
		return theEmployee;
	}
	 */
	@PutMapping("/employees")
	public EmployeeDto updateEmployee(@RequestBody EmployeeDto employeeDto) {
		Employee theEmployee=new Employee();
		theEmployee.setId(employeeDto.getId());
		theEmployee.setFirstName(employeeDto.getFirstName());
		theEmployee.setLastName(employeeDto.getLastName());
		theEmployee.setEmail(employeeDto.getEmail());

		employeeService.save(theEmployee);

		return modelMapper.map(theEmployee,EmployeeDto.class);
	}
	
	// add mapping for DELETE /employees/{employeeId} - delete employee
	
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		
		Employee tempEmployee = employeeService.findById(employeeId);
		// throw exception if null
		if (tempEmployee == null) {
			throw new EmployeeNotFoundException("Employee id not found - " + employeeId);
		}
		employeeService.deleteById(employeeId);
		return "Deleted employee id - " + employeeId;
	}
	
}


/*OLD way before dto:

package com.zemoso.springboot.cruddemo.rest;

import com.zemoso.springboot.cruddemo.dao.EmployeeDto;
import com.zemoso.springboot.cruddemo.entity.Employee;
import com.zemoso.springboot.cruddemo.exception.EmployeeNotFoundException;
import com.zemoso.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	private EmployeeService employeeService;

	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}

	// add mapping for  "/employees" and return list of employees

	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}

	// add mapping for GET /employees/{employeeId}

	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {

		Employee theEmployee = employeeService.findById(employeeId);

		if (theEmployee == null) {
			throw new EmployeeNotFoundException("Employee id not found - " + employeeId);
		}
		return theEmployee;
	}

	// add mapping for POST /employees - add new employee


	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {

		// set id to 0- to force a save of new item ... instead of update
		theEmployee.setId(0);
		employeeService.save(theEmployee);
		return theEmployee;
	}

	// add mapping for PUT /employees - update existing employee

	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		employeeService.save(theEmployee);
		return theEmployee;
	}


	// add mapping for DELETE /employees/{employeeId} - delete employee

	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {

		Employee tempEmployee = employeeService.findById(employeeId);
		// throw exception if null
		if (tempEmployee == null) {
			throw new EmployeeNotFoundException("Employee id not found - " + employeeId);
		}
		employeeService.deleteById(employeeId);
		return "Deleted employee id - " + employeeId;
	}

}


 */







