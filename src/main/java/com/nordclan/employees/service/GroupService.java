package com.nordclan.employees.service;

import com.nordclan.employees.api.DefaultService;
import com.nordclan.employees.dto.create.CreateGroupDto;
import com.nordclan.employees.dto.entity.GroupDto;
import com.nordclan.employees.entity.Group;
import com.nordclan.employees.exception.GroupNotFoundException;
import com.nordclan.employees.mapper.GroupMapper;
import com.nordclan.employees.repository.GroupRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nordclan.employees.utils.CommonUtils.setIfNotBlank;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Transactional
public class GroupService extends DefaultService<Long, Group, GroupDto> {
    GroupRepository groupRepository;
    GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        super(groupRepository, groupMapper, GroupNotFoundException::new);
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    public GroupDto create(CreateGroupDto groupDto) {
        return groupMapper.toDto(
            groupRepository.save(
                groupMapper.toNewEntity(groupDto)));
    }

    @Override
    protected Group updateInternal(Group currentGroup, GroupDto groupDto) {
        setIfNotBlank(groupDto.getName(), currentGroup::setName);
        return currentGroup;
    }
}