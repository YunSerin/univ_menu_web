package ewhamenu.com.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")        //Setting > Editor > Inspection > .. > Unresolved database references in annotations 체크 해제
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "userId")
    private String userId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "joinDate")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    public Long getSeq(){
        return seq;
    }
    public void setSeq(Long seq){
        this.seq = seq;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getNickname(){
        return nickname;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Users(Users user){
        super();
    }
    public Users(String userId, String password, String nickname){
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.date = LocalDate.now();
    }

}
