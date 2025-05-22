package io.arrogantprogrammer.cfp.domain.services;

import io.arrogantprogrammer.cfp.persistence.SessionAbstractEntity;
import io.arrogantprogrammer.domain.valueobjects.Email;

public record SubmitAbstractCommand(Email email, SessionAbstractEntity sessionAbstractEntity){

}
