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

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(savedTodo.getContent()).isEqualTo(content);
        assertThat(savedTodo.getContent()).isEqualTo(todo.getContent());
    }

    @Test
    @DisplayName("저장 테스트")
    public void test_save() {
        // GIVEN
        Todo todo = Todo.builder()
                .content("내용1")
                .isComplete(true)
                .createdDateTime(LocalDateTime.now())
                .build();

        // WHEN
        Todo savedTodo = todoRepository.save(todo);
        System.out.println("=========================");
        System.out.println(savedTodo.getId());
        System.out.println(savedTodo.getContent());
        System.out.println(savedTodo.getIsComplete());
        System.out.println(savedTodo.getCreatedDateTime());
        System.out.println("=========================");

        // THEN
        assertThat(savedTodo.getId()).isGreaterThan(0);
        assertThat(savedTodo.getContent()).isEqualTo("내용1");
        assertThat(savedTodo.getIsComplete()).isEqualTo(true);
    }

    @Test
    @DisplayName("삭제 테스트")
    public void test_delete() {
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
        todoRepository.deleteById(id);

        // THEN
        assertThat(entityManager.find(Todo.class, id)).isNull();
    }

}
