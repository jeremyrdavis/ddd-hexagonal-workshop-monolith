package io.arrogantprogrammer.cfp.domain.services;

import io.arrogantprogrammer.cfp.domain.valueobjects.SessionAbstract;
import io.arrogantprogrammer.domain.valueobjects.Email;

public record SubmitAbstractCommand(Email email, SessionAbstract sessionAbstract){

}
