package com.springbank.user.cmd.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.springbank.user.core.models.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserCommand {
	@TargetAggregateIdentifier
	private String id;
	private User user; 

}
