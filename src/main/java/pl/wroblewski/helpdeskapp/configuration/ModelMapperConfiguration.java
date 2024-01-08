package pl.wroblewski.helpdeskapp.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wroblewski.helpdeskapp.dto.TicketDto;
import pl.wroblewski.helpdeskapp.models.Ticket;
import pl.wroblewski.helpdeskapp.models.UserTicket;

@Configuration

public class ModelMapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        var mapping = modelMapper.createTypeMap(UserTicket.class, TicketDto.class);
        mapping.addMapping(t -> t.getTicket().getTicketId(), TicketDto::setTicketId);
        mapping.addMapping(t -> t.getTicket().getSla().getSlaLevel(), TicketDto::setSla);
        mapping.addMapping(UserTicket::getOpeningDate, TicketDto::setOpeningDate);
        mapping.addMapping(t -> t.getTicket().getDescription(), TicketDto::setTitle);
        mapping.addMapping(t -> t.getUser().getFirstName(), TicketDto::setFirstName);
        return modelMapper;
    }
}
