package hello.springmvc2.domain.member.entry;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
public class Member {

    private final Long id;
    private final String username;       // 로그인 아이디 (unique)
    private final String password;       // 암호화된 비밀번호
    private final String name;           // 사용자 이름
    private final LocalDateTime registeredAt;  // 가입일시
    private final LocalDateTime updatedAt;     // 최근 수정일시
}
