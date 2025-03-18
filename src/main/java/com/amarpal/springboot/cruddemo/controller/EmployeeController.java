package com.luv2code.springboot.cruddemo.controller;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    //add mapping for "/list"
    @GetMapping("/list")
    public String listEmployee(Model theModel) {
        //get the employee from db
        List<Employee> theEmployees = employeeService.findAll();
        //add to the spring model
        theModel.addAttribute("employees", theEmployees);
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        //create model Attribute to bind form data
        Employee theEmployee = new Employee();
        theModel.addAttribute("employee", theEmployee);
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
        // save the Employee
        employeeService.save(theEmployee);
        //use redirect to prevent duplicate submission
        return "redirect:/employees/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("id") int theId, Model theMdel) {
        //get the employee from the service
        Employee theEmployee = employeeService.findById(theId);
        //set employee in the moel to prepopulate the form
        theMdel.addAttribute("employee", theEmployee);
        //sent over to form
        return "employees/employee-form";
    }

    @GetMapping("/delete")
    public String deleteEmployeeById(@RequestParam("id") int theId) {
        //delete the employee
        employeeService.deleteById(theId);
        //redirect to /employee/list
        return "redirect:/employees/list";
    }


}
