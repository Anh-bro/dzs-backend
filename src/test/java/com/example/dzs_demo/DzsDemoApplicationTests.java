package com.example.dzs_demo;

import com.example.dzs_demo.service.IndexService;
import com.example.dzs_demo.service.NoteService;
import com.example.dzs_demo.service.PassageService;
import com.example.dzs_demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DzsDemoApplicationTests {

    @Autowired
    NoteService noteService;

    @Test
    void contextLoads() {
//        noteService.selectNote();
    }

}
