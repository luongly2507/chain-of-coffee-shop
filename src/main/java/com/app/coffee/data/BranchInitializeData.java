package com.app.coffee.data;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.app.coffee.entity.Branch;
import com.app.coffee.repository.BranchRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class BranchInitializeData implements CommandLineRunner {
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public void run(String... args) throws Exception {
        branchRepository.saveAll(
                Arrays.asList(
                        Branch.builder()
                                .name("CN Quận 10")
                                .address("Quận 10, TP HCM")
                                .description("")
                                .build(),
                        Branch.builder()
                                .name("CN Hà Nội")
                                .address("Cầu Giấy, Hà Nội")
                                .description("Chi nhánh chính")
                                .build(),
                        Branch.builder()
                                .name("CN Đà Nẵng")
                                .address("Đà Nẵng")
                                .description("")
                                .build(),
                        Branch.builder()
                                .name("CN Quận 3")
                                .address("Quận 3, TP HCM")
                                .description("")
                                .build(),
                        Branch.builder()
                                .name("CN Cần Thơ")
                                .address("Ninh Kiều, Cần Thơ")
                                .description("")
                                .build()));
    }

}
