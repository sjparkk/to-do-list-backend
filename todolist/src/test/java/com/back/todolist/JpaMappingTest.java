package com.back.todolist;

import com.back.todolist.domain.Todo;
import com.back.todolist.repository.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@DataJpaTest
public class JpaMappingTest {

    private final String content = "내용";

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Todo getSaved() {
        Todo todo = Todo.builder()
                .content(content)
                .createdDateTime(LocalDateTime.now())
                .build();

        return entityManager.persist(todo);
    }

    @Test
    @DisplayName("도메인 객체 생성 테스트")
    public void test_get() {
        // GIVEN
        Todo todo = getSaved();
        System.out.println("=========================");
        System.out.println(todo.getId());
        System.out.println(todo.getContent());
        System.out.println(todo.getIsComplete());
        System.out.println(todo.getCreatedDateTime());
        System.out.println("=========================");
        Long id = todo.getId();

        // WHEN
        Todo savedTodo = todoRepository.getOne(id);

        // THEN
        Assertions.assertThat(savedTodo.getContent()).isEqualTo(content);
        Assertions.assertThat(savedTodo.getContent()).isEqualTo(todo.getContent());
    }

}
