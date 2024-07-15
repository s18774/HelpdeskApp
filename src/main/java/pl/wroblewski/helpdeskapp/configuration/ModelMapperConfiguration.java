package pl.wroblewski.helpdeskapp.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wroblewski.helpdeskapp.dto.*;
import pl.wroblewski.helpdeskapp.dto.application.ApplicationDto;
import pl.wroblewski.helpdeskapp.dto.device.DeviceDto;
import pl.wroblewski.helpdeskapp.dto.ticket.TicketDto;
import pl.wroblewski.helpdeskapp.dto.user.UserDetailsDto;
import pl.wroblewski.helpdeskapp.dto.user.UserDto;
import pl.wroblewski.helpdeskapp.models.*;

@Configuration

public class ModelMapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        addTicketMapping(modelMapper);
        addApplicationMapping(modelMapper);
        addUserMapping(modelMapper);
        addDeviceMapping(modelMapper);

        return modelMapper;
    }

    public void addTicketMapping(ModelMapper modelMapper) {
        modelMapper.createTypeMap(UserTicket.class, TicketDto.class)
                .addMappings(mapper -> {
                    mapper.map(t -> t.getTicket().getTicketId(), TicketDto::setTicketId);
                    mapper.map(t -> t.getTicket().getSla().getSlaLevel(), TicketDto::setSla);
                    mapper.map(UserTicket::getOpeningDate, TicketDto::setOpeningDate);
                    mapper.map(t -> t.getTicket().getTitle(), TicketDto::setTitle);
                    mapper.map(t -> t.getUser().getFullName(), TicketDto::setFullName);
                    mapper.map(t -> t.getStageId().getStageId(), TicketDto::setStageId);
                    mapper.map(t -> t.getTicket().getDescription(), TicketDto::setDescription);
                });


        modelMapper.createTypeMap(UserTicket.class, JobDto.class)
                .addMappings(mapper -> {
                    mapper.map(t -> "ticket", JobDto::setJobType);
                    mapper.map(t -> t.getId().getTicketId(), JobDto::setJobId);
                    mapper.map(t -> t.getUser().getFullName(), JobDto::setFullName);
                    mapper.map(t -> t.getTicket().getSla().getSlaLevel(), JobDto::setSla);
                    mapper.map(t -> t.getStageId().getStageId(), JobDto::setStageId);
                });

    }

    public void addApplicationMapping(ModelMapper modelMapper) {
        modelMapper.createTypeMap(UserApplication.class, ApplicationDto.class)
                .addMappings(mapper -> {
                    mapper.map(a -> a.getApplication().getApplicationId(), ApplicationDto::setApplicationId);
                    mapper.map(a -> a.getApplication().getSla().getSlaLevel(), ApplicationDto::setSla);
                    mapper.map(a -> a.getApplication().getSubject(), ApplicationDto::setSubject);
                    mapper.map(a -> a.getApplication().getDescription(), ApplicationDto::setDescription);
                    mapper.map(UserApplication::getOpeningDate, ApplicationDto::setOpeningDate);
                    mapper.map(a -> a.getUser().getFullName(), ApplicationDto::setFullName);
                    mapper.map(a -> a.getHelpDeskId().getUserId(), ApplicationDto::setHelpdeskId);
                    mapper.map(a -> a.getGroupId().getGroupId(), ApplicationDto::setGroupId);
                    mapper.map(a -> a.getStageId().getStageId(), ApplicationDto::setStageId);
                });


        modelMapper.createTypeMap(UserApplication.class, JobDto.class)
                .addMappings(mapper -> {
                    mapper.map(t -> "application", JobDto::setJobType);
                    mapper.map(t -> t.getId().getApplicationId(), JobDto::setJobId);
                    mapper.map(t -> t.getUser().getFullName(), JobDto::setFullName);
                    mapper.map(t -> t.getApplication().getSla().getSlaLevel(), JobDto::setSla);
                    mapper.map(t -> t.getStageId().getStageId(), JobDto::setStageId);
                });
    }

    public void addUserMapping(ModelMapper modelMapper) {
        var mapping = modelMapper.createTypeMap(User.class, UserDto.class);
        mapping.addMapping(User::getFullName, UserDto::setFullName);

        modelMapper.createTypeMap(User.class, UserDetailsDto.class)
                .addMappings(mapper -> {
                    mapper.map(u -> u.getGroup().getGroupName(), UserDetailsDto::setGroupName);
                    mapper.map(User::getUserId, UserDetailsDto::setUserId);
                    mapper.map(User::getPositionName, UserDetailsDto::setPositionName);
                    mapper.map(User::getFirstName, UserDetailsDto::setFirstName);
                    mapper.map(User::getSecondName, UserDetailsDto::setSecondName);
                    mapper.map(u -> u.getGroup().getGroupId(), UserDetailsDto::setGroupId);
                    mapper.map(u -> u.getSupervisor().getUserId(), UserDetailsDto::setSupervisorId);
                    mapper.map(u -> u.getDepartmentId().getDepartmentId(), UserDetailsDto::setDepartmentId);
                    mapper.map(u -> u.getRole().getRoleId(), UserDetailsDto::setRoleId);
                    mapper.map(User::getPhoneNumber, UserDetailsDto::setPhoneNumber);
                    mapper.map(User::getEmail, UserDetailsDto::setEmail);
                    mapper.map(User::getFloor, UserDetailsDto::setFloor);
                    mapper.map(User::getRoom, UserDetailsDto::setRoom);
                });
    }

    public void addDeviceMapping(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Device.class, DeviceDto.class)
                .addMappings(mapper -> {
                    mapper.map(Device::getModel, DeviceDto::setModel);
                    mapper.map(Device::getBrand, DeviceDto::setBrand);
                    mapper.map(d -> d.getDeviceType().getTypeDescription(), DeviceDto::setDeviceTypeName);
                    mapper.map(Device::getSerialNumber, DeviceDto::setSerialNumber);
                });
    }
}
