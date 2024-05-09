package com.example.testproject.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.testproject.entities.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    // 추가적으로 필요한 메소드가 있다면 여기에 정의할 수 있습니다.
}
