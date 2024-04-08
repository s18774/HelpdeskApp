package pl.wroblewski.helpdeskapp.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wroblewski.helpdeskapp.dto.*;
import pl.wroblewski.helpdeskapp.models.Ticket;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserApplication;
import pl.wroblewski.helpdeskapp.models.UserTicket;

@Configuration

public class ModelMapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        addTicketMapping(modelMapper);
        addApplicationMapping(modelMapper);
        addUserMapping(modelMapper);

        return modelMapper;
    }

    private void addTicketMapping(ModelMapper modelMapper) {
        var ticketMapping = modelMapper.createTypeMap(UserTicket.class, TicketDto.class);
        ticketMapping.addMapping(t -> t.getTicket().getTicketId(), TicketDto::setTicketId);
        ticketMapping.addMapping(t -> t.getTicket().getSla().getSlaLevel(), TicketDto::setSla);
        ticketMapping.addMapping(UserTicket::getOpeningDate, TicketDto::setOpeningDate);
        ticketMapping.addMapping(t -> t.getTicket().getDescription(), TicketDto::setTitle);
        ticketMapping.addMapping(t -> t.getUser().getFullName(), TicketDto::setFullName);

        var jobMapping = modelMapper.createTypeMap(UserTicket.class, JobDto.class);
        jobMapping.addMapping(t -> "ticket", JobDto::setJobType);
        jobMapping.addMapping(UserTicket::getId, JobDto::setId);
        jobMapping.addMapping(t -> t.getUser().getFullName(), JobDto::setFullName);
        jobMapping.addMapping(t -> t.getTicket().getSla().getSlaLevel(), JobDto::setSla);
    }

    private void addApplicationMapping(ModelMapper modelMapper) {
        var mapping = modelMapper.createTypeMap(UserApplication.class, ApplicationDto.class);
        mapping.addMapping(a -> a.getApplication().getApplicationId(), ApplicationDto::setApplicationId);
        mapping.addMapping(a -> a.getApplication().getSla().getSlaLevel(), ApplicationDto::setSla);
        mapping.addMapping(a -> a.getApplication().getSubject(), ApplicationDto::setSubject);
        mapping.addMapping(UserApplication::getOpeningDate, ApplicationDto::setOpeningDate);
        mapping.addMapping(a -> a.getUser().getFullName(), ApplicationDto::setFullName);

        var jobMapping = modelMapper.createTypeMap(UserApplication.class, JobDto.class);
        jobMapping.addMapping(t -> "application", JobDto::setJobType);
        jobMapping.addMapping(UserApplication::getId, JobDto::setId);
        jobMapping.addMapping(t -> t.getUser().getFullName(), JobDto::setFullName);
        jobMapping.addMapping(t -> t.getApplication().getSla().getSlaLevel(), JobDto::setSla);
    }

    private void addUserMapping(ModelMapper modelMapper) {
        var mapping = modelMapper.createTypeMap(User.class, UserDto.class);
        mapping.addMapping(User::getFullName, UserDto::setFullName);
    }
}
