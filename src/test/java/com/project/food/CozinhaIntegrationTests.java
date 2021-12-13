package com.project.food;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.food.domain.exception.CozinhaNaoEncontradaException;
import com.project.food.domain.model.Cozinha;
import com.project.food.domain.service.CozinhaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CozinhaIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private CozinhaService cozinhaService;

    @Before
    public void setUp(){
       RestAssured.port = port;
       RestAssured.basePath = "/cozinhas";
    }

//    @Test
//    public void testarCozinhaServicePost() {
//        Cozinha novaCozinha = new Cozinha();
//        novaCozinha.setNome("Chinesa");
//
//        cozinhaService.save(novaCozinha);
//
//        assertThat(novaCozinha).isNotNull();
//        assertThat(novaCozinha.getId()).isNotNull();
//    }

    @Test(expected = ConstraintViolationException.class)
    public void testarPostCozinhaSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);
        cozinhaService.save(novaCozinha);
    }

    @Test(expected = CozinhaNaoEncontradaException.class)
    public void testarDeleteCozinhaInexistente() {
        cozinhaService.delete(100L);
    }

    @Test
    public void ConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get().then().statusCode(200);
    }

    @Test
    public void ConsultarCozinhasSize() {
        given()
                //.basePath("/cozinhas")
                //.port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(3))
                .body("nome", Matchers.hasItems("Brasileira", "Tailandesa", "Chinesa"));
    }

    @Test
    public void consultarCozinhaId(){
        given()
                .pathParam("id", 1)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", Matchers.equalTo("Brasileira"));
    }

    @Test
    public void consultarCozinhaIdInexistente(){
        given()
                .pathParam("id", 100)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
