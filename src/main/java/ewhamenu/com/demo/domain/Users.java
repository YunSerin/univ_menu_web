package ewhamenu.com.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@DynamicInsert
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

    @Column(name="email")
    private String email;

    @Column(name = "joinDate")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "is_active" ,columnDefinition = "boolean default true")
    private Boolean isActive;

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

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Users(Users user){
        super();
    }
    public Users(String userId, String password, String nickname, String email, boolean isActive){
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.isActive = isActive;
        this.date = LocalDate.now();
    }

}
