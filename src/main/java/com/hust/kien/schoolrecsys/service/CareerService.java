package com.hust.kien.schoolrecsys.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CareerService {
    public List<String> findAll() {
        List<String> careers = new ArrayList<>();
        careers.add("Nông, lâm, thuỷ sản");
        careers.add("Khai khoáng ");
        careers.add("Công nghiệp chế biến, chế tạo");
        careers.add("Sản xuất và phân phối điện, khí đốt, hơi nước và điều hoà không khí");
        careers.add("Cung cấp nước, hoạt động quản lý và xử lý rác thải, nước thải");
        careers.add("Xây dựng");
        careers.add("Bán buôn và bán lẻ; sửa chữa ô tô, mô tô, xe máy và xe có động cơ khác");
        careers.add("Vận tải kho bãi");
        careers.add("Dịch vụ lưu trú và ăn uống");
        careers.add("Thông tin và truyền thông");
        careers.add("Hoạt động tài chính, ngân hàng và bảo hiểm");
        careers.add("Hoạt động chuyên môn, khoa học và công nghệ ");
        careers.add("Hoạt động hành chính và dịch vụ hỗ trợ");
        careers.add("Giáo dục và đào tạo");
        careers.add("Y tế và hoạt động trợ giúp xã hội");
        careers.add("Nghệ thuật, vui chơi và giải trí");
        careers.add("Hoạt động dịch vụ khác");
        return careers;
    }
}
