package com.springbank.user.cmd.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class RemoveUserCommand {
	@TargetAggregateIdentifier
	private String id;

}
