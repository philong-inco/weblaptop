package com.dantn.weblaptop.util;

import com.dantn.weblaptop.dto.response.SanPhamChiTietClientDTO;
import com.dantn.weblaptop.dto.response.SanPhamClientDTO;

import java.util.ArrayList;
import java.util.List;

public class FakeDataForClient {
    public static List<SanPhamClientDTO> fakeDataSanPhamForClient(){
        List<SanPhamClientDTO> list = new ArrayList<>();
        for (long i = 0; i < 100; i++) {
            SanPhamClientDTO sp = SanPhamClientDTO.builder()
                    .id(i+1)
                    .ten("Laptop Thinkpad Carbon Gen10 16GB-256GB 14\" siêu mỏng văn phòng")
                    .moTa("Laptop Thinkpad Carbon Gen10 16GB-256GB 14\" siêu mỏng văn phòng Laptop Thinkpad Carbon Gen10 16GB-256GB 14\" siêu mỏng văn phòng Laptop Thinkpad Carbon Gen10 16GB-256GB 14\" siêu mỏng văn phòng")
                    .nhuCau("Văn phòng")
                    .thuongHieu("Thinkpad")
                    .giaMin("12.000.000")
                    .giaMax("14.000.000")
                    .giaSauKhuyenMai("11.500.000")
                    .tenKhuyenMai("Giảm 12%")
                    .soTienGiam("800.000")
                    .banChay(true)
                    .image("https://macstores.vn/wp-content/uploads/2023/04/thinkpad_x1_carbon_gen_11_1.png")
                    .build();
            list.add(sp);
        }
        return list;
    }

    public static SanPhamChiTietClientDTO fakeDataSPCTForClient(){

            SanPhamChiTietClientDTO sp = SanPhamChiTietClientDTO.builder()
                    .id(1l)
                    .tenSanPham("Laptop Thinkpad Carbon Gen10 16GB-256GB 14\" siêu mỏng văn phòng")
                    .moTaSP("Laptop Thinkpad Carbon Gen10 16GB-256GB 14\" siêu mỏng văn phòng Laptop Thinkpad Carbon Gen10 16GB-256GB 14\" siêu mỏng văn phòng Laptop Thinkpad Carbon Gen10 16GB-256GB 14\" siêu mỏng văn phòng")
                    .nhuCau("Văn phòng")
                    .thuongHieu("Thinkpad")
                    .giaBan("14.000.000")
                    .banPhim("Bàn phím LED")
                    .heDieuHanh("Window")
                    .manHinh("14 inch")
                    .mauSac("Xám đen")
                    .oCung("500GB")
                    .ram("16GB")
                    .maSP("SP00000")
                    .cpu("Intel CoreI7-ABCXYZ")
                    .vga("NIVIDIA GTX")
                    .webcam("5MP")
                    .tonKhoConLai("Còn 9 sản phẩm")
                    .sanPhamId(1l)
                    .giaSauKhuyenMai("12.000.000")
                    .soTienDuocGiam("2.000.000")
                    .tenKhuyenMai("Giảm 12%")
                    .listUrlAnhSanPham("https://macstores.vn/wp-content/uploads/2023/04/thinkpad_x1_carbon_gen_11_1.png,https://thegioiso365.vn/wp-content/uploads/2022/10/x1-gen-10-13-768x766.jpg,https://thegioiso365.vn/wp-content/uploads/2022/10/17-1-1536x864.jpg")
                    .build();
        return sp;
    }

}
