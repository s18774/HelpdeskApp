package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.HelpDeskGroup;
import pl.wroblewski.helpdeskapp.models.HelpDeskGroupId;

public interface HelpDeskGroupRepository extends CrudRepository<HelpDeskGroup, HelpDeskGroupId> {
    //dodawanie/usuwanie pracownika helpdesk do grupy(może to zrobić tylko admin/lider)
    //wyświetlanie grup

}
