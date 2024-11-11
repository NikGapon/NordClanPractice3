package unit.controller;

import com.nordclan.employees.controller.GroupController;
import com.nordclan.employees.dto.entity.GroupDto;
import com.nordclan.employees.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {
    @Mock
    private GroupService groupService;
    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMVC;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() { mockMVC = MockMvcBuilders.standaloneSetup(groupController).build();
        objectMapper = new ObjectMapper();

    }


    @Test
    void getGroup() throws Exception {
        GroupDto groupDto = new GroupDto(1L, "Чета");
        when(groupService.findById(1L)).thenReturn(groupDto);
        mockMVC.perform(get("/group/getGroup/{id}", 1)

//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(groupDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Чета"))

        ;
    }

    @Test
    void allGroups() throws Exception {
        GroupDto groupDto = new GroupDto(1L, "Чета");
        GroupDto groupDto2 = new GroupDto(2L, "Чета2");
        List<GroupDto> test_list = new ArrayList<>();
        test_list.add(groupDto2);
        test_list.add(groupDto);


        when(groupService.findAll()).thenReturn(test_list);


        mockMVC.perform(get("/group/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("").value(test_list));


    }

    @Test
    void createGroup() throws Exception {
        GroupDto groupDto = new GroupDto(1L, "Чета");
        mockMVC.perform(post("/group/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(groupDto))).andExpect(status().isOk());

    }

    @Test
    void deleteGroup() throws Exception{
       //when(groupService.delete(1L));

        mockMVC.perform(delete("/group/delete/{groupId}", 1L)
                        )
                .andExpect(status().isOk());
    }

    @Test
    void updateGroup() throws Exception {
        GroupDto groupDto = new GroupDto(1L, "Чета");
        GroupDto groupDto2 = new GroupDto(1L, "Чета новое");
        when(groupService.update(1L, groupDto))
                .thenReturn(groupDto2);

        mockMVC.perform(put("/group/update/{groupId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Чета новое"));
    }
    }
