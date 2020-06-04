package com.btg.jokempo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.btg.jokempo.service.IUsuarioService;

@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
public class UsuarioControllerTest {

	@InjectMocks
	UsuarioController usuarioController;

	@Mock
	IUsuarioService usuarioService;

	@Test
	public void testAddEmployee() {
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//
//		when(usuarioService.cadastrar(any(UsuarioDto.class))).thenReturn(true);
//
//		UsuarioDto usuarioDto = new UsuarioDto("Felipe");
//		ResponseEntity<Object> responseEntity = usuarioController.cadastrar(usuarioDto);
//		
//		Assertions.assertEquals(201, responseEntity.getStatusCodeValue());

//		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//		assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
	}

//	@Test
//	public void testFindAll() {
//		// given
//		Employee employee1 = new Employee(1, "Lokesh", "Gupta", "howtodoinjava@gmail.com");
//		Employee employee2 = new Employee(2, "Alex", "Gussin", "example@gmail.com");
//		Employees employees = new Employees();
//		employees.setEmployeeList(Arrays.asList(employee1, employee2));
//
//		when(employeeDAO.getAllEmployees()).thenReturn(employees);
//
//		// when
//		Employees result = employeeController.getEmployees();
//
//		// then
//		assertThat(result.getEmployeeList().size()).isEqualTo(2);
//
//		assertThat(result.getEmployeeList().get(0).getFirstName()).isEqualTo(employee1.getFirstName());
//
//		assertThat(result.getEmployeeList().get(1).getFirstName()).isEqualTo(employee2.getFirstName());
//	}
}
