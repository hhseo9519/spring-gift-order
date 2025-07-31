package gift.entity;
import jakarta.persistence.*;

@Entity
@Table
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private Long kakaoId;


    @Column
    private String kakaoAccessToken;

    protected Member() {

    }

    public Member(Long kakaoId, String email, String password) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.password = password;
    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getKakaoAccessToken() {
        return kakaoAccessToken;
    }
    public void setKakaoAccessToken(String kakaoAccessToken) {
        this.kakaoAccessToken = kakaoAccessToken;
    }
}
