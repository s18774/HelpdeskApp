package pl.wroblewski.helpdeskapp.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
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
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        addTicketMapping(modelMapper);
        addApplicationMapping(modelMapper);
        addUserMapping(modelMapper);

        return modelMapper;
    }

    private void addTicketMapping(ModelMapper modelMapper) {
        modelMapper.createTypeMap(UserTicket.class, TicketDto.class)
                .addMappings(mapper -> {
                    mapper.map(t -> t.getTicket().getTicketId(), TicketDto::setTicketId);
                    mapper.map(t -> t.getTicket().getSla().getSlaLevel(), TicketDto::setSla);
                    mapper.map(UserTicket::getOpeningDate, TicketDto::setOpeningDate);
                    mapper.map(t -> t.getTicket().getDescription(), TicketDto::setTitle);
                    mapper.map(t -> t.getUser().getFullName(), TicketDto::setFullName);
                });


        modelMapper.createTypeMap(UserTicket.class, JobDto.class)
                .addMappings(mapper -> {
                    mapper.map(t -> "ticket", JobDto::setJobType);
                    mapper.map(t -> t.getId().getTicketId(), JobDto::setJobId);
                    mapper.map(t -> t.getUser().getFullName(), JobDto::setFullName);
                    mapper.map(t -> t.getTicket().getSla().getSlaLevel(), JobDto::setSla);
                });

    }

    private void addApplicationMapping(ModelMapper modelMapper) {
        modelMapper.createTypeMap(UserApplication.class, ApplicationDto.class)
                .addMappings(mapper -> {
                    mapper.map(a -> a.getApplication().getApplicationId(), ApplicationDto::setApplicationId);
                    mapper.map(a -> a.getApplication().getSla().getSlaLevel(), ApplicationDto::setSla);
                    mapper.map(a -> a.getApplication().getSubject(), ApplicationDto::setSubject);
                    mapper.map(UserApplication::getOpeningDate, ApplicationDto::setOpeningDate);
                    mapper.map(a -> a.getUser().getFullName(), ApplicationDto::setFullName);
                });


        modelMapper.createTypeMap(UserApplication.class, JobDto.class)
                .addMappings(mapper -> {
                    mapper.map(t -> "application", JobDto::setJobType);
                    mapper.map(t -> t.getId().getApplicationId(), JobDto::setJobId);
                    mapper.map(t -> t.getUser().getFullName(), JobDto::setFullName);
                    mapper.map(t -> t.getApplication().getSla().getSlaLevel(), JobDto::setSla);
                });
    }

    private void addUserMapping(ModelMapper modelMapper) {
        var mapping = modelMapper.createTypeMap(User.class, UserDto.class);
        mapping.addMapping(User::getFullName, UserDto::setFullName);
    }
}
