package ewhamenu.com.demo.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Email {
    private String address;
    private String title="【이돼장】 임시비밀번호 발급 안내";
    private String message;

    public String getAddress() { return address; }
    public void setAddress(String address) {this.address = address;  }

    public String getTitle() { return title; }

    public String getMessage() { return message; }
    public void setMessage(String userId, String password) {
        this.message = userId+"님의 임시비밀번호\n" +password+
                "\n";
    }
}
