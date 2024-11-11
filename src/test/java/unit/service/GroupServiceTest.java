package unit.service;

import com.nordclan.employees.dto.create.CreateGroupDto;
import com.nordclan.employees.dto.entity.GroupDto;
import com.nordclan.employees.mapper.GroupMapper;
import com.nordclan.employees.repository.GroupRepository;
import com.nordclan.employees.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private GroupMapper groupMapper;

    @InjectMocks
    private GroupService groupService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(groupService);
    }

    @Test
    void create() {
        when(groupMapper.toDto(any())).thenReturn(new GroupDto(1L, "Новая группа"));

        GroupDto result = groupService.create(new CreateGroupDto());

        assertNotNull(result);
        assertEquals("Новая группа", result.getName());
    }

    @Test
    void create_not(){
        when(groupMapper.toDto(any())).thenReturn(new GroupDto(1L, "Новая группа"));

        GroupDto result = groupService.create(new CreateGroupDto());

        assertNotNull(result);
        assertNotEquals("Не Новая группа", result.getName());
    }
}