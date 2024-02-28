package pl.wroblewski.helpdeskapp.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wroblewski.helpdeskapp.dto.ApplicationDto;
import pl.wroblewski.helpdeskapp.dto.TicketCreateDto;
import pl.wroblewski.helpdeskapp.dto.TicketDto;
import pl.wroblewski.helpdeskapp.dto.UserDto;
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
        var mapping = modelMapper.createTypeMap(UserTicket.class, TicketDto.class);
        mapping.addMapping(t -> t.getTicket().getTicketId(), TicketDto::setTicketId);
        mapping.addMapping(t -> t.getTicket().getSla().getSlaLevel(), TicketDto::setSla);
        mapping.addMapping(UserTicket::getOpeningDate, TicketDto::setOpeningDate);
        mapping.addMapping(t -> t.getTicket().getDescription(), TicketDto::setTitle);
        mapping.addMapping(t -> t.getUser().getFullName(), TicketDto::setFullName);
    }

    private void addApplicationMapping(ModelMapper modelMapper) {
        var mapping = modelMapper.createTypeMap(UserApplication.class, ApplicationDto.class);
        mapping.addMapping(a -> a.getApplication().getApplicationId(), ApplicationDto::setApplicationId);
        mapping.addMapping(a -> a.getApplication().getSla().getSlaLevel(), ApplicationDto::setSla);
        mapping.addMapping(a -> a.getApplication().getSubject(), ApplicationDto::setSubject);
        mapping.addMapping(UserApplication::getOpeningDate, ApplicationDto::setOpeningDate);
        mapping.addMapping(a -> a.getUser().getFullName(), ApplicationDto::setFullName);
    }

    private void addUserMapping(ModelMapper modelMapper) {
        var mapping = modelMapper.createTypeMap(User.class, UserDto.class);
        mapping.addMapping(User::getFullName, UserDto::setFullName);
    }
}
