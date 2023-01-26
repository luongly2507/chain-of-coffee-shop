package com.app.coffee.data;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.app.coffee.entity.Category;
import com.app.coffee.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class CategoryInitializeData implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        categoryRepository.saveAll(
            Arrays.asList(
                Category.builder()
                        .name("Đá xay")  
                        .description("Đá xay là dòng thức uống kết hợp sữa tươi, đá viên với nhiều nguyên liệu khác. Đặc điểm “nhận dạng” dòng thức uống này chính là phần kem tươi xốp mịn bên trên. ")
                        .build(),
                Category.builder()
                        .name("Sinh tố")  
                        .description("Sinh tố là chỉ thức uống được chế biến từ các loại hoa quả tươi bằng cách xay nhuyễn với một vài muỗng cà phê sữa đặc có đường, đá vụn và trái cây tươi. Sinh tố là một nước uống bổ dưỡng giàu vitamin rất tốt cho sức khỏe.")
                        .build(),
                Category.builder()
                        .name("Coffee")  
                        .description("Cà phê là một loại thức uống được ủ từ hạt cà phê rang, lấy từ quả của cây cà phê. ")
                        .build(),
                Category.builder()
                        .name("Nước ép")  
                        .description("Nước trái cây là thức uống được tạo ra từ việc chiết xuất hoặc ép chất lỏng tự nhiên có trong trái cây và rau quả. ")
                        .build(),
                Category.builder()
                        .name("Bánh ngọt")  
                        .description("Bánh ngọt hay bánh ga-tô là một thuật ngữ chung cho bánh có nguồn gốc từ phương Tây. Bánh một dạng thức ăn ngọt làm từ bột mì, đường và các thành phần khác, thường được nướng. ")
                        .build(),
                Category.builder()
                        .name("Topping")  
                        .description("Topping là từ dùng để chỉ những loại thức ăn được đặt phía trên một loại thức ăn khác. ")
                        .build(),
                Category.builder()
                        .name("Trà sữa")  
                        .description("Trà sữa là loại thức uống đa dạng được tìm thấy ở nhiều nền văn hóa, bao gồm một vài cách kết hợp giữa trà và sữa. Các loại thức uống khác nhau tùy thuộc vào lượng thành phần chính của mỗi loại, phương pháp pha chế, và các thành phần khác được thêm vào.")
                        .build(),
                Category.builder()
                        .name("Soda")  
                        .description("Soda hay còn gọi là soft drink, là loại nước khoáng có gas.")
                        .build()
            )
        );
        
    }

}
