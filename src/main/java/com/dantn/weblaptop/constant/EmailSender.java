package com.dantn.weblaptop.constant;

import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import com.dantn.weblaptop.repository.VaiTro_Repository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class EmailSender {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void signupNhanVienSendEmail(NhanVien nhanVien, String genPassWord, Set<VaiTro> vaiTros) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(nhanVien.getEmail());
        message.setSubject("Chào mừng bạn đến với Cửa hàng bán Laptop ComNoOne.");

        String vaiTrosStr = vaiTros.stream()
                .map(VaiTro::getTen)
                .reduce((role1, role2) -> role1 + ", " + role2)
                .orElse("");

        String emailContent = String.format(
                "Xin chào %s,\n\n" +
                        "Chúng tôi rất vui mừng chào đón bạn gia nhập đội ngũ của Cửa hàng Laptop ComNoOne! Dưới đây là thông tin đăng nhập cần thiết để bạn có thể truy cập vào hệ thống của chúng tôi:\n\n" +
                        "Tài khoản (email): %s\n" +
                        "Mật khẩu: %s\n" +
                        "Chức vụ: %s\n\n" +
                        "Vui lòng lưu ý rằng đây là mật khẩu tạm thời. Chúng tôi khuyến nghị bạn nên thay đổi mật khẩu ngay sau khi đăng nhập lần đầu. Hãy đảm bảo rằng bạn giữ thông tin đăng nhập này một cách cẩn thận để tránh rủi ro về việc lạm dụng tài khoản.\n\n" +
                        "Nếu bạn gặp bất kỳ vấn đề gì trong quá trình đăng nhập hoặc sử dụng hệ thống, vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi qua địa chỉ [địa chỉ email hỗ trợ] hoặc số điện thoại [số điện thoại hỗ trợ]. Chúng tôi luôn sẵn sàng hỗ trợ bạn.\n\n" +
                        "Chúc bạn một ngày làm việc hiệu quả và thú vị tại Laptop ComNoOne. Chúng tôi hy vọng bạn sẽ đóng góp mạnh mẽ vào sự phát triển của đội ngũ và sự thành công của cửa hàng.\n\n" +
                        "Trân trọng,\n" +
                        "Vũ Ngọc Tú\n" +
                        "Giám đốc Cửa hàng Laptop ComNoOne",
                nhanVien.getTen(),
                nhanVien.getEmail(),
                genPassWord,
                vaiTrosStr
        );

        message.setText(emailContent);
        javaMailSender.send(message);
    }

    public void sendForgotPasswordEmailForNhanVien(NhanVien nhanVien, String newPlainTextPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(nhanVien.getEmail());
        message.setSubject("Thực hiện thay đổi mật khẩu \"ComNoOne\"");
        message.setText("Chào anh/chị, " +
                "Dưới đậy là mật khẩu mới của bạn" +
                "Mật khẩu: " + newPlainTextPassword + "\n" +
                "Vui lòng dùng mật khẩu này để đăng nhập lại tài khoản của bạn" + "\n" +
                "Trân trọng" + "\n" +
                "Của hàng bán áo đấu thể thao PSG");
        javaMailSender.send(message);
    }
}
