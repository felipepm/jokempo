package com.btg.jokempo.controller;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.btg.jokempo.service.IUsuarioService;
import com.btg.jokempo.service.impl.UsuarioServiceImpl;

//@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
public class UsuarioControllerTest {
//
//	@InjectMocks
//	UsuarioController usuarioController;
//
//	@Mock
//	IUsuarioService usuarioService;
//	
	private UsuarioController usuarioController;
	private IUsuarioService usuarioService;
	
	@BeforeEach
	private void iniciar() {
		usuarioService = mock(UsuarioServiceImpl.class);
		usuarioController = new UsuarioController(usuarioService);
	}


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
