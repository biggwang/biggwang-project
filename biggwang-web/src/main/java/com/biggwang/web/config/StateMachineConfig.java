package com.biggwang.web.config;

import com.biggwang.web.code.EventsEnum;
import com.biggwang.web.code.StatesEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<StatesEnum, EventsEnum> {

    @Override
    public void configure(StateMachineStateConfigurer<StatesEnum, EventsEnum> states) throws Exception {
        states.withStates()
                .initial(StatesEnum.UNLOCKED)
                .states(EnumSet.allOf(StatesEnum.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<StatesEnum, EventsEnum> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(StatesEnum.UNLOCKED)
                    .target(StatesEnum.LOCKED)
                    .event(EventsEnum.CLOSE)
                    .and()
                .withExternal()
                    .source(StatesEnum.LOCKED)
                    .target(StatesEnum.UNLOCKED)
                    .event(EventsEnum.OPEN)
                    .and();
    }
}