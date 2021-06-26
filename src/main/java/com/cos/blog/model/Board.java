package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content;

    private int count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER) // N:1 연관관계
    @JoinColumn(name = "userId") // 실제 테이블에선 FK가 생성된다.
    private User user; // DB는 오브젝트를 저장할 수 없다, 자바는 오브젝트를 저장할 수 있다.

    // mappedBy : 연관관계의 주인이 아니다. (DB에 컬럼을 만들지 않는다.), Reply의 board를 FK로 쓸것임을 알림
    // board 를 select 할 때 같이 가져오기 위해 필요한 것임을 알림
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("board") // 데이터 리턴 시 jackson 라이브러리가 동작하는데 그 때 발생하는 무한참조 방지
    @OrderBy("id desc") // 정렬
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp createDate;
}
