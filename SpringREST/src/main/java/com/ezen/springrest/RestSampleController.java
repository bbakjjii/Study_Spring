package com.ezen.springrest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.springrest.dto.EmployeeDTO;
import com.ezen.springrest.dto.Pizza;

import lombok.extern.log4j.Log4j;

// -- 1 --
@Log4j
@RestController
public class RestSampleController {
	
	// value : 들어오게 되는 경로
	// produces = 어떤 타입의 데이터를 생산 할 것인지
	@GetMapping(value = {"/restful/test1"}, produces = "text/plain; charset=UTF-8") // 어떤 주소로 들어올 때 GetMapping을 할지
	public String test1() {
		log.info("요청을 받았습니다!");
		
		return "Hello, world! 안녕하세요!!"; // 데이터 자체를 리턴한다
	}
	
	// -- 3, 5 --
	// JSON 타입의 데이터를 쉽게 응답하기 위해서는 jackson-databind라는 라이브러리를 사용한다
	// JSON 형태의 문자열을 응답하면서 해당 데이터가 json이라고 표시해줄 수 있다.
	@GetMapping(value = {"/restful/pizza1"}, produces = "application/json; charset=UTF-8")
	public String pizza1() {
		return "{\"name\":\"포테이토피자\",\"price\":null,\"calories\":2041.38}"; // 문자열이지만 json으로 인식
	}
	
	// -- 6 --
	@GetMapping(value = "/restful/pizza2", produces = "application/json; charset=UTF-8")
	public Pizza pizza2() {
		Pizza pizza = new Pizza();
		
		pizza.setName("콤비네이션 피자");
		pizza.setPrice(9900);
		pizza.setCalories(1888.88);
		
		return pizza;
	}
	
	// -- 10 (9번은 pom.xml에 jackson-dataformat XML라이브러리 설치) --
	// XML타입으로 자동변환 하기 위해서는 
	@GetMapping(value = "/restful/pizza3", produces = MediaType.APPLICATION_XML_VALUE)
	public Pizza pizza3() {
		Pizza pizza = new Pizza();
		
		pizza.setName("페퍼로니 피자");
		pizza.setCalories(2111.23);
		pizza.setPrice(8000);
		
		return pizza;
	}
	
	// -- 12 --
	@GetMapping(value = "restful/ok1")
	public ResponseEntity<String> ok1() {
		// ResponseEntity<> : 개발자가 원하는 응답을 마음대로 생성하여 응답한다
		
		// ResponseEntity.ok() : 상태코드가 200인 응답을 바로 생성한다 (상태코드 200은 정상적인 통신 성공)
		// ResponseEntity.ok(body) : body는 데이터를 실어보내는 곳을 의미한다
		
		//return ResponseEntity.ok("I am a data"); // -- ok1
		return ResponseEntity.ok("<html><head></head><body><h3>Hello!</h3></body></html>"); // -- ok1
	}
	
	// -- 13 --
	@GetMapping(value = "/restful/ok2")
	public ResponseEntity<String> ok2() {
		return ResponseEntity
		.ok()
		//.contentType(MediaType.TEXT_PLAIN)
		.contentType(MediaType.TEXT_HTML)
		.body("<html><head></head><body><h3>Hello!</h3></body></html>");
	}
	
	// -- 14 --
	// # 생성자 만들어서 하기
	@GetMapping(value = "/restful/ok3")
	public ResponseEntity<Pizza> ok3() {
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(new Pizza("하와이안 피자", 11200, 2333.33));
	}
	
	// -- 16 --
	// DB에서 데이터 실어보내면 끝
	@GetMapping(value = "/restful/status1")
	public ResponseEntity<String> status1() {
		return ResponseEntity
				.status(HttpStatus.BAD_GATEWAY)
				.contentType(MediaType.TEXT_PLAIN)
				.body("Bad Gateway...");
	}
	
	// -- 17 --
	// 페이지를 찾을 수 없습니다라고 나오게 하기
	@GetMapping(value = "/result/status2")
	public ResponseEntity<String> status2(){
		return ResponseEntity.notFound().build();
	}
	
	// -- 20 --
	// 숫자가 아닌 문자가 들어오면 에러(에러코드 400번)가 난다
	@GetMapping(value = "/restful/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EmployeeDTO getEmployee(@PathVariable("id")Integer employee_id) {
		log.info("employee_id: " + employee_id);
		
		return new EmployeeDTO();
	}
	
	// -- 23 --
	// -- 37 --
	// @PostMapping(value = "/restful/employee") // -- 23 --
	@PostMapping(value = "/restful/employee", produces = MediaType.APPLICATION_JSON_VALUE) // -- 37 --
	public EmployeeDTO createEmployee(EmployeeDTO employee) {
		log.info("insert this employee: " + employee);
		return new EmployeeDTO();
	}
	
	// -- 42 --
	@PostMapping(value = "/restful/employee/json")
	public String createEmployee2(@RequestBody EmployeeDTO employee) {
		log.info("insert this employee2: " + employee);
		
		return "<h1>Good!</h1>";
	}
	// -- 42 끝 --
	
	// PUT : 해당 데이터의 전체 수정
	// PATCH : 해당 데이터의 일부 수정 (나머지는 기존 값 유지)
	// @RequestBody : JSON 또는 XML로 도착한 데이터를 DTO 형태로 바인딩해준다 //-- 41 --
	 @PutMapping(value = "/restful/employee")
	// public ResponseEntity<EmployeeDTO> putEmployee(EmployeeDTO employee){
	   public ResponseEntity<EmployeeDTO> putEmployee(@RequestBody EmployeeDTO employee){ //-- 41 --
		 // -- 41 --
		 log.info("update this employee: " + employee);
		 // -- 41 끝 --
		 
		 // 데이터 수정 후 결과에 따라 알맞은 응답을 생성
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_XML)
				.body(new EmployeeDTO());
	 }
	 
	 // PATCH - 해당 데이터의 일부 수정 (나머지는 기존 값 유지)
	 @PatchMapping(value = "/restful/employee")
	 public ResponseEntity<EmployeeDTO> patchEmployee(EmployeeDTO employee) {
		 // 데이터 수정 시 일부의 데이터만 수정
		 return ResponseEntity.ok(new EmployeeDTO());
	 }
	 
	 @DeleteMapping(value="/restful/employee/{id}")
	 public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable("id")Integer employee_id){
		// 삭제 후 결과에 따라 알맞은 응답을 생성 
		return ResponseEntity.ok(new EmployeeDTO());
	 }
}
