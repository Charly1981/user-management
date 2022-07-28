package com.springbank.user.cmd.api.aggregates;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.commands.RemoveUserCommand;
import com.springbank.user.cmd.api.commands.UpdateUserCommnand;
import com.springbank.user.cmd.api.security.PasswordEncoder;
import com.springbank.user.cmd.api.security.PasswordEncoderImpl;
import com.springbank.user.core.events.UserRegisterdEvent;
import com.springbank.user.core.events.UserRemovedEvent;
import com.springbank.user.core.events.UserUpdatedEvent;
import com.springbank.user.core.models.User;

@Aggregate
public class UserAggregate {
	@AggregateIdentifier
	private String id;
	private User user;

	private final PasswordEncoder passwordEncoder;

	public UserAggregate() {
		passwordEncoder = new PasswordEncoderImpl();

	}

	@CommandHandler
	public UserAggregate(RegisterUserCommand command) {
		User newUser = command.getUser();
		newUser.setId(command.getId());
		String password = newUser.getAcount().getPassword();
		passwordEncoder = new PasswordEncoderImpl();
		var hashedPassword = passwordEncoder.hashPassword(password);
		newUser.getAcount().setPassword(password);

		UserRegisterdEvent event = UserRegisterdEvent.builder().id(command.getId()).user(newUser).build();

		AggregateLifecycle.apply(event);
	}

	@CommandHandler
	public void handle(UpdateUserCommnand command) {
		User updatedUser = command.getUser();
		updatedUser.setId(command.getId());
		String password = updatedUser.getAcount().getPassword();
		String hashedPassword = passwordEncoder.hashPassword(password);
		updatedUser.getAcount().setPassword(hashedPassword);

		UserUpdatedEvent event = UserUpdatedEvent.builder().id(UUID.randomUUID().toString()).user(updatedUser).build();

		AggregateLifecycle.apply(event);
	}

	@CommandHandler
	public void handle(RemoveUserCommand command) {
		var event = new UserRemovedEvent();
		event.setId(command.getId());

		AggregateLifecycle.apply(event);
	}

	@EventSourcingHandler
	public void on(UserRegisterdEvent event) {
		this.id = event.getId();
		this.user = event.getUser();

	}

	@EventSourcingHandler
	public void on(UserUpdatedEvent event) {
		this.user = event.getUser();

	}

	@EventSourcingHandler
	public void on(UserRemovedEvent event) {
		AggregateLifecycle.markDeleted();

	}
}
