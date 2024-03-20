package com.example.projectschedulehaircutserver.service.employee;

import com.example.projectschedulehaircutserver.dto.EmployeeDTO;
import com.example.projectschedulehaircutserver.entity.Account;
import com.example.projectschedulehaircutserver.entity.Employee;
import com.example.projectschedulehaircutserver.entity.Role;
import com.example.projectschedulehaircutserver.repository.AccountRepo;
import com.example.projectschedulehaircutserver.repository.EmployeeRepo;
import com.example.projectschedulehaircutserver.repository.RoleRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeRepo employeeRepo;
    private AccountRepo accountRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder encoder;
    @Override
    public void createEmployee(EmployeeDTO employeeDTO, Integer roleId) {
        try {
            Role role = roleRepo.findById(roleId).orElseThrow(() -> new RuntimeException("No roles specified."));

            Account account = Account.builder()
                    .fullName(employeeDTO.getFullName())
                    .userName(employeeDTO.getUserName())
                    .password(encoder.encode(employeeDTO.getPassword()))
                    .age(employeeDTO.getAge())
                    .address(employeeDTO.getAddress())
                    .role(role)
                    .phone(employeeDTO.getPhone())
                    .build();

            Account savedAccount = accountRepo.save(account);

            Employee employee = new Employee();

            employee.setFullName(employeeDTO.getFullName());
            employee.setUserName(employeeDTO.getUserName());
            employee.setPassword(encoder.encode(employeeDTO.getPassword()));
            employee.setAge(employeeDTO.getAge());
            employee.setAddress(employeeDTO.getAddress());
            employee.setPhone(employeeDTO.getPhone());
            employee.setIsDeleted(false);
            employee.setAvatar(employeeDTO.getAvatar());
            employee.setAccount(savedAccount);

            employeeRepo.save(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
