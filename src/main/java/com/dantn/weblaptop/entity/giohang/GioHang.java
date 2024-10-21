package com.dantn.weblaptop.entity.giohang;

import com.dantn.weblaptop.entity.khachhang.KhachHang;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Getter
@Setter
@ToString
@Builder
@Table(name = "gio_hang")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false)
    Long id;
    @Column(name = "session_id")
    String sessionId;
    @OneToOne
    @JoinColumn(name = "id_khach_hang",referencedColumnName = "id")
    KhachHang khachHang;
}
